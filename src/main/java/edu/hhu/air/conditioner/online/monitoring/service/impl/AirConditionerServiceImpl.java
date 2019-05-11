package edu.hhu.air.conditioner.online.monitoring.service.impl;

import edu.hhu.air.conditioner.online.monitoring.constant.AirConditionerConsts;
import edu.hhu.air.conditioner.online.monitoring.constant.SystemConsts;
import edu.hhu.air.conditioner.online.monitoring.constant.enums.AirConditionerStateEnum;
import edu.hhu.air.conditioner.online.monitoring.constant.enums.ErrorCodeEnum;
import edu.hhu.air.conditioner.online.monitoring.constant.enums.RegionCodeEnum;
import edu.hhu.air.conditioner.online.monitoring.constant.enums.WindSpeedEnum;
import edu.hhu.air.conditioner.online.monitoring.exception.ActivationException;
import edu.hhu.air.conditioner.online.monitoring.exception.AirConditionerException;
import edu.hhu.air.conditioner.online.monitoring.model.EarthCoordinate;
import edu.hhu.air.conditioner.online.monitoring.model.dto.AirConditionerDTO;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Address;
import edu.hhu.air.conditioner.online.monitoring.model.entity.AirConditioner;
import edu.hhu.air.conditioner.online.monitoring.model.entity.User;
import edu.hhu.air.conditioner.online.monitoring.repository.AirConditionerRepository;
import edu.hhu.air.conditioner.online.monitoring.service.AddressService;
import edu.hhu.air.conditioner.online.monitoring.service.AirConditionerService;
import edu.hhu.air.conditioner.online.monitoring.service.UserService;
import edu.hhu.air.conditioner.online.monitoring.util.BaiduMapUtils;
import edu.hhu.air.conditioner.online.monitoring.util.TimeStampUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 覃国强
 * @date 2019-02-20
 */
@Slf4j
@Service
public class AirConditionerServiceImpl implements AirConditionerService {

    private final AirConditionerRepository airConditionerRepository;
    private final UserService userService;
    private final AddressService addressService;

