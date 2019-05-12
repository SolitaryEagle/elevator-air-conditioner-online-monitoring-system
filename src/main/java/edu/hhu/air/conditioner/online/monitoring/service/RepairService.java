package edu.hhu.air.conditioner.online.monitoring.service;

import edu.hhu.air.conditioner.online.monitoring.model.entity.Repair;

import java.util.List;

/**
 * @author 覃国强
 * @date 2019/5/17 11:26
 */
public interface RepairService {

    Repair add(Repair repair);

    List<Repair> listAll();

    Repair getByFaultId(Long faultId);

}
