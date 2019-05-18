package edu.hhu.air.conditioner.online.monitoring.service;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.FaultStateEnum;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Fault;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author 覃国强
 * @date 2019-02-13
 */
public interface FaultService {

    Fault add(Fault fault);

    void deleteById(Long id);

    void updateById(Fault fault);

    int updateStateById(Fault fault);

    int updateRepairResultById(Fault fault);

    List<Fault> listAll();

    List<Fault> listAllByReportUserId(Long reportUserId);

    Page<Fault> listAll(int page, int size);

    Page<Fault> listAllByReportUserId(Long reportUserId, int page, int size);

    List<Fault> listAll(Example<Fault> example);

    Fault getById(Long id);

    List<Fault> listAllByState(FaultStateEnum state);

    List<Fault> listByAirConditionerId(Long airConditionerId);

}
