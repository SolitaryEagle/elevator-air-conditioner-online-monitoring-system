package edu.hhu.air.conditioner.online.monitoring.service.impl;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.ErrorCodeEnum;
import edu.hhu.air.conditioner.online.monitoring.exception.AddressException;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Address;
import edu.hhu.air.conditioner.online.monitoring.repository.AddressRepository;
import edu.hhu.air.conditioner.online.monitoring.service.AddressService;
import edu.hhu.air.conditioner.online.monitoring.util.TimeStampUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author 覃国强
 * @date 2019/5/2 16:33
 */
@Slf4j
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Address add(Address address) {
        address.setGmtCreate(TimeStampUtils.now());
        address.setGmtModified(TimeStampUtils.now());
        return addressRepository.save(address);
    }

    @Override
    public Address getByExample(Example<Address> example) {
        Optional<Address> optional = addressRepository.findOne(example);
        if (optional.isPresent()) {
            return optional.get();
        }
        log.debug("地址不存在！example：{}", example);
        return null;
    }

    @Override
    public Address getById(Long id) {
        Optional<Address> optional = addressRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
        log.warn("地址不存在！addressId：{}", id);
        throw new AddressException(ErrorCodeEnum.MISSING, "addressId", "地址不存在！");
    }

}
