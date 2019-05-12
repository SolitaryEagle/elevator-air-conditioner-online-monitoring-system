package edu.hhu.air.conditioner.online.monitoring.service.impl;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.ErrorCodeEnum;
import edu.hhu.air.conditioner.online.monitoring.constant.enums.FaultStateEnum;
import edu.hhu.air.conditioner.online.monitoring.constant.enums.RepairResultEnum;
import edu.hhu.air.conditioner.online.monitoring.exception.BusinessException;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Evaluation;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Fault;
import edu.hhu.air.conditioner.online.monitoring.repository.EvaluationRepository;
import edu.hhu.air.conditioner.online.monitoring.service.EvaluationService;
import edu.hhu.air.conditioner.online.monitoring.service.FaultService;
import edu.hhu.air.conditioner.online.monitoring.util.TimestampUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author 覃国强
 * @date 2019/5/17 16:30
 */
@Slf4j
@Service
public class EvaluationServiceImpl implements EvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private FaultService faultService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Evaluation add(Evaluation evaluation) {

        evaluation.setGmtCreate(TimestampUtils.now());
        evaluation.setGmtModified(TimestampUtils.now());

        // 将 fault 的 state 设置为 END
        Fault fault = Fault.builder().id(evaluation.getFaultId()).state(FaultStateEnum.END).build();
        faultService.updateStateById(fault);

        // 将 fault 的 repair_result 修改为 SUCCESS
        fault.setRepairResult(RepairResultEnum.SUCCESS);
        faultService.updateRepairResultById(fault);

        Evaluation saveEvaluation = evaluationRepository.save(evaluation);
        log.info("Evaluation 保存成功！");

        return saveEvaluation;
    }

    @Override
    public Evaluation getByFaultId(Long faultId) {
        Optional<Evaluation> optional = evaluationRepository.findByFaultId(faultId);
        if (!optional.isPresent()) {
            log.warn("根据 faultId 获取 Evaluation 失败。faultId：{}", faultId);
            return null;
        }
        log.info("根据 faultId 获取 Evaluation 成功。faultId：{}", faultId);
        return optional.get();
    }

}
