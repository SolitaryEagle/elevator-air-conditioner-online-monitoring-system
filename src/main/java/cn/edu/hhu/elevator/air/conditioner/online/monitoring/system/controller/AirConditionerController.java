package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.controller;

import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.constant.AirConditionerStateEnum;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.constant.RequestConsts;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.constant.RoleEnum;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.constant.SessionConsts;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.exception.BusinessException;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.dto.AirConditionerDTO;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.entity.AirConditioner;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.entity.User;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.request.AirConditionerRequest;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.response.AirConditionerResponse;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.vo.AirConditionerVO;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.service.AirConditionerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // 根据用户角色查询设备信息

    @GetMapping("/list")
    @ResponseBody
    public List<AirConditionerResponse> listByUserRole(@SessionAttribute(SessionConsts.LOGIN_USER_KEY) User user) {
        if (RoleEnum.ADMINISTRATOR.equals(user.getRole()) || RoleEnum.CUSTOM_SERVICE.equals(user.getRole())) {
            return airConditionerService.listAll();
        } else {
            return airConditionerService.listByUserId(user.getId());
        }
    }

    @GetMapping("/page/list")
    @ResponseBody
    public List<AirConditionerResponse> listByUserRole(@SessionAttribute(SessionConsts.LOGIN_USER_KEY) User user,
            @RequestParam int page, @RequestParam int limit) {
        if (page == 0 && limit == 0) {
            return listByUserRole(user);
        }
        if (RoleEnum.ADMINISTRATOR.equals(user.getRole()) || RoleEnum.CUSTOM_SERVICE.equals(user.getRole())) {
            return airConditionerService.listAll(page * limit, limit);
        } else {
            return airConditionerService.listLimitByUserId(user.getId(), (page - 1) * limit, limit);
        }
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
