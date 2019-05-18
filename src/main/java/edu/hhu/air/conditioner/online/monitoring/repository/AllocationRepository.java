package edu.hhu.air.conditioner.online.monitoring.repository;

import edu.hhu.air.conditioner.online.monitoring.model.entity.Allocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author 覃国强
 * @date 2019-01-31
 */
public interface AllocationRepository extends JpaRepository<Allocation, Long> {

    List<Allocation> findByRepairUserId(Long userId);

    Optional<Allocation> findByFaultId(Long faultId);

}