    @Autowired
    public AirConditionerServiceImpl(AirConditionerRepository airConditionerRepository, UserService userService,
            AddressService addressService) {
        this.airConditionerRepository = airConditionerRepository;
        this.userService = userService;
        this.addressService = addressService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AirConditionerDTO add(AirConditionerDTO airConditionerDTO) throws IOException {

        // 查询当前地址是否存在 ?  存在：不保存！不存在：保存！
        Address address = airConditionerDTO.getAddress();
        Address testAddress = addressService.getByExample(Example.of(address));
        address = Objects.isNull(testAddress) ? addressService.add(address) : testAddress;

        AirConditioner airConditioner = airConditionerDTO.toAirConditioner();
        airConditioner.setGmtCreate(TimeStampUtils.now());
        airConditioner.setGmtModified(TimeStampUtils.now());
        airConditioner.setAddressString(address.toSimpleString());

        // 根据省份获取区域编码
        RegionCodeEnum regionCode = RegionCodeEnum.valueOfProvince(address.getProvince());
        airConditioner.setRegionCode(regionCode);

        // 获取设备编号
        long regionCount = airConditionerRepository.countByRegionCode(regionCode);
        String equipmentId = regionCount2EquipmentId(regionCount);
        equipmentId = "LD" + regionCode.getCode() + equipmentId;
        airConditioner.setEquipmentId(equipmentId);

        // 获取地址经纬度
        EarthCoordinate earthCoordinate = BaiduMapUtils.getEarthCoordinate(airConditioner.getAddressString());
        airConditioner.setLongitude(earthCoordinate.getLongitude());
        airConditioner.setLatitude(earthCoordinate.getLatitude());

        // 以下数据本该通过HTTP协议与远程实际空调获取，但本系统为了简化功能，进行了随机数的设置
        int randomTemperature =
                RandomUtils.nextInt(AirConditionerConsts.MIN_TEMPERATURE, AirConditionerConsts.MAX_TEMPERATURE);
        airConditioner.setTemperature(randomTemperature);
        airConditioner.setWindSpeed(WindSpeedEnum.random());
        double randomKwh = RandomUtils.nextDouble(AirConditionerConsts.MIN_KWH, AirConditionerConsts.MAX_KWH);
        airConditioner.setKwh(BigDecimal.valueOf(randomKwh));
        double randomCurrentIntensity = RandomUtils
                .nextDouble(AirConditionerConsts.MIN_CURRENT_INTENSITY, AirConditionerConsts.MAX_CURRENT_INTENSITY);
        airConditioner.setCurrentIntensity(BigDecimal.valueOf(randomCurrentIntensity));
        double randomVoltage =
                RandomUtils.nextDouble(AirConditionerConsts.MIN_VOLTAGE, AirConditionerConsts.MAX_VOLTAGE);
        airConditioner.setVoltage(BigDecimal.valueOf(randomVoltage));
        double power = randomCurrentIntensity * randomVoltage;
        airConditioner.setPower(BigDecimal.valueOf(power));
        airConditioner.setState(AirConditionerStateEnum.GOOD);

        if (!userService.existsById(airConditioner.getUserId())) {
            throw new AirConditionerException(ErrorCodeEnum.MISSING_FIELD, "userId", "该设备所属的用户不存在！");
        }

        airConditioner.setUserId(airConditionerDTO.getUser().getId());
        airConditioner.setAddressId(address.getId());

        return airConditioner2AirConditionerDTO(airConditioner);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByEquipmentId(String equipmentId) {
        airConditionerRepository.deleteByEquipmentId(equipmentId);
        log.info("设备信息已删除！equipmentId：{}", equipmentId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AirConditionerDTO updateByEquipmentId(AirConditionerDTO airConditionerDTO) throws IOException {

        // 判断地址是否合法
        Address address = airConditionerDTO.getAddress();
        Address testAddress = addressService.getByExample(Example.of(address));
        address = Objects.isNull(testAddress) ? addressService.add(address) : testAddress;

        AirConditioner airConditioner = airConditionerDTO.toAirConditioner();
        airConditioner.setGmtModified(TimeStampUtils.now());
        airConditioner.setAddressString(address.toSimpleString());

        // 根据省份获取区域编码
        RegionCodeEnum regionCode = RegionCodeEnum.valueOfProvince(address.getProvince());
        airConditioner.setRegionCode(regionCode);

        // 获取地址经纬度
        EarthCoordinate earthCoordinate = BaiduMapUtils.getEarthCoordinate(airConditioner.getAddressString());
        airConditioner.setLongitude(earthCoordinate.getLongitude());
        airConditioner.setLatitude(earthCoordinate.getLatitude());

        airConditioner.setAddressId(address.getId());

        int result = airConditionerRepository.updateByEquipmentId(airConditioner);
        if (result <= 0) {
            log.warn("更新设备信息失败； equipmentId: {}", airConditioner.getEquipmentId());
            throw new ActivationException(ErrorCodeEnum.INTERNAL_SERVER_ERROR, "equipmentId",
                    SystemConsts.SYSTEM_ERROR_PROMPT);
        }
        log.info("更新设备信息成功！ equipmentId: {}", airConditioner.getEquipmentId());
        return getByEquipmentId(airConditioner.getEquipmentId());
    }

    @Override
    public long count() {
        return airConditionerRepository.count();
    }

    @Override
    public AirConditionerDTO getById(Long id) {

        Optional<AirConditioner> optional = airConditionerRepository.findById(id);
        if (optional.isPresent()) {
            return airConditioner2AirConditionerDTO(optional.get());
        }
        log.warn("设备不存在！id ：{}", id);
        throw new AirConditionerException(ErrorCodeEnum.MISSING, "id", "id 为 " + id + " 的设备不存在！");
    }

    private AirConditionerDTO airConditioner2AirConditionerDTO(AirConditioner airConditioner) {
        AirConditionerDTO result = new AirConditionerDTO();
        BeanUtils.copyProperties(airConditioner, result);
        User user = userService.getById(airConditioner.getUserId());
        Address address = addressService.getById(airConditioner.getAddressId());
        result.setUser(user);
        result.setAddress(address);
        return result;
    }

    @Override
    public List<AirConditionerDTO> listAll() {
        List<AirConditioner> list = airConditionerRepository.findAll();
        return list.stream().map(this::airConditioner2AirConditionerDTO).collect(Collectors.toList());
    }

    @Override
    public Page<AirConditionerDTO> listAll(int page, int size) {
        Page<AirConditioner> pageResult = airConditionerRepository.findAll(PageRequest.of(page, size));
        return pageResult.map(this::airConditioner2AirConditionerDTO);
    }

    @Override
    public List<AirConditionerDTO> listAll(Specification<AirConditioner> specification) {
        // 使用 Specification 查询
        List<AirConditioner> list = airConditionerRepository.findAll(specification);
        return list.stream().map(this::airConditioner2AirConditionerDTO).collect(Collectors.toList());
    }

    @Override
    public Page<AirConditionerDTO> listAll(Specification<AirConditioner> specification, int page, int size) {
        Page<AirConditioner> pageResult = airConditionerRepository.findAll(specification, PageRequest.of(page, size));
        return pageResult.map(this::airConditioner2AirConditionerDTO);
    }

    @Override
    public List<AirConditionerDTO> listAllByUserId(Long userId) {
        List<AirConditioner> list = airConditionerRepository.findByUserId(userId);
        return list.stream().map(this::airConditioner2AirConditionerDTO).collect(Collectors.toList());
    }

    @Override
    public Page<AirConditionerDTO> listAllByUserId(Long userId, int page, int size) {
        Page<AirConditioner> pageResult = airConditionerRepository
                .findAll(Example.of(AirConditioner.builder().userId(userId).build()), PageRequest.of(page, size));
        return pageResult.map(this::airConditioner2AirConditionerDTO);
    }

    @Override
    public AirConditionerDTO getByEquipmentId(String equipmentId) {
        Optional<AirConditioner> optional = airConditionerRepository.findByEquipmentId(equipmentId);
        if (optional.isPresent()) {
            return airConditioner2AirConditionerDTO(optional.get());
        }
        log.error("设备不存在！equipmentId ： " + equipmentId);
        throw new AirConditionerException(ErrorCodeEnum.MISSING, "equipmentId", "设备不存在！");
    }

    /**
     * 将 Long 类型的 regionCount 通过添加前导0变成一个 长度为 8 的定长字符串
     *
     * @param regionCount 当前 region 的数量
     * @return 生成的 equipmentId
     */
    private String regionCount2EquipmentId(long regionCount) {

        int length = 8;
        StringBuilder sb = new StringBuilder(length);
        sb.append(regionCount);
        while (sb.length() < length) {
            sb.insert(0, 0);
        }
        return sb.toString();
    }

}
