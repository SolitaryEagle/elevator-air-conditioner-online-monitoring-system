package edu.hhu.air.conditioner.online.monitoring.repository;

import edu.hhu.air.conditioner.online.monitoring.model.entity.Revisit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author 覃国强
 * @date 2019/5/12 16:24
 */
public interface RevisitRepository extends JpaRepository<Revisit, Long> {

    Optional<Revisit> findByFaultId(Long faultId);

}
