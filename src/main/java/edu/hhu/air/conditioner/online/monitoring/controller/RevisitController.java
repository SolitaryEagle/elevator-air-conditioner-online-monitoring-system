package edu.hhu.air.conditioner.online.monitoring.controller;

import edu.hhu.air.conditioner.online.monitoring.constant.SessionConsts;
import edu.hhu.air.conditioner.online.monitoring.constant.UrlMappingConsts;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Revisit;
import edu.hhu.air.conditioner.online.monitoring.model.entity.User;
import edu.hhu.air.conditioner.online.monitoring.model.request.RevisitAddRequest;
import edu.hhu.air.conditioner.online.monitoring.service.RevisitService;
import edu.hhu.air.conditioner.online.monitoring.util.EntityTranslatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

/**
 * @author 覃国强
 * @date 2019/5/17 15:24
 */
@RestController
@RequestMapping(UrlMappingConsts.REVISIT_BASE_MAPPING_V1)
public class RevisitController {

    @Autowired
    private RevisitService revisitService;

    @PostMapping("/add")
    public void add(@SessionAttribute(SessionConsts.LOGIN_USER_KEY) User user,
            @ModelAttribute RevisitAddRequest request) throws InstantiationException, IllegalAccessException {

        Revisit translate = EntityTranslatorUtils.translate(request, Revisit.class);
        translate.setRevisitUserId(user.getId());

        revisitService.add(translate);

    }

}
