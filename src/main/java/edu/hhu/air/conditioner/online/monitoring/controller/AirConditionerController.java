package edu.hhu.air.conditioner.online.monitoring.controller;

import edu.hhu.air.conditioner.online.monitoring.constant.SessionConsts;
import edu.hhu.air.conditioner.online.monitoring.constant.UrlMappingConsts;
import edu.hhu.air.conditioner.online.monitoring.constant.enums.AirConditionerStateEnum;
import edu.hhu.air.conditioner.online.monitoring.constant.enums.RoleEnum;
import edu.hhu.air.conditioner.online.monitoring.model.Interval;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Address;
import edu.hhu.air.conditioner.online.monitoring.model.entity.AirConditioner;
import edu.hhu.air.conditioner.online.monitoring.model.entity.User;
import edu.hhu.air.conditioner.online.monitoring.model.request.AddressRequest;
import edu.hhu.air.conditioner.online.monitoring.model.request.AirConditionerAddRequest;
import edu.hhu.air.conditioner.online.monitoring.model.request.AirConditionerSearchRequest;
import edu.hhu.air.conditioner.online.monitoring.model.request.AirConditionerUpdateRequest;
import edu.hhu.air.conditioner.online.monitoring.model.response.AirConditionerDetailResponse;
import edu.hhu.air.conditioner.online.monitoring.model.response.AirConditionerMapResponse;
import edu.hhu.air.conditioner.online.monitoring.model.response.AirConditionerTableResponse;
import edu.hhu.air.conditioner.online.monitoring.model.response.PageResponse;
import edu.hhu.air.conditioner.online.monitoring.model.response.UserResponse;
import edu.hhu.air.conditioner.online.monitoring.service.AddressService;
import edu.hhu.air.conditioner.online.monitoring.service.AirConditionerService;
import edu.hhu.air.conditioner.online.monitoring.service.UserService;
import edu.hhu.air.conditioner.online.monitoring.util.EntityTranslatorUtils;
import edu.hhu.air.conditioner.online.monitoring.util.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 覃国强
 * @date 2019-02-20
 */
@RestController
@RequestMapping(UrlMappingConsts.AIR_CONDITIONER_BASE_MAPPING_V1)
public class AirConditionerController {

    @Autowired
    private HttpSession session;

    @Autowired
    private AirConditionerService airConditionerService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserController userController;

    /**
     * 设备信息录入
     *
     * @param user                     当前登录用户
     * @param airConditionerAddRequest 信息的提交表单
     * @return 最后生成的设备信息
     * @throws IOException 地址查询时可能会发生 IO 异常
     */
    @PostMapping("/add")
    public AirConditioner add(@SessionAttribute(SessionConsts.LOGIN_USER_KEY) User user,
            @ModelAttribute AirConditionerAddRequest airConditionerAddRequest)
            throws IOException, InstantiationException, IllegalAccessException {
        AddressRequest addressRequest = airConditionerAddRequest.getAddress();
        // 获取 Address 实体类
        Address address = getAddressByAddressRequest(addressRequest);

        // 获取 AirConditioner 实体类
        AirConditioner translateAirConditioner =
                EntityTranslatorUtils.translate(airConditionerAddRequest, AirConditioner.class);
        translateAirConditioner.setAddressString(addressRequest.toSimpleString());
        translateAirConditioner.setAddressId(address.getId());
        translateAirConditioner.setUserId(user.getId());

        return airConditionerService.add(translateAirConditioner);
    }

    /**
     * 依据 equipmentId 删除设备信息
     *
     * @param equipmentId 设备Id
     */
    @PostMapping("/{equipmentId}/delete")
    public void deleteByEquipmentId(@PathVariable String equipmentId) {
        airConditionerService.deleteByEquipmentId(equipmentId);
    }

