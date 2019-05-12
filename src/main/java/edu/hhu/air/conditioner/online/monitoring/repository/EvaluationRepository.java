package edu.hhu.air.conditioner.online.monitoring.repository;

import edu.hhu.air.conditioner.online.monitoring.model.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author 覃国强
 * @date 2019/5/12 16:33
 */
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    Optional<Evaluation> findByFaultId(Long faultId);

}
