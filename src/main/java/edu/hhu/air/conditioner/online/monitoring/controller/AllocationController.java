package edu.hhu.air.conditioner.online.monitoring.controller;

import edu.hhu.air.conditioner.online.monitoring.constant.SessionConsts;
import edu.hhu.air.conditioner.online.monitoring.constant.UrlMappingConsts;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Allocation;
import edu.hhu.air.conditioner.online.monitoring.model.entity.User;
import edu.hhu.air.conditioner.online.monitoring.model.request.AllocationAddRequest;
import edu.hhu.air.conditioner.online.monitoring.service.AllocationService;
import edu.hhu.air.conditioner.online.monitoring.util.EntityTranslatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

/**
 * @author 覃国强
 * @date 2019/5/16 10:05
 */
@RestController
@RequestMapping(UrlMappingConsts.ALLOCATION_BASE_MAPPING_V1)
public class AllocationController {

    @Autowired
    private AllocationService allocationService;


    @PostMapping("/add")
    public Allocation add(@SessionAttribute(SessionConsts.LOGIN_USER_KEY) User user,
            @ModelAttribute AllocationAddRequest request) throws InstantiationException, IllegalAccessException {
        Allocation translate = EntityTranslatorUtils.translate(request, Allocation.class);
        translate.setAllocationUserId(user.getId());

        allocationService.add(translate);

        return null;
    }

}