    /**
     * 根据 addressRequest 从数据库中获取一个 Address 对象，如果数据库中已存在此对象，直接返回；否则，先插入，再返回。
     *
     * @param addressRequest 给定的 addressRequest 对象
     * @return 数据库中的 Address 实体对象
     * @throws InstantiationException 实体转换可能会抛出此异常
     * @throws IllegalAccessException 实体转换可能会抛出此异常
     */
    private Address getAddressByAddressRequest(AddressRequest addressRequest)
            throws InstantiationException, IllegalAccessException {
        Address translateAddress = EntityTranslatorUtils.translate(addressRequest, Address.class);
        Address testAddress = addressService.getByExample(Example.of(translateAddress));
        if (Objects.isNull(testAddress)) {
            testAddress = addressService.add(translateAddress);
        }
        return testAddress;
    }

    /**
     * 依据 equipmentId 更新 设备信息
     *
     * @param request 更新表单信息
     * @return 更新后的设备完整信息
     */
    @PostMapping("/update")
    public AirConditionerDetailResponse updateByEquipmentId(@ModelAttribute AirConditionerUpdateRequest request)
            throws IOException, IllegalAccessException, InstantiationException {

        AddressRequest requestAddress = request.getAddress();
        Address address = getAddressByAddressRequest(requestAddress);

        AirConditioner translateAirConditioner = EntityTranslatorUtils.translate(request, AirConditioner.class);
        translateAirConditioner.setAddressString(requestAddress.toSimpleString());
        translateAirConditioner.setAddressId(address.getId());
        AirConditioner airConditioner = airConditionerService.updateByEquipmentId(translateAirConditioner);
        AirConditionerDetailResponse response = airConditioner2AirConditionerDetailResponse(airConditioner);
        return response;
    }

    private AirConditionerDetailResponse airConditioner2AirConditionerDetailResponse(AirConditioner airConditioner) {
        AirConditionerDetailResponse response = new AirConditionerDetailResponse();
        BeanUtils.copyProperties(airConditioner, response);
        response.setEquipmentState(airConditioner.getState());
        response.setWindSpeed(airConditioner.getWindSpeed().getValue());
        Address address = addressService.getById(airConditioner.getAddressId());
        response.setAddress(address);
        UserResponse user = userController.get(airConditioner.getUserId());
        response.setUser(user);
        return response;
    }

    /**
     * 获取 地图树 需要的的数据
     *
     * @param user 当前登录用户
     * @return 分组后的设备信息
     */
    @GetMapping("/map")
    public Map<String, Map<String, List<AirConditionerMapResponse>>> getAirConditionerMapDataByUserRole(
            @SessionAttribute(SessionConsts.LOGIN_USER_KEY) User user) {

        List<AirConditioner> list;
        if (RoleEnum.REPAIRMAN.equals(user.getRole()) || RoleEnum.CUSTOM_SERVICE.equals(user.getRole())) {
            list = airConditionerService.listAll();
        } else {
            list = airConditionerService.listAllByUserId(user.getId());
        }
        List<AirConditionerMapResponse> collect =
                list.stream().map(this::airConditioner2AirConditionerMapResponse).collect(Collectors.toList());

        Map<String, Map<String, List<AirConditionerMapResponse>>> result =
                collect.stream().collect(Collectors.groupingBy(value -> value.getAddress().getProvince(),
                        Collectors.groupingBy(value -> value.getAddress().getCity())));
        return result;
    }

    private AirConditionerMapResponse airConditioner2AirConditionerMapResponse(AirConditioner airConditioner) {
        AirConditionerMapResponse response = new AirConditionerMapResponse();
        BeanUtils.copyProperties(airConditioner, response);
        Address address = addressService.getById(airConditioner.getAddressId());
        response.setAddress(address);
        return response;
    }

    /**
     * 根据用户角色查询设备信息 （不分页）
     *
     * @param user 当前登录用户
     * @return 查询到的设备信息
     */
    @GetMapping
    public List<AirConditionerTableResponse> listByUserRole(@SessionAttribute(SessionConsts.LOGIN_USER_KEY) User user) {
        List<AirConditioner> result;
        if (RoleEnum.REPAIRMAN.equals(user.getRole()) || RoleEnum.CUSTOM_SERVICE.equals(user.getRole())) {
            result = airConditionerService.listAll();
        } else {
            result = airConditionerService.listAllByUserId(user.getId());
        }
        return result.stream().map(this::airConditioner2AirConditionerTableResponse).collect(Collectors.toList());
    }

