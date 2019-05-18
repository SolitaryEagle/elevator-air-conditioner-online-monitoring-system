package edu.hhu.air.conditioner.online.monitoring.service;

import edu.hhu.air.conditioner.online.monitoring.model.entity.Address;
import org.springframework.data.domain.Example;

import java.util.List;

/**
 * @author 覃国强
 * @date 2019/5/2 16:33
 */
public interface AddressService {

    Address add(Address address);

    Address getByExample(Example<Address> example);

    Address getById(Long id);

    List<Address> listAll();

}
