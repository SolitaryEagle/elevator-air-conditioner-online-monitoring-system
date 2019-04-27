package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.service.impl;

import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.constant.AirConditionerConsts;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.constant.AirConditionerStateEnum;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.constant.RegionCodeEnum;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.constant.WindSpeed;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.exception.BusinessException;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.exception.RegionCodeException;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.exception.ResponseCode;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.entity.AirConditioner;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.entity.User;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.vo.AirConditionerVO;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.repository.AirConditionerRepository;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.service.AirConditionerService;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.service.UserService;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.util.BaiduMapUtils;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 覃国强
 * @date 2019-02-20
 */
@Service("airConditionerService")
public class AirConditionerServiceImpl implements AirConditionerService {

    private final AirConditionerRepository airConditionerRepository;
    private final UserService userService;

    @Autowired
    public AirConditionerServiceImpl(AirConditionerRepository airConditionerRepository, UserService userService) {
        this.airConditionerRepository = airConditionerRepository;
        this.userService = userService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AirConditioner save(AirConditionerVO airConditionerVO) throws IOException {

        // 根据地址获取区域编码
        RegionCodeEnum regionCode = RegionCodeEnum.parse(airConditionerVO.getAddress());
        if (Objects.isNull(regionCode)) {
            throw new RegionCodeException(ResponseCode.INVALID, "address", "地址无效");
        }
        airConditionerVO.setRegionCode(regionCode);

        // 获取当前区域的空调总数
        long regionCount = airConditionerRepository.countByRegionCode(regionCode);

        // 设置编号
        String equipmentNumber = regionCount2EquimentNumber(regionCount);
        equipmentNumber = "LD" + regionCode.getCode() + equipmentNumber;
        airConditionerVO.setEquipmentNumber(equipmentNumber);

        // 获取地址经纬度
        BigDecimal[] longitudeAndLatitude = BaiduMapUtils.getLongitudeAndLatitude(airConditionerVO.getAddress());
        airConditionerVO.setLongitude(longitudeAndLatitude[0]);
        airConditionerVO.setLatitude(longitudeAndLatitude[1]);

        // 设置一些系统生成的数据 和 默认值
        airConditionerVO.setGmtCreate(new Timestamp(System.currentTimeMillis()));
        airConditionerVO.setGmtModified(new Timestamp(System.currentTimeMillis()));
        airConditionerVO.setState(AirConditionerStateEnum.GOOD);
        airConditionerVO.setFaultDescription("");

        // 以下数据本该通过HTTP协议与远程实际空调获取，但本系统为了简化功能，进行了随机数的设置
        int randomTemperature =
                RandomUtils.nextInt(AirConditionerConsts.MIN_TEMPERATURE, AirConditionerConsts.MAX_TEMPERATURE);
        airConditionerVO.setTemperature(randomTemperature);

        airConditionerVO.setWindSpeed(WindSpeed.random());

        double randomKwh = RandomUtils.nextDouble(AirConditionerConsts.MIN_KWH, AirConditionerConsts.MAX_KWH);
        airConditionerVO.setKwh(BigDecimal.valueOf(randomKwh));

        double randomCurrentIntensity = RandomUtils
                .nextDouble(AirConditionerConsts.MIN_CURRENT_INTENSITY, AirConditionerConsts.MAX_CURRENT_INTENSITY);
        airConditionerVO.setCurrentIntensity(BigDecimal.valueOf(randomCurrentIntensity));

        double randomVoltage =
                RandomUtils.nextDouble(AirConditionerConsts.MIN_VOLTAGE, AirConditionerConsts.MAX_VOLTAGE);
        airConditionerVO.setVoltage(BigDecimal.valueOf(randomVoltage));

        double power = randomCurrentIntensity * randomVoltage;
        airConditionerVO.setPower(BigDecimal.valueOf(power));

        AirConditioner airConditioner = new AirConditioner();
        BeanUtils.copyProperties(airConditionerVO, airConditioner);
        airConditioner.setUserId(airConditionerVO.getUser().getId());
        return airConditionerRepository.save(airConditioner);
    }

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
     *
     * @return
     */
    private String regionCount2EquimentNumber(long regionCount) {

        int length = 8;
        StringBuilder sb = new StringBuilder(length);
        sb.append(regionCount);
        while (sb.length() < length) {
            sb.insert(0, 0);
        }
        return sb.toString();
    }

}
