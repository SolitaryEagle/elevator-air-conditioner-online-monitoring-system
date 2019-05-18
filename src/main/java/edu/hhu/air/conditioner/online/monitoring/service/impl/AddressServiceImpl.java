package edu.hhu.air.conditioner.online.monitoring.service.impl;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.ErrorCodeEnum;
import edu.hhu.air.conditioner.online.monitoring.exception.AddressException;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Address;
import edu.hhu.air.conditioner.online.monitoring.repository.AddressRepository;
import edu.hhu.air.conditioner.online.monitoring.service.AddressService;
import edu.hhu.air.conditioner.online.monitoring.util.TimestampUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    /**
     * 添加 Address 记录。参数 address 中需要的字段：
     * province：省份
     * city：城市
     * district：地区
     * detail：详细地址
     *
     * @param address 聚合 address 字段的参数
     * @return 保存成功后的 address 对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Address add(Address address) {
        address.setGmtCreate(TimestampUtils.now());
        address.setGmtModified(TimestampUtils.now());
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
        if (optional.isPresent()) {
            return optional.get();
        }
        log.warn("地址不存在！addressId：{}", id);
        throw new AddressException(ErrorCodeEnum.MISSING, "addressId", "地址不存在！");
    }

    @Override
    public List<Address> listAll() {
        return addressRepository.findAll();
    }

}
