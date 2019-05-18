package edu.hhu.air.conditioner.online.monitoring.service.impl;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.ErrorCodeEnum;
import edu.hhu.air.conditioner.online.monitoring.constant.enums.FaultStateEnum;
import edu.hhu.air.conditioner.online.monitoring.exception.BusinessException;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Allocation;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Fault;
import edu.hhu.air.conditioner.online.monitoring.repository.AllocationRepository;
import edu.hhu.air.conditioner.online.monitoring.service.AllocationService;
import edu.hhu.air.conditioner.online.monitoring.service.FaultService;
import edu.hhu.air.conditioner.online.monitoring.util.TimestampUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author 覃国强
 * @date 2019/5/16 10:25
 */
@Slf4j
@Service
public class AllocationServiceImpl implements AllocationService {

    @Autowired
    private AllocationRepository allocationRepository;

    @Autowired
    private FaultService faultService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Allocation add(Allocation allocation) {

        // 更新 Fault 的 state
        Fault fault = Fault.builder().id(allocation.getFaultId()).state(FaultStateEnum.ALLOCATED).build();
        faultService.updateStateById(fault);

        allocation.setGmtCreate(TimestampUtils.now());
        allocation.setGmtModified(TimestampUtils.now());

        Allocation saveAllocation = allocationRepository.save(allocation);
        log.info("分配保存成功。id：{}", saveAllocation.getId());

        return saveAllocation;
    }

    @Override
    public List<Allocation> listByRepairUserId(Long userId) {
        List<Allocation> list = allocationRepository.findByRepairUserId(userId);
        log.info("根据 repairUserId 获取 Allocation 成功！repairUserId：{}", userId);
        return list;
    }

    @Override
    public Allocation getByFaultId(Long faultId) {
        Optional<Allocation> optional = allocationRepository.findByFaultId(faultId);
        if (!optional.isPresent()) {
            log.warn("根据faultId获取Allocation失败，faultId：{}", faultId);
            return null;
        }
        log.info("根据faultId获取Allocation成功，faultId：{}", faultId);
        return optional.get();
    }

}
