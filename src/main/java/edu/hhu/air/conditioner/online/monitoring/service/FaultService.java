package edu.hhu.air.conditioner.online.monitoring.service;

import edu.hhu.air.conditioner.online.monitoring.model.entity.Fault;
import edu.hhu.air.conditioner.online.monitoring.model.vo.FaultVO;

/**
 * @author 覃国强
 * @date 2019-02-13
 */
public interface FaultService {

    Fault save(FaultVO faultVO);

    void updateRepairUserId(FaultVO faultVO);

    void updateRepairInfo(FaultVO faultVO);

    void updateRevisitInfo(FaultVO faultVO);

    void updateEvaluationInfo(FaultVO faultVO);

}
