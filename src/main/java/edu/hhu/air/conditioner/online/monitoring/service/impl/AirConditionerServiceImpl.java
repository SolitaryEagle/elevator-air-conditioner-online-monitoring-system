package edu.hhu.air.conditioner.online.monitoring.service.impl;

import edu.hhu.air.conditioner.online.monitoring.constant.AirConditionerConsts;
import edu.hhu.air.conditioner.online.monitoring.constant.AirConditionerStateEnum;
import edu.hhu.air.conditioner.online.monitoring.constant.RegionCodeEnum;
import edu.hhu.air.conditioner.online.monitoring.constant.WindSpeedEnum;
import edu.hhu.air.conditioner.online.monitoring.exception.AddressEmptyException;
import edu.hhu.air.conditioner.online.monitoring.exception.BusinessException;
import edu.hhu.air.conditioner.online.monitoring.exception.ResponseCode;
import edu.hhu.air.conditioner.online.monitoring.model.EarthCoordinate;
import edu.hhu.air.conditioner.online.monitoring.model.dto.AddressDTO;
import edu.hhu.air.conditioner.online.monitoring.model.dto.AirConditionerDTO;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Address;
import edu.hhu.air.conditioner.online.monitoring.model.entity.AirConditioner;
import edu.hhu.air.conditioner.online.monitoring.model.request.AirConditionerSearchRequest;
import edu.hhu.air.conditioner.online.monitoring.model.request.IntervalRequest;
import edu.hhu.air.conditioner.online.monitoring.model.response.AirConditionerResponse;
import edu.hhu.air.conditioner.online.monitoring.model.response.PageResponse;
import edu.hhu.air.conditioner.online.monitoring.repository.AirConditionerRepository;
import edu.hhu.air.conditioner.online.monitoring.service.AddressService;
import edu.hhu.air.conditioner.online.monitoring.service.AirConditionerService;
import edu.hhu.air.conditioner.online.monitoring.service.UserService;
import edu.hhu.air.conditioner.online.monitoring.util.BaiduMapUtils;
import edu.hhu.air.conditioner.online.monitoring.util.ObjectUtils;
import edu.hhu.air.conditioner.online.monitoring.util.TimeStampUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
    public long count() {
        return airConditionerRepository.count();
    }

    @Override
    public List<AirConditionerResponse> listAll() {
        List<AirConditioner> list = airConditionerRepository.findAll();
        return list.stream().map(this::airConditioner2AirConditionerResponse).collect(Collectors.toList());
    }

    @Override
    public PageResponse<AirConditionerResponse> listAll(int page, int size) {
        Page<AirConditioner> pageResult = airConditionerRepository.findAll(PageRequest.of(page, size));
        return PageResponse.of(pageResult.getTotalElements(),
                pageResult.stream().map(this::airConditioner2AirConditionerResponse).collect(Collectors.toList()));
    }

    @Override
    public List<AirConditionerResponse> listAll(AirConditionerSearchRequest searchRequest) {
        Specification<AirConditioner> specification = getAirConditionerSearchRequestSpecification(searchRequest);
        // 使用 Specification 查询
        List<AirConditioner> list = airConditionerRepository.findAll(specification);
        return list.stream().map(this::airConditioner2AirConditionerResponse).collect(Collectors.toList());
    }

    /**
     * 使用给定的 AirConditionerSearchRequest 参数构造一个 Specification
     *
     * @param searchRequest
     * @return
     */
    @NotNull
    private Specification<AirConditioner> getAirConditionerSearchRequestSpecification(
            AirConditionerSearchRequest searchRequest) {
        return (Specification<AirConditioner>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(searchRequest.getBrand())) {
                predicates.add(criteriaBuilder.like(root.get("brand"), "%" + searchRequest.getBrand() + "%"));
            }
            if (StringUtils.isNotBlank(searchRequest.getModel())) {
                predicates.add(criteriaBuilder.like(root.get("model"), "%" + searchRequest.getModel() + "%"));
            }
            if (StringUtils.isNotBlank(searchRequest.getSeller())) {
                predicates.add(criteriaBuilder.like(root.get("seller"), "%" + searchRequest.getSeller() + "%"));
            }
            if (StringUtils.isNotBlank(searchRequest.getAddress())) {
                predicates.add(criteriaBuilder
                        .like(root.get("addressString"), "%" + searchRequest.getAddress() + "%"));
            }
            if (ObjectUtils.nonNull(searchRequest.getWindSpeed())) {
                predicates.add(criteriaBuilder.equal(root.get("windSpeed"), searchRequest.getWindSpeed()));
            }
            if (ObjectUtils.nonNull(searchRequest.getEquipmentState())) {
                predicates.add(criteriaBuilder.equal(root.get("state"), searchRequest.getEquipmentState()));
            }
            if (ObjectUtils.nonNull(searchRequest.getTemperature())) {
                IntervalRequest temperature = searchRequest.getTemperature();
                int min = ObjectUtils.nonNull(temperature.getMin()) ? temperature.getMin().intValue()
                        : Integer.MIN_VALUE;
                int max = ObjectUtils.nonNull(temperature.getMax()) ? temperature.getMax().intValue()
                        : Integer.MAX_VALUE;
                predicates.add(criteriaBuilder.between(root.get("temperature"), min, max));
            }
            if (ObjectUtils.nonNull(searchRequest.getKwh())) {
                IntervalRequest kwh = searchRequest.getKwh();
                double min = ObjectUtils.nonNull(kwh.getMin()) ? kwh.getMin().doubleValue() : Double.MIN_VALUE;
                double max = ObjectUtils.nonNull(kwh.getMax()) ? kwh.getMax().doubleValue() : Double.MAX_VALUE;
                predicates.add(criteriaBuilder.between(root.get("kwh"), BigDecimal.valueOf(min),
                        BigDecimal.valueOf(max)));
            }
            if (ObjectUtils.nonNull(searchRequest.getCurrentIntensity())) {
                IntervalRequest currentIntensity = searchRequest.getCurrentIntensity();
                double min = ObjectUtils.nonNull(currentIntensity.getMin()) ?
                        currentIntensity.getMin().doubleValue() : Double.MIN_VALUE;
                double max = ObjectUtils.nonNull(currentIntensity.getMax()) ?
                        currentIntensity.getMax().doubleValue() : Double.MAX_VALUE;
                predicates.add(criteriaBuilder.between(root.get("currentIntensity"), BigDecimal.valueOf(min),
                        BigDecimal.valueOf(max)));
            }
            if (ObjectUtils.nonNull(searchRequest.getVoltage())) {
                IntervalRequest voltage = searchRequest.getVoltage();
                double min = ObjectUtils.nonNull(voltage.getMin()) ? voltage.getMin().doubleValue()
                        : Double.MIN_VALUE;
                double max = ObjectUtils.nonNull(voltage.getMax()) ? voltage.getMax().doubleValue()
                        : Double.MAX_VALUE;
                predicates.add(criteriaBuilder.between(root.get("voltage"), BigDecimal.valueOf(min),
                        BigDecimal.valueOf(max)));
            }
            if (ObjectUtils.nonNull(searchRequest.getPower())) {
                IntervalRequest power = searchRequest.getPower();
                double min = ObjectUtils.nonNull(power.getMin()) ? power.getMin().doubleValue() : Double.MIN_VALUE;
                double max = ObjectUtils.nonNull(power.getMax()) ? power.getMax().doubleValue() : Double.MAX_VALUE;
                predicates.add(criteriaBuilder.between(root.get("power"), BigDecimal.valueOf(min),
                        BigDecimal.valueOf(max)));
            }
            if (ObjectUtils.nonNull(searchRequest.getUserId()) && searchRequest.getUserId() > 0) {
                predicates.add(criteriaBuilder.equal(root.get("userId"), searchRequest.getUserId()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Override
    public PageResponse<AirConditionerResponse> listAll(AirConditionerSearchRequest searchRequest, int page, int size) {
        Specification<AirConditioner> specification = getAirConditionerSearchRequestSpecification(searchRequest);
        Page<AirConditioner> pageResult = airConditionerRepository.findAll(specification, PageRequest.of(page, size));
        return PageResponse.of(pageResult.getTotalElements(),
                pageResult.stream().map(this::airConditioner2AirConditionerResponse).collect(Collectors.toList()));
    }

    @Override
    public List<AirConditionerResponse> listAllByUserId(Long userId) {
        List<AirConditioner> list = airConditionerRepository.findByUserId(userId);
        return list.stream().map(this::airConditioner2AirConditionerResponse).collect(Collectors.toList());
    }

    @Override
    public PageResponse<AirConditionerResponse> listAllByUserId(Long userId, int page, int size) {
        Page<AirConditioner> pageResult = airConditionerRepository
                .findAll(Example.of(AirConditioner.builder().userId(userId).build()), PageRequest.of(page, size));
        return PageResponse.of(pageResult.getTotalElements(),
                pageResult.stream().map(this::airConditioner2AirConditionerResponse).collect(Collectors.toList()));
    }

    private AirConditionerResponse airConditioner2AirConditionerResponse(AirConditioner airConditioner) {

        AirConditionerResponse response = new AirConditionerResponse();
        BeanUtils.copyProperties(airConditioner, response);
        response.setGmtCreate(new Date(airConditioner.getGmtCreate().getTime()));
        response.setGmtModified(new Date(airConditioner.getGmtModified().getTime()));
        response.setWindSpeed(airConditioner.getWindSpeed().getValue());
        response.setEquipmentState(airConditioner.getState().getValue());
        response.setAddress(airConditioner.getAddressString());
        return response;

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
