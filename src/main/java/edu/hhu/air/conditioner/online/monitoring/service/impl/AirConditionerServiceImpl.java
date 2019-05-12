package edu.hhu.air.conditioner.online.monitoring.service.impl;

import edu.hhu.air.conditioner.online.monitoring.constant.AirConditionerConsts;
import edu.hhu.air.conditioner.online.monitoring.constant.SystemConsts;
import edu.hhu.air.conditioner.online.monitoring.constant.enums.AirConditionerStateEnum;
import edu.hhu.air.conditioner.online.monitoring.constant.enums.ErrorCodeEnum;
import edu.hhu.air.conditioner.online.monitoring.constant.enums.RegionCodeEnum;
import edu.hhu.air.conditioner.online.monitoring.constant.enums.WindSpeedEnum;
import edu.hhu.air.conditioner.online.monitoring.exception.ActivationException;
import edu.hhu.air.conditioner.online.monitoring.exception.AirConditionerException;
import edu.hhu.air.conditioner.online.monitoring.exception.BusinessException;
import edu.hhu.air.conditioner.online.monitoring.model.EarthCoordinate;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Address;
import edu.hhu.air.conditioner.online.monitoring.model.entity.AirConditioner;
import edu.hhu.air.conditioner.online.monitoring.repository.AirConditionerRepository;
import edu.hhu.air.conditioner.online.monitoring.service.AddressService;
import edu.hhu.air.conditioner.online.monitoring.service.AirConditionerService;
import edu.hhu.air.conditioner.online.monitoring.service.UserService;
import edu.hhu.air.conditioner.online.monitoring.util.BaiduMapUtils;
import edu.hhu.air.conditioner.online.monitoring.util.TimestampUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
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
import java.util.Optional;

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

    /**
     * 添加 AirConditioner 记录。参数 airConditioner 中需要的字段：
     * brand：品牌
     * model：型号
     * seller：销售方
     * addressString：地址的普通形式
     * addressId：地址主键
     * userId: 所属者主键
     *
     * @param airConditioner 聚合 airConditioner 字段的参数
     * @return 保存成功后的 airConditioner 对象
     * @throws IOException 获取地址坐标时可能会抛出此异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AirConditioner add(AirConditioner airConditioner) throws IOException {
        airConditioner.setGmtCreate(TimestampUtils.now());
        airConditioner.setGmtModified(TimestampUtils.now());

        Address address = addressService.getById(airConditioner.getAddressId());
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
        AirConditioner saveAirConditioner = airConditionerRepository.save(airConditioner);
        return saveAirConditioner;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByEquipmentId(String equipmentId) {
        airConditionerRepository.deleteByEquipmentId(equipmentId);
        log.info("设备信息已删除！equipmentId：{}", equipmentId);
    }

    /**
     * 更新设备信息。参数 airConditioner 中需要的字段：
     * equipmentId：设备Id
     * brand：品牌
     * model：型号
     * seller：销售方
     * addressString：地址的普通形式
     * addressId：地址主键
     * userId: 所属者主键
     *
     * @param airConditioner 聚合 airConditioner 字段的参数
     * @return 更新后的 AirConditioner 的对象
     * @throws IOException 获取地址坐标时可能会抛出此异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AirConditioner updateByEquipmentId(AirConditioner airConditioner) throws IOException {
        airConditioner.setGmtModified(TimestampUtils.now());
        Address address = addressService.getById(airConditioner.getAddressId());
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
    @Transactional(rollbackFor = Exception.class)
    public int updateStateAndFaultDescriptionById(AirConditioner airConditioner) {
        airConditioner.setGmtModified(TimestampUtils.now());
        int result = airConditionerRepository.updateStateAndFaultDescriptionById(airConditioner);
        if (result <= 0) {
            log.info("更新设备状态和故障描述失败。id: {}, state: {}, desc: {}", airConditioner.getId(),
                    airConditioner.getState(), airConditioner.getFaultDescription());
            throw new BusinessException(ErrorCodeEnum.INTERNAL_SERVER_ERROR, "id", "更新设备状态和故障描述失败");
        } else {
            log.info("更新设备状态和故障描述成功。id：{}", airConditioner.getId());
        }
        return result;
    }

    @Override
    public long count() {
        return airConditionerRepository.count();
    }

    @Override
    public AirConditioner getById(Long id) {

        Optional<AirConditioner> optional = airConditionerRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        log.warn("设备不存在！id ：{}", id);
        throw new AirConditionerException(ErrorCodeEnum.MISSING, "id", "id 为 " + id + " 的设备不存在！");
    }

    @Override
    public List<AirConditioner> listAll() {
        List<AirConditioner> list = airConditionerRepository.findAll();
        return list;
    }

    @Override
    public Page<AirConditioner> listAll(int page, int size) {
        Page<AirConditioner> pageResult = airConditionerRepository.findAll(PageRequest.of(page, size));
        return pageResult;
    }

    @Override
    public List<AirConditioner> listAll(Specification<AirConditioner> specification) {
        // 使用 Specification 查询
        List<AirConditioner> list = airConditionerRepository.findAll(specification);
        return list;
    }

    @Override
    public Page<AirConditioner> listAll(Specification<AirConditioner> specification, int page, int size) {
        Page<AirConditioner> pageResult = airConditionerRepository.findAll(specification, PageRequest.of(page, size));
        return pageResult;
    }

    @Override
    public List<AirConditioner> listAllByUserId(Long userId) {
        List<AirConditioner> list = airConditionerRepository.findByUserId(userId);
        return list;
    }

    @Override
    public Page<AirConditioner> listAllByUserId(Long userId, int page, int size) {
        Page<AirConditioner> pageResult = airConditionerRepository
                .findAll(Example.of(AirConditioner.builder().userId(userId).build()), PageRequest.of(page, size));
        return pageResult;
    }

    @Override
    public AirConditioner getByEquipmentId(String equipmentId) {
        Optional<AirConditioner> optional = airConditionerRepository.findByEquipmentId(equipmentId);
        if (optional.isPresent()) {
            return optional.get();
        }
        log.error("设备不存在！equipmentId ： " + equipmentId);
        throw new AirConditionerException(ErrorCodeEnum.MISSING, "equipmentId", "设备不存在！");
    }

    @Override
    public List<AirConditioner> listByUserIdAndState(Long userId, AirConditionerStateEnum state) {
        List<AirConditioner> list = airConditionerRepository.findByUserIdAndState(userId, state);
        log.info("获取该用户的所有指定状态下的设备成功。userId: {}, state: {}", userId, state);
        return list;
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
