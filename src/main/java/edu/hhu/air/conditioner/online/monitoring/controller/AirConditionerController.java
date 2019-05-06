package edu.hhu.air.conditioner.online.monitoring.controller;

import edu.hhu.air.conditioner.online.monitoring.constant.RequestConsts;
import edu.hhu.air.conditioner.online.monitoring.constant.RoleEnum;
import edu.hhu.air.conditioner.online.monitoring.constant.SessionConsts;
import edu.hhu.air.conditioner.online.monitoring.model.dto.AirConditionerDTO;
import edu.hhu.air.conditioner.online.monitoring.model.entity.User;
import edu.hhu.air.conditioner.online.monitoring.model.request.AirConditionerRequest;
import edu.hhu.air.conditioner.online.monitoring.model.request.AirConditionerSearchRequest;
import edu.hhu.air.conditioner.online.monitoring.model.response.PageResponse;
import edu.hhu.air.conditioner.online.monitoring.service.AirConditionerService;
import edu.hhu.air.conditioner.online.monitoring.exception.BusinessException;
import edu.hhu.air.conditioner.online.monitoring.model.response.AirConditionerResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @author 覃国强
 * @date 2019-02-20
 */
@Controller
@RequestMapping("/v1/monitoring-system/air-conditioners")
public class AirConditionerController {

    @Autowired
    private AirConditionerService airConditionerService;

    // 信息录入

    @PostMapping("/add")
    public String add(@SessionAttribute(SessionConsts.LOGIN_USER_KEY) User user,
            @ModelAttribute AirConditionerRequest airConditionerRequest, HttpServletRequest request) {
        request.setAttribute(RequestConsts.AIR_CONDITIONER_ADD_FORM_KEY, airConditionerRequest);
        AirConditionerDTO airConditionerDTO = new AirConditionerDTO();
        BeanUtils.copyProperties(airConditionerRequest, airConditionerDTO);
        airConditionerDTO.setUser(user);
        try {
            airConditionerService.add(airConditionerDTO);
        } catch (BusinessException | IOException e) {
            request.setAttribute(RequestConsts.TIP_KEY, e.getMessage());
            return "air-conditioner/add-info";
        }
        return "map/address-catalogue";
    }

    // 根据 id 获取设备信息

    @GetMapping("/{id}")
    @ResponseBody
    public String getById(@PathVariable Long id) {
        // airConditionerService.getById(id);
        return null;
    }


    // 根据用户角色查询设备信息

    @GetMapping
    @ResponseBody
    public List<AirConditionerResponse> listByUserRole(@SessionAttribute(SessionConsts.LOGIN_USER_KEY) User user) {
        if (RoleEnum.ADMINISTRATOR.equals(user.getRole()) || RoleEnum.CUSTOM_SERVICE.equals(user.getRole())) {
            return airConditionerService.listAll();
        } else {
            return airConditionerService.listAllByUserId(user.getId());
        }
    }

    @GetMapping("/search")
    @ResponseBody
    public List<AirConditionerResponse> listByUserRole(
            @SessionAttribute(SessionConsts.LOGIN_USER_KEY) User user,
            @ModelAttribute AirConditionerSearchRequest searchRequest) {
        if (RoleEnum.ORDINARY.equals(user.getRole()) || RoleEnum.REPAIRMAN.equals(user.getRole())) {
            searchRequest.setUserId(user.getId());
        }
        return airConditionerService.listAll(searchRequest);
    }

    @GetMapping("/page")
    @ResponseBody
    public PageResponse<AirConditionerResponse> listByUserRole(
            @SessionAttribute(SessionConsts.LOGIN_USER_KEY) User user,
            @RequestParam int page, @RequestParam int limit) {
        // 将前端page参数与查询page参数对应
        page -= 1;
        if (page <= 0 && limit <= 0) {
            List<AirConditionerResponse> data = listByUserRole(user);
            return PageResponse.of(data.size(), data);
        }
        if (RoleEnum.ADMINISTRATOR.equals(user.getRole()) || RoleEnum.CUSTOM_SERVICE.equals(user.getRole())) {
            return airConditionerService.listAll(page, limit);
        } else {
            return airConditionerService.listAllByUserId(user.getId(), page, limit);
        }

    }

    @GetMapping("/page/search")
    @ResponseBody
    public PageResponse<AirConditionerResponse> listByUserRole(
            @SessionAttribute(SessionConsts.LOGIN_USER_KEY) User user, AirConditionerSearchRequest searchRequest,
            @RequestParam int page, @RequestParam int limit) {
        // 将前端page参数与查询page参数对应
        page -= 1;
        if (page <= 0 && limit <= 0) {
            List<AirConditionerResponse> data = listByUserRole(user, searchRequest);
            return PageResponse.of(data.size(), data);
        }
        if (RoleEnum.ORDINARY.equals(user.getRole()) || RoleEnum.REPAIRMAN.equals(user.getRole())) {
            searchRequest.setUserId(user.getId());
        }
        return airConditionerService.listAll(searchRequest, page, limit);
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
