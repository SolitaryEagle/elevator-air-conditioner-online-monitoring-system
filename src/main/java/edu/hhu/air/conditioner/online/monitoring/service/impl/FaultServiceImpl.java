package edu.hhu.air.conditioner.online.monitoring.service.impl;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.AirConditionerStateEnum;
import edu.hhu.air.conditioner.online.monitoring.constant.enums.ErrorCodeEnum;
import edu.hhu.air.conditioner.online.monitoring.constant.enums.FaultStateEnum;
import edu.hhu.air.conditioner.online.monitoring.exception.BusinessException;
import edu.hhu.air.conditioner.online.monitoring.model.entity.AirConditioner;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Fault;
import edu.hhu.air.conditioner.online.monitoring.repository.FaultRepository;
import edu.hhu.air.conditioner.online.monitoring.service.AirConditionerService;
import edu.hhu.air.conditioner.online.monitoring.service.FaultService;
import edu.hhu.air.conditioner.online.monitoring.util.TimestampUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author 覃国强
 * @date 2019-02-13
 */
@Slf4j
@Service
public class FaultServiceImpl implements FaultService {

    @Autowired
    private FaultRepository faultRepository;
    @Autowired
    private AirConditionerService airConditionerService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Fault add(Fault fault) {

        fault.setGmtCreate(TimestampUtils.now());
        fault.setGmtModified(TimestampUtils.now());
        fault.setState(FaultStateEnum.NEW);

        // 修改 AirConditioner 中的 state 和 faultDescription 字段
        AirConditioner airConditioner = AirConditioner.builder().id(fault.getAirConditionerId())
                .state(AirConditionerStateEnum.FAULT).faultDescription(fault.getDescription()).build();
        airConditionerService.updateStateAndFaultDescriptionById(airConditioner);
        Fault saveFault = faultRepository.save(fault);
        log.info("新增故障成功。id: {}", saveFault.getId());
        return saveFault;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {

        Optional<Fault> optional = faultRepository.findById(id);
        if (!optional.isPresent()) {
            throw new BusinessException(ErrorCodeEnum.MISSING, "id", "故障不存在");
        }
        Fault fault = optional.get();

        // 更新 AirConditioner 的状态
        AirConditioner airConditioner = AirConditioner.builder().id(fault.getAirConditionerId())
                .state(AirConditionerStateEnum.GOOD).faultDescription("").build();

        airConditionerService.updateStateAndFaultDescriptionById(airConditioner);

        faultRepository.deleteById(id);
        log.info("故障删除成功! id: {}", id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateById(Fault fault) {

        Optional<Fault> optional = faultRepository.findById(fault.getId());
        if (!optional.isPresent()) {
            throw new BusinessException(ErrorCodeEnum.MISSING, "id", "故障不存在");
        }

        // 更新 AirConditioner 中的故障描述         ---------------->     在 其它表中保存了 该表的冗余信息，使得所有改变都得考虑这个相关的表
        AirConditioner airConditioner = AirConditioner.builder().id(optional.get().getAirConditionerId())
                .state(AirConditionerStateEnum.FAULT).faultDescription(fault.getDescription()).build();
        airConditionerService.updateStateAndFaultDescriptionById(airConditioner);

        fault.setGmtModified(TimestampUtils.now());
        int result = faultRepository.updateById(fault);
        if (result <= 0) {
            throw new BusinessException(ErrorCodeEnum.INTERNAL_SERVER_ERROR, "id", "更新故障失败");
        }
        log.info("更新故障成功！ id：{}", fault.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateStateById(Fault fault) {
        fault.setGmtModified(TimestampUtils.now());
        int result = faultRepository.updateStateById(fault);
        if (result <= 0) {
            throw new BusinessException(ErrorCodeEnum.INTERNAL_SERVER_ERROR, "id", "更新故障状态失败");
        }
        log.info("更新故障状态成功！ id：{}", fault.getId());
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateRepairResultById(Fault fault) {
        fault.setGmtModified(TimestampUtils.now());
        int result = faultRepository.updateRepairResultById(fault);
        if (result <= 0) {
            throw new BusinessException(ErrorCodeEnum.INTERNAL_SERVER_ERROR, "id", "更新故障维修结果失败");
        }
        log.info("更新故障维修结果成功！ id：{}", fault.getId());
        return result;
    }

    @Override
    public Fault getById(Long id) {
        Optional<Fault> optional = faultRepository.findById(id);
        if (!optional.isPresent()) {
            throw new BusinessException(ErrorCodeEnum.MISSING, "id", "故障不存在");
        }
        Fault fault = optional.get();
        log.info("获取故障成功！id: {}", id);
        return fault;
    }

    @Override
    public List<Fault> listAllByState(FaultStateEnum state) {
        List<Fault> list = faultRepository.findByState(state);
        log.info("按 state 获取fault 成功！state：{}", state);
        return list;
    }

    @Override
    public List<Fault> listByAirConditionerId(Long airConditionerId) {
        List<Fault> list = faultRepository.findByAirConditionerId(airConditionerId);
        log.info("根据 airConditionerId 获取 Fault 成功！airConditionerId：{}", airConditionerId);
        return list;
    }

    @Override
    public List<Fault> listAll() {
        return faultRepository.findAll();
    }

    @Override
    public List<Fault> listAllByReportUserId(Long reportUserId) {
        return faultRepository.findByReportUserId(reportUserId);
    }

    @Override
    public Page<Fault> listAll(int page, int size) {
        Page<Fault> pageResult = faultRepository.findAll(PageRequest.of(page, size));
        return pageResult;
    }

    @Override
    public Page<Fault> listAllByReportUserId(Long reportUserId, int page, int size) {
        Page<Fault> pageResult = faultRepository
                .findAll(Example.of(Fault.builder().reportUserId(reportUserId).build()), PageRequest.of(page, size));
        return pageResult;
    }

    @Override
    public List<Fault> listAll(Example<Fault> example) {
        List<Fault> result = faultRepository.findAll(example);
        log.info("根据example获取Fault成功！example: {}", example);
        return result;
    }

}
