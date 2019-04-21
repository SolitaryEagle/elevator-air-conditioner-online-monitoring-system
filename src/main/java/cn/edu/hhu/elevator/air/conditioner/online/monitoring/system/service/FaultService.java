package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.service;

import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.entity.Fault;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.vo.FaultVO;

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
