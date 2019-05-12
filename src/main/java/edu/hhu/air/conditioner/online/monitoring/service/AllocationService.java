package edu.hhu.air.conditioner.online.monitoring.service;

import edu.hhu.air.conditioner.online.monitoring.model.entity.Allocation;

import java.util.List;

/**
 * @author 覃国强
 * @date 2019/5/16 10:25
 */
public interface AllocationService {

    Allocation add(Allocation allocation);

    List<Allocation> listByRepairUserId(Long userId);

    Allocation getByFaultId(Long faultId);

}
