package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.controller;

import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.constant.RoleEnum;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.constant.SessionConsts;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.exception.FaultException;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.exception.ResponseCode;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.entity.User;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.vo.FaultVO;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.service.FaultService;
import java.sql.Timestamp;
import java.util.Objects;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

/**
 * @author 覃国强
 * @date 2019-02-12
 */
@Slf4j
@RestController
@RequestMapping("/v1/monitoring-system/faults")
public class FaultController {

    private final FaultService faultService;

    @Autowired
    public FaultController(FaultService faultService) {
        this.faultService = faultService;
    }

    // 报修

    @PostMapping
    public void reportFault(@SessionAttribute(SessionConsts.LOGIN_USER_KEY) User user, @Valid FaultVO faultVO) {

        // 校验
        boolean valid = RoleEnum.CUSTOM_SERVICE.equals(user.getRole()) && (Objects.isNull(faultVO.getRepairUserId())
                || faultVO.getRepairUserId() <= 0);
        if (valid) {
            throw new FaultException(ResponseCode.MISSING, "repairUserId", "缺少维修工");
        }
        faultVO.setReportUserId(user.getId());
        faultService.save(faultVO);
    }

    // 客服安排维修工

    @PutMapping("/others")
    public void arrangeRepairUser(@Valid FaultVO faultVO) {

        if (Objects.isNull(faultVO.getRepairUserId()) || faultVO.getRepairUserId() <= 0) {
            throw new FaultException(ResponseCode.MISSING, "repairUserId", "缺少维修工");
        }
        faultService.updateRepairUserId(faultVO);
    }

    // 维修工自己接单

    @PutMapping("/oneself")
    public void arrangedRepairUser(@SessionAttribute(SessionConsts.LOGIN_USER_KEY) User user, @Valid FaultVO faultVO) {

        faultVO.setRepairUserId(user.getId());
        faultService.updateRepairUserId(faultVO);
    }

    // 维修工上门维修

    @PutMapping("/works")
    public void repair(@Valid FaultVO faultVO) {

        // 校验
        if (Objects.isNull(faultVO.getRepairResult())) {
            throw new FaultException(ResponseCode.MISSING, "repairResult", "缺少维修结果");
        }
        if (StringUtils.isBlank(faultVO.getRepairRecord())) {
            throw new FaultException(ResponseCode.MISSING, "repairRecord", "缺少维修记录");
        }
        if (StringUtils.isBlank(faultVO.getRepairMaterial())) {
            throw new FaultException(ResponseCode.MISSING, "repairMaterial", "缺少维修材料");
        }

        faultService.updateRepairInfo(faultVO);
    }

    // 客服回访

    @PutMapping("/revisits")
    public void revisit(@SessionAttribute(SessionConsts.LOGIN_USER_KEY) User user,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Timestamp gmtRevisit, String revisitResult) {

        FaultVO faultVO =
                FaultVO.builder().revisitUserId(user.getId()).revisitResult(revisitResult).gmtRevisit(gmtRevisit)
                        .build();
        faultService.updateRevisitInfo(faultVO);
    }

    // 用户评价

    @PutMapping("/evaluations")
    public void evaluate(@SessionAttribute(SessionConsts.LOGIN_USER_KEY) User user, String userEvaluation,
            int userSatisfaction) {

        FaultVO faultVO = FaultVO.builder().evaluationUserId(user.getId()).userEvaluation(userEvaluation)
                .userSatisfaction(userSatisfaction).build();
        faultService.updateEvaluationInfo(faultVO);
    }

}
