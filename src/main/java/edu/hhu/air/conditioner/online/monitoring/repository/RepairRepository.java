package edu.hhu.air.conditioner.online.monitoring.repository;

import edu.hhu.air.conditioner.online.monitoring.model.entity.Repair;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author 覃国强
 * @date 2019/5/12 15:58
 */
public interface RepairRepository extends JpaRepository<Repair, Long> {

    Optional<Repair> findByFaultId(Long faultId);

}
