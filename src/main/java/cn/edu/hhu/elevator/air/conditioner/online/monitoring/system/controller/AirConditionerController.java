package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.controller;

import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.constant.AirConditionerStateEnum;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.constant.RoleEnum;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.constant.SessionConsts;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.exception.BusinessException;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.exception.ResponseCode;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.entity.AirConditioner;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.entity.User;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.vo.AirConditionerVO;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.service.AirConditionerService;
import java.io.IOException;
import java.util.List;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

/**
 * @author 覃国强
 * @date 2019-02-20
 */
@RestController
@RequestMapping("/v1/monitoring-system/air-conditioners")
public class AirConditionerController {

    private final AirConditionerService airConditionerService;

    @Autowired
    public AirConditionerController(AirConditionerService airConditionerService) {
        this.airConditionerService = airConditionerService;
    }

    // 信息录入

    @PostMapping
    public AirConditioner save(@SessionAttribute(SessionConsts.LOGIN_USER_KEY) User user,
            @Valid AirConditionerVO airConditionerVO)
            throws IOException {

        airConditionerVO.setUser(user);
        return airConditionerService.save(airConditionerVO);
    }

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

}
