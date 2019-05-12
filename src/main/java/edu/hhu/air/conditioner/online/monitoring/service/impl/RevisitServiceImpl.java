package edu.hhu.air.conditioner.online.monitoring.service.impl;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.ErrorCodeEnum;
import edu.hhu.air.conditioner.online.monitoring.constant.enums.FaultStateEnum;
import edu.hhu.air.conditioner.online.monitoring.exception.BusinessException;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Fault;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Revisit;
import edu.hhu.air.conditioner.online.monitoring.repository.RevisitRepository;
import edu.hhu.air.conditioner.online.monitoring.service.FaultService;
import edu.hhu.air.conditioner.online.monitoring.service.RevisitService;
import edu.hhu.air.conditioner.online.monitoring.util.TimestampUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.BufferOverflowException;
import java.util.List;
import java.util.Optional;

/**
 * @author 覃国强
 * @date 2019/5/17 15:26
 */
@Slf4j
@Service
public class RevisitServiceImpl implements RevisitService {

    @Autowired
    private RevisitRepository revisitRepository;

    @Autowired
    private FaultService faultService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Revisit add(Revisit revisit) {

        revisit.setGmtCreate(TimestampUtils.now());
        revisit.setGmtModified(TimestampUtils.now());

        // 将 fault 的 state 设置为 REVISITED
        Fault fault = Fault.builder().id(revisit.getFaultId()).state(FaultStateEnum.REVISITED).build();
        faultService.updateStateById(fault);

        Revisit saveRevisit = revisitRepository.save(revisit);
        log.info("Revisit 添加成功。id：{}", saveRevisit.getId());

        return saveRevisit;
    }

    @Override
    public List<Revisit> listAll() {
        List<Revisit> list = revisitRepository.findAll();
        log.info("获取所有的 Revisit 成功。");
        return list;
    }

    @Override
    public Revisit getByFaultId(Long faultId) {
        Optional<Revisit> optional = revisitRepository.findByFaultId(faultId);
        if (!optional.isPresent()) {
            log.warn("根据 faultId 获取 Revisit 失败。faultId: {}", faultId);
            return null;
        }
        log.info("根据 faultId 获取到 Revisit。faultId: {}", faultId);
        return optional.get();
    }

}
