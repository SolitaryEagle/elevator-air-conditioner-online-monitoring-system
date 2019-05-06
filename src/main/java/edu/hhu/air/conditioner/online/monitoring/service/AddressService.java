package edu.hhu.air.conditioner.online.monitoring.service;

import edu.hhu.air.conditioner.online.monitoring.model.dto.AddressDTO;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Address;
import org.springframework.data.domain.Example;

import java.util.Optional;

/**
 * @author 覃国强
 * @date 2019/5/2 16:33
 */
public interface AddressService {

    Address add(AddressDTO addressDTO);

    Optional<Address> findOne(Example<Address> example);

}
