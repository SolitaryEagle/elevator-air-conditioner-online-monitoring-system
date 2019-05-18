package edu.hhu.air.conditioner.online.monitoring.controller;

import edu.hhu.air.conditioner.online.monitoring.constant.SessionConsts;
import edu.hhu.air.conditioner.online.monitoring.constant.UrlMappingConsts;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Repair;
import edu.hhu.air.conditioner.online.monitoring.model.entity.User;
import edu.hhu.air.conditioner.online.monitoring.model.request.RepairAddRequest;
import edu.hhu.air.conditioner.online.monitoring.service.RepairService;
import edu.hhu.air.conditioner.online.monitoring.util.EntityTranslatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

/**
 * @author 覃国强
 * @date 2019/5/17 11:22
 */
@RestController
@RequestMapping(UrlMappingConsts.REPAIR_BASE_MAPPING_V1)
public class RepairController {

    @Autowired
    private RepairService repairService;

    @PostMapping("/add")
    public void add(@SessionAttribute(SessionConsts.LOGIN_USER_KEY) User user,
            @ModelAttribute RepairAddRequest request) throws InstantiationException, IllegalAccessException {

        Repair translate = EntityTranslatorUtils.translate(request, Repair.class);
        translate.setRepairUserId(user.getId());

        repairService.add(translate);

    }

}
