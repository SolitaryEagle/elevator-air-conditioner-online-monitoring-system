package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.service.impl;

import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.constant.AirConditionerStateEnum;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.exception.FaultException;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.exception.ResponseCode;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.entity.Fault;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.vo.FaultVO;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.repository.AirConditionerRepository;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.repository.FaultRepository;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.service.FaultService;
import java.sql.Timestamp;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 覃国强
 * @date 2019-02-13
 */
@Slf4j
@Service("faultService")
public class FaultServiceImpl implements FaultService {

    private final FaultRepository faultRepository;
    private final AirConditionerRepository airConditionerRepository;

    @Autowired
    public FaultServiceImpl(FaultRepository faultRepository, AirConditionerRepository airConditionerRepository) {
        this.faultRepository = faultRepository;
        this.airConditionerRepository = airConditionerRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Fault save(FaultVO faultVO) {

        // 设置默认数据
        faultVO.setGmtCreate(new Timestamp(System.currentTimeMillis()));
        faultVO.setGmtModified(new Timestamp(System.currentTimeMillis()));
        faultVO.setHandle(Boolean.FALSE);

        Fault fault = new Fault();
        BeanUtils.copyProperties(faultVO, fault);
        return faultRepository.save(fault);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRepairUserId(FaultVO faultVO) {

        faultVO.setGmtModified(new Timestamp(System.currentTimeMillis()));
        faultRepository.updateRepairUserId(faultVO.getId(), faultVO.getRepairUserId(), faultVO.getGmtModified());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRepairInfo(FaultVO faultVO) {

        switch (faultVO.getRepairResult()) {
            // 维修成功
            case SUCCESS:
                airConditionerRepository
                        .updateFaultInfo(faultVO.getAirConditionerId(), AirConditionerStateEnum.GOOD, "");
                log.info("维修成功");
                break;
            // 维修失败，重新插入一条记录
            case FAILURE:
                Optional<Fault> optional = faultRepository.findById(faultVO.getId());
                if (!optional.isPresent()) {
                    log.error("id为 {} 的Fault不存在", faultVO.getId());
                    throw new FaultException(ResponseCode.MISSING, "id", "id为 " + faultVO.getId() + " 的Fault不存在");
                }
                Fault fault = optional.get();
                FaultVO saveFaultVO =
                        FaultVO.builder().description(fault.getDescription()).faultType(fault.getFaultType())
                                .airConditionerId(fault.getAirConditionerId()).build();
                save(saveFaultVO);
                break;
            default:
                log.error("维修结果填写错误，{}", faultVO.getRepairResult());
                throw new FaultException(ResponseCode.INVALID, "repairResult", "维修结果无效");
        }
        faultVO.setGmtModified(new Timestamp(System.currentTimeMillis()));
        faultVO.setGmtRepair(new Timestamp(System.currentTimeMillis()));
        faultRepository.updateRepairInfo(faultVO.getId(), faultVO.getHandle(), faultVO.getRepairRecord(),
                faultVO.getRepairResult(), faultVO.getGmtRepair(), faultVO.getRepairMaterial(),
                faultVO.getGmtModified());
    }

    @Override
    public void updateRevisitInfo(FaultVO faultVO) {

        faultVO.setGmtModified(new Timestamp(System.currentTimeMillis()));
        faultRepository.updateRevisitInfo(faultVO.getId(), faultVO.getRevisitUserId(), faultVO.getRevisitResult(),
                faultVO.getGmtRevisit(), faultVO.getGmtModified());
    }

    @Override
    public void updateEvaluationInfo(FaultVO faultVO) {

        faultVO.setGmtEvaluation(new Timestamp(System.currentTimeMillis()));
        faultVO.setGmtModified(new Timestamp(System.currentTimeMillis()));
        faultRepository
                .updateEvaluationInfo(faultVO.getId(), faultVO.getEvaluationUserId(), faultVO.getUserEvaluation(),
                        faultVO.getUserSatisfaction(), faultVO.getGmtEvaluation(),
                        faultVO.getGmtModified());
    }

}
