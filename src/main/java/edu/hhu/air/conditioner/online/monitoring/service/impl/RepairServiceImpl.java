package edu.hhu.air.conditioner.online.monitoring.service.impl;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.ErrorCodeEnum;
import edu.hhu.air.conditioner.online.monitoring.constant.enums.FaultStateEnum;
import edu.hhu.air.conditioner.online.monitoring.constant.enums.RepairResultEnum;
import edu.hhu.air.conditioner.online.monitoring.exception.BusinessException;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Fault;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Repair;
import edu.hhu.air.conditioner.online.monitoring.repository.RepairRepository;
import edu.hhu.air.conditioner.online.monitoring.service.FaultService;
import edu.hhu.air.conditioner.online.monitoring.service.RepairService;
import edu.hhu.air.conditioner.online.monitoring.util.TimestampUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author 覃国强
 * @date 2019/5/17 11:26
 */
@Slf4j
@Service
public class RepairServiceImpl implements RepairService {

    @Autowired
    private RepairRepository repairRepository;

    @Autowired
    private FaultService faultService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Repair add(Repair repair) {

        repair.setGmtCreate(TimestampUtils.now());
        repair.setGmtModified(TimestampUtils.now());

        if (RepairResultEnum.SUCCESS.equals(repair.getResult())) {
            // 将 fault 的 state 改为 REPAIRED
            Fault fault = Fault.builder().id(repair.getFaultId()).state(FaultStateEnum.REPAIRED).build();
            faultService.updateStateById(fault);
        } else {
            // 将 fault 的 state 改为 END，repair_result 改为 FAILURE
            Fault fault = Fault.builder().id(repair.getFaultId()).state(FaultStateEnum.END).build();
            faultService.updateStateById(fault);
            fault.setRepairResult(RepairResultEnum.FAILURE);
            faultService.updateRepairResultById(fault);

            // 将原维修工单重新加入fault表中
            fault = faultService.getById(repair.getFaultId());
            Fault copyFault = new Fault();
            BeanUtils.copyProperties(fault, copyFault);
            copyFault.setId(null);
            copyFault.setRepairResult(null);
            faultService.add(copyFault);
        }

        return repairRepository.save(repair);
    }

    @Override
    public List<Repair> listAll() {
        List<Repair> list = repairRepository.findAll();
        log.info("获取所有的 Repair。");
        return list;
    }

    @Override
    public Repair getByFaultId(Long faultId) {
        Optional<Repair> optional = repairRepository.findByFaultId(faultId);
        if (!optional.isPresent()) {
            log.warn("根据 faultId 获取 Repair 失败. faultId: {}", faultId);
            return null;
        }
        log.info("根据 faultId 找到 Repair. faultId: {}", faultId);
        return optional.get();
    }

}
