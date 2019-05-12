package edu.hhu.air.conditioner.online.monitoring.repository;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.FaultStateEnum;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Fault;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author 覃国强
 * @date 2019-01-31
 */
public interface FaultRepository extends JpaRepository<Fault, Long> {

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update Fault set realName = :#{#fault.realName}, phoneNumber = :#{#fault.phoneNumber}, " +
            "contactAddress = :#{#fault.contactAddress}, type = :#{#fault.type}, " +
            "description = :#{#fault.description}, gmtModified = :#{#fault.gmtModified} " +
            "where id = :#{#fault.id}")
    int updateById(Fault fault);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update Fault set state = :#{#fault.state}, gmtModified = :#{#fault.gmtModified} " +
            "where id = :#{#fault.id}")
    int updateStateById(Fault fault);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update Fault set repairResult = :#{#fault.repairResult}, gmtModified = :#{#fault.gmtModified} " +
            "where id = :#{#fault.id}")
    int updateRepairResultById(Fault fault);

    List<Fault> findByReportUserId(Long reportUserId);

    List<Fault> findByState(FaultStateEnum state);

    List<Fault> findByAirConditionerId(Long airConditionerId);

}
