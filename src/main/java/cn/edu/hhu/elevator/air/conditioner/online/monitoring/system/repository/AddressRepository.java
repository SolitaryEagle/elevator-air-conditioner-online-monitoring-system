package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.repository;

import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 覃国强
 * @date 2019/5/1 16:55
 */
public interface AddressRepository extends JpaRepository<Address, Long> {
}
