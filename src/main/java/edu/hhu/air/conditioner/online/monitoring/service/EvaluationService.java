package edu.hhu.air.conditioner.online.monitoring.service;

import edu.hhu.air.conditioner.online.monitoring.model.entity.Evaluation;

/**
 * @author 覃国强
 * @date 2019/5/17 16:30
 */
public interface EvaluationService {

    Evaluation add(Evaluation evaluation);

    Evaluation getByFaultId(Long faultId);

}