    @GetMapping("/state/{state}")
    public List<AirConditioner> listByUserIdAndState(@SessionAttribute(SessionConsts.LOGIN_USER_KEY) User user,
            @PathVariable AirConditionerStateEnum state) {
        List<AirConditioner> list = airConditionerService.listByUserIdAndState(user.getId(), state);
        return list;
    }

    private AirConditionerTableResponse airConditioner2AirConditionerTableResponse(AirConditioner airConditioner) {
        AirConditionerTableResponse response = new AirConditionerTableResponse();
        BeanUtils.copyProperties(airConditioner, response);
        Address address = addressService.getById(airConditioner.getAddressId());
        response.setAddress(address.toSimpleString());
        response.setWindSpeed(airConditioner.getWindSpeed().getValue());
        response.setEquipmentState(airConditioner.getState().getValue());
        return response;
    }

    /**
     * 根据用户角色查询设备信息 （分页）
     *
     * @param user  当前登录用户
     * @param page  页码，从 1 开始
     * @param limit 每页行数
     * @return 查询到的设备信息
     */
    @GetMapping("/page")
    public PageResponse<AirConditionerTableResponse> listByUserRole(
            @SessionAttribute(SessionConsts.LOGIN_USER_KEY) User user, @RequestParam int page,
            @RequestParam int limit) {
        // 将前端page参数与查询page参数对应
        page -= 1;
        if (page <= 0 && limit <= 0) {
            List<AirConditionerTableResponse> data = listByUserRole(user);
            return PageResponse.of(data.size(), data);
        }
        Page<AirConditioner> result;
        if (RoleEnum.REPAIRMAN.equals(user.getRole()) || RoleEnum.CUSTOM_SERVICE.equals(user.getRole())) {
            result = airConditionerService.listAll(page, limit);
        } else {
            result = airConditionerService.listAllByUserId(user.getId(), page, limit);
        }
        return PageResponse.of(result, this::airConditioner2AirConditionerTableResponse);
    }

    /**
     * 根据条件查询 设备信息 （不分页）
     *
     * @param user          当前登录用户
     * @param searchRequest 查询条件
     * @return 查询到的设备信息
     */
    @GetMapping("/search")
    public List<AirConditionerTableResponse> listByUserRole(
            @SessionAttribute(SessionConsts.LOGIN_USER_KEY) User user,
            @ModelAttribute AirConditionerSearchRequest searchRequest) {
        if (RoleEnum.ORDINARY.equals(user.getRole()) || RoleEnum.REPAIRMAN.equals(user.getRole())) {
            searchRequest.setUserId(user.getId());
        }
        Specification<AirConditioner> specification = getAirConditionerSearchRequestSpecification(searchRequest);
        List<AirConditioner> result = airConditionerService.listAll(specification);
        return result.stream().map(this::airConditioner2AirConditionerTableResponse).collect(Collectors.toList());
    }

