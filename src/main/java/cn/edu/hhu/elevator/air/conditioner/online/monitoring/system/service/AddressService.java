package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.service;

import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.dto.AddressDTO;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.entity.Address;
import org.springframework.data.domain.Example;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author 覃国强
 * @date 2019/5/2 16:33
 */
public interface AddressService {

    Address add(AddressDTO addressDTO);

    Optional<Address> findOne(Example<Address> example);

}
