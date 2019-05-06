package edu.hhu.air.conditioner.online.monitoring.repository;

import edu.hhu.air.conditioner.online.monitoring.constant.RepairResultEnum;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Fault;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author 覃国强
 * @date 2019-01-31
 */
public interface FaultRepository extends JpaRepository<Fault, Long> {

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update Fault set repairUserId = :repairUserId, gmtModified = :gmtModified where id = :id")
    int updateRepairUserId(Long id, Long repairUserId, Timestamp gmtModified);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update Fault set handle = :handle, repairRecord = :repairRecord, repairResult = :repairResult, gmtRepair = "
            + ":gmtRepair, repairMaterial = :repairMaterial, gmtModified = :gmtModified where id = :id")
    int updateRepairInfo(Long id, boolean handle, String repairRecord, RepairResultEnum repairResult,
            Timestamp gmtRepair, String repairMaterial, Timestamp gmtModified);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update Fault set revisitUserId = :revisitUserId, revisitResult = :revisitResult, gmtRevisit = :gmtRevisit, "
            + "gmtModified = :gmtModified where id = :id")
    int updateRevisitInfo(Long id, Long revisitUserId, String revisitResult, Timestamp gmtRevisit,
            Timestamp gmtModified);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update Fault set evaluationUserId = :evaluationUserId, userEvaluation = :userEvaluation, userSatisfaction ="
            + " :userSatisfaction, gmtEvaluation = :gmtEvaluation, gmtModified = :gmtModified where id = :id")
    int updateEvaluationInfo(Long id, Long evaluationUserId, String userEvaluation, Integer userSatisfaction,
            Timestamp gmtEvaluation, Timestamp gmtModified);

}
