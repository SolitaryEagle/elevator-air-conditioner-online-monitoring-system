package edu.hhu.air.conditioner.online.monitoring.controller;

import edu.hhu.air.conditioner.online.monitoring.constant.SessionConsts;
import edu.hhu.air.conditioner.online.monitoring.constant.UrlMappingConsts;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Evaluation;
import edu.hhu.air.conditioner.online.monitoring.model.entity.User;
import edu.hhu.air.conditioner.online.monitoring.model.request.EvaluationAddRequest;
import edu.hhu.air.conditioner.online.monitoring.service.EvaluationService;
import edu.hhu.air.conditioner.online.monitoring.util.EntityTranslatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

/**
 * @author 覃国强
 * @date 2019/5/17 16:46
 */
@RestController
@RequestMapping(UrlMappingConsts.EVALUATION_BASE_MAPPING_V1)
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @PostMapping("/add")
    public void add(@SessionAttribute(SessionConsts.LOGIN_USER_KEY) User user,
            @ModelAttribute EvaluationAddRequest request) throws InstantiationException, IllegalAccessException {
        Evaluation translate = EntityTranslatorUtils.translate(request, Evaluation.class);
        translate.setEvaluationUserId(user.getId());

        evaluationService.add(translate);
    }

}
