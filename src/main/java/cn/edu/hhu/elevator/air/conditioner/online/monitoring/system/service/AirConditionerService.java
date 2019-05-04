package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.service;

import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.exception.BusinessException;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.dto.AirConditionerDTO;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.entity.AirConditioner;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.entity.User;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.response.AirConditionerResponse;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.vo.AirConditionerVO;

import java.io.IOException;
import java.util.List;

/**
 * @author 覃国强
 * @date 2019-02-20
 */
public interface AirConditionerService {

    AirConditioner add(AirConditionerDTO airConditionerDTO) throws BusinessException, IOException;

    List<AirConditionerResponse> listAll();

    List<AirConditionerResponse> listAll(int page, int size);

    List<AirConditionerResponse> listByUserId(Long userId);

/*
    int update(AirConditionerVO airConditionerVO) throws IOException;

    List<AirConditionerVO> listAirConditioners(AirConditioner example);

    List<AirConditionerVO> listAll();
*/
}
