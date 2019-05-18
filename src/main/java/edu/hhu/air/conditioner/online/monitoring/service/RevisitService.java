package edu.hhu.air.conditioner.online.monitoring.service;

import edu.hhu.air.conditioner.online.monitoring.model.entity.Revisit;

import java.util.List;

/**
 * @author 覃国强
 * @date 2019/5/17 15:26
 */
public interface RevisitService {

    Revisit add(Revisit revisit);

    List<Revisit> listAll();

    Revisit getByFaultId(Long faultId);

}