    /**
     * 使用给定的 AirConditionerSearchRequest 参数构造一个 Specification
     *
     * @param searchRequest 给定的条件
     * @return Specification
     */
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
                Interval temperature = searchRequest.getTemperature();
                int min = ObjectUtils.nonNull(temperature.getMin()) ? temperature.getMin().intValue()
                        : Integer.MIN_VALUE;
                int max = ObjectUtils.nonNull(temperature.getMax()) ? temperature.getMax().intValue()
                        : Integer.MAX_VALUE;
                predicates.add(criteriaBuilder.between(root.get("temperature"), min, max));
            }
            if (ObjectUtils.nonNull(searchRequest.getKwh())) {
                Interval kwh = searchRequest.getKwh();
                double min = ObjectUtils.nonNull(kwh.getMin()) ? kwh.getMin().doubleValue() : Double.MIN_VALUE;
                double max = ObjectUtils.nonNull(kwh.getMax()) ? kwh.getMax().doubleValue() : Double.MAX_VALUE;
                predicates.add(criteriaBuilder.between(root.get("kwh"), BigDecimal.valueOf(min),
                        BigDecimal.valueOf(max)));
            }
            if (ObjectUtils.nonNull(searchRequest.getCurrentIntensity())) {
                Interval currentIntensity = searchRequest.getCurrentIntensity();
                double min = ObjectUtils.nonNull(currentIntensity.getMin()) ?
                        currentIntensity.getMin().doubleValue() : Double.MIN_VALUE;
                double max = ObjectUtils.nonNull(currentIntensity.getMax()) ?
                        currentIntensity.getMax().doubleValue() : Double.MAX_VALUE;
                predicates.add(criteriaBuilder.between(root.get("currentIntensity"), BigDecimal.valueOf(min),
                        BigDecimal.valueOf(max)));
            }
            if (ObjectUtils.nonNull(searchRequest.getVoltage())) {
                Interval voltage = searchRequest.getVoltage();
                double min = ObjectUtils.nonNull(voltage.getMin()) ? voltage.getMin().doubleValue()
                        : Double.MIN_VALUE;
                double max = ObjectUtils.nonNull(voltage.getMax()) ? voltage.getMax().doubleValue()
                        : Double.MAX_VALUE;
                predicates.add(criteriaBuilder.between(root.get("voltage"), BigDecimal.valueOf(min),
                        BigDecimal.valueOf(max)));
            }
            if (ObjectUtils.nonNull(searchRequest.getPower())) {
                Interval power = searchRequest.getPower();
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

    /**
     * 根据条件查询 设备信息 （分页）
     *
     * @param user          当前登录用户
     * @param searchRequest 查询条件
     * @param page          页码，从 1 开始
     * @param limit         每页行数
     * @return 查询到的设备信息
     */
    @GetMapping("/page/search")
    public PageResponse<AirConditionerTableResponse> listByUserRole(
            @SessionAttribute(SessionConsts.LOGIN_USER_KEY) User user, AirConditionerSearchRequest searchRequest,
            @RequestParam int page, @RequestParam int limit) {
        // 将前端page参数与查询page参数对应
        page -= 1;
        if (page <= 0 && limit <= 0) {
            List<AirConditionerTableResponse> data = listByUserRole(user, searchRequest);
            return PageResponse.of(data.size(), data);
        }
        if (RoleEnum.ORDINARY.equals(user.getRole()) || RoleEnum.REPAIRMAN.equals(user.getRole())) {
            searchRequest.setUserId(user.getId());
        }
        Specification<AirConditioner> specification = getAirConditionerSearchRequestSpecification(searchRequest);
        Page<AirConditioner> result = airConditionerService.listAll(specification, page, limit);
        return PageResponse.of(result, this::airConditioner2AirConditionerTableResponse);
    }

    /**
     * 根据 equipmentId 获取设备信息
     *
     * @param equipmentId 设备编号
     * @return 设备信息
     */
    @GetMapping("/{equipmentId}")
    public AirConditionerDetailResponse getByEquipmentId(@PathVariable String equipmentId) {
        AirConditioner airConditioner = airConditionerService.getByEquipmentId(equipmentId);
        AirConditionerDetailResponse response = airConditioner2AirConditionerDetailResponse(airConditioner);
        return response;
    }


/*






    // 修改信息

    @PutMapping
    public int update(@Valid AirConditionerVO airConditionerVO) throws IOException {

        return airConditionerService.update(airConditionerVO);
    }

    // 根据查询条件获取空调信息

    @GetMapping
    public List<AirConditionerVO> listAirConditioners(@SessionAttribute(SessionConsts.LOGIN_USER_KEY) User user,
            @RequestParam(required = false) AirConditionerStateEnum state,
            @RequestParam(required = false) String address) {

        // 根据给定的条件 构建一个 AirConditioner 的 probe 实例
        AirConditioner probe = AirConditioner.builder().address(address).state(state).build();
        if (RoleEnum.ORDINARY.equals(user.getRole())) {
            probe.setUserId(user.getId());
        }
        return airConditionerService.listAirConditioners(probe);
    }

    @GetMapping("/search")
    public Map<String, Object> listAllAirConditioners() {
        Map<String, Object> map = new HashMap<>(2);
        map.put("code", "0");
        map.put("data", airConditionerService.listAll());
        return map;
    }
*/
}
