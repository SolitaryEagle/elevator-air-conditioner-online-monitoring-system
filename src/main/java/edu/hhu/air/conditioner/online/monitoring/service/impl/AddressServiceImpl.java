package edu.hhu.air.conditioner.online.monitoring.service.impl;

import edu.hhu.air.conditioner.online.monitoring.model.dto.AddressDTO;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Address;
import edu.hhu.air.conditioner.online.monitoring.service.AddressService;
import edu.hhu.air.conditioner.online.monitoring.repository.AddressRepository;
import edu.hhu.air.conditioner.online.monitoring.util.TimeStampUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author 覃国强
 * @date 2019/5/2 16:33
 */
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Address add(AddressDTO addressDTO) {
        Address address = new Address();
        BeanUtils.copyProperties(addressDTO, address);
        address.setGmtCreate(TimeStampUtils.now());
        address.setGmtModified(TimeStampUtils.now());
        return addressRepository.save(address);
    }

    @Override
    public Optional<Address> findOne(Example<Address> example) {
        return addressRepository.findOne(example);
    }

}
