package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.service.impl;

import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.constant.AirConditionerConsts;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.constant.AirConditionerStateEnum;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.constant.RegionCodeEnum;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.constant.WindSpeedEnum;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.exception.AddressEmptyException;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.exception.BusinessException;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.exception.RegionCodeException;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.exception.ResponseCode;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.EarthCoordinate;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.dto.AddressDTO;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.dto.AirConditionerDTO;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.entity.Address;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.entity.AirConditioner;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.entity.User;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.response.AirConditionerResponse;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.vo.AirConditionerVO;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.repository.AddressRepository;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.repository.AirConditionerRepository;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.service.AddressService;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.service.AirConditionerService;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.service.UserService;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.util.BaiduMapUtils;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.util.TimeStampUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 覃国强
 * @date 2019-02-20
 */
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
    public AirConditioner add(AirConditionerDTO airConditionerDTO) throws BusinessException, IOException {

        AddressDTO addressDTO = airConditionerDTO.getAddressDTO();
        if (Objects.isNull(addressDTO)) {
            throw new AddressEmptyException(ResponseCode.MISSING, "address", "地址不能为null");
        }

        // 查询当前地址是否存在
        Optional<Address> optional = addressService.findOne(Example.of(
                Address.builder().province(addressDTO.getProvince()).city(addressDTO.getCity())
                        .district(addressDTO.getDistrict()).detail(addressDTO.getDetail()).build()));
        // 保存地址
        Address address = optional.orElseGet(() -> addressService.add(addressDTO));

        AirConditioner airConditioner = new AirConditioner();
        BeanUtils.copyProperties(airConditionerDTO, airConditioner);
        airConditioner.setGmtCreate(TimeStampUtils.now());
        airConditioner.setGmtModified(TimeStampUtils.now());
        airConditioner.setAddressString(addressDTO.toString());

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

        // TODO 检查用户是否在数据库中存在

        airConditioner.setUserId(airConditionerDTO.getUser().getId());
        airConditioner.setAddressId(address.getId());

        return airConditionerRepository.save(airConditioner);
    }

    @Override
    public List<AirConditionerResponse> listAll() {
        List<AirConditioner> list = airConditionerRepository.findAll();
        return airConditioner2AirConditionerResponse(list);
    }

    @Override
    public List<AirConditionerResponse> listAll(int page, int size) {
        return null;
    }

    @Override
    public List<AirConditionerResponse> listByUserId(Long userId) {
        List<AirConditioner> list = airConditionerRepository.findByUserId(userId);
        return airConditioner2AirConditionerResponse(list);
    }

    @Override
    public List<AirConditionerResponse> listLimit(int offset, int rowCount) {

        

        return null;
    }

    private List<AirConditionerResponse> airConditioner2AirConditionerResponse(List<AirConditioner> list) {

        return list.stream().map(value -> {
            AirConditionerResponse response = new AirConditionerResponse();
            BeanUtils.copyProperties(value, response);
            response.setGmtCreate(new Date(value.getGmtCreate().getTime()));
            response.setGmtModified(new Date(value.getGmtModified().getTime()));
            response.setWindSpeed(value.getWindSpeed().getValue());
            response.setEquipmentState(value.getState().getValue());
            response.setAddress(value.getAddressString());
            return response;
        }).collect(Collectors.toList());

    }

    /*
        @Transactional(rollbackFor = Exception.class)
        @Override
        public int update(AirConditionerVO airConditionerVO) throws IOException {

            // 根据地址获取区域编码
            RegionCodeEnum regionCode = RegionCodeEnum.parse(airConditionerVO.getAddress());
            if (Objects.isNull(regionCode)) {
                throw new RegionCodeException(ResponseCode.INVALID, "address", "地址无效");
            }
            airConditionerVO.setRegionCode(regionCode);
            // 获取地址经纬度
            BigDecimal[] longitudeAndLatitude = BaiduMapUtils.getLongitudeAndLatitude(airConditionerVO.getAddress());
            airConditionerVO.setLongitude(longitudeAndLatitude[0]);
            airConditionerVO.setLatitude(longitudeAndLatitude[1]);
            airConditionerVO.setGmtModified(new Timestamp(System.currentTimeMillis()));
            return airConditionerRepository.update(airConditionerVO.getId(), airConditionerVO.getGmtModified(),
                    airConditionerVO.getBrand(), airConditionerVO.getModel(), airConditionerVO.getSeller(),
                    airConditionerVO.getAddress(), airConditionerVO.getRegionCode(), airConditionerVO.getLongitude(),
                    airConditionerVO.getLatitude());
        }

        @Override
        public List<AirConditionerVO> listAirConditioners(AirConditioner probe) {

            ExampleMatcher matcher =
                    ExampleMatcher.matching().withMatcher("address", ExampleMatcher.GenericPropertyMatchers.contains());
            List<AirConditioner> list = airConditionerRepository.findAll(Example.of(probe, matcher));
            return list.stream().map(airConditioner -> {
                User user = null;
                try {
                    user = userService.findById(airConditioner.getUserId());
                } catch (BusinessException e) {
                    e.printStackTrace();
                    throw new RuntimeException("我发生异常了！");
                }
                AirConditionerVO airConditionerVO = new AirConditionerVO();
                BeanUtils.copyProperties(airConditioner, airConditionerVO);
                airConditionerVO.setUser(user);
                return airConditionerVO;
            }).collect(Collectors.toList());
        }

        @Override
        public List<AirConditionerVO> listAll() {
            List<AirConditioner> list = airConditionerRepository.findAll();
            return list.stream().map(airConditioner -> {
                AirConditionerVO airConditionerVO = new AirConditionerVO();
                BeanUtils.copyProperties(airConditioner, airConditionerVO);
                return airConditionerVO;
            }).collect(Collectors.toList());
        }

        /**
         * 将 Long 类型的 regionCount 通过添加前导0变成一个 长度为 8 的定长字符串
         *
         * @param regionCount
         * @return
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
