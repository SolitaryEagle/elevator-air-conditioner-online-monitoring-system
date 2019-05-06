package edu.hhu.air.conditioner.online.monitoring.service;

import edu.hhu.air.conditioner.online.monitoring.exception.BusinessException;
import edu.hhu.air.conditioner.online.monitoring.model.dto.AirConditionerDTO;
import edu.hhu.air.conditioner.online.monitoring.model.entity.AirConditioner;
import edu.hhu.air.conditioner.online.monitoring.model.request.AirConditionerSearchRequest;
import edu.hhu.air.conditioner.online.monitoring.model.response.AirConditionerResponse;
import edu.hhu.air.conditioner.online.monitoring.model.response.PageResponse;

import java.io.IOException;
import java.util.List;

/**
 * @author 覃国强
 * @date 2019-02-20
 */
public interface AirConditionerService {

    AirConditioner add(AirConditionerDTO airConditionerDTO) throws BusinessException, IOException;

    long count();

    List<AirConditionerResponse> listAll();

    PageResponse<AirConditionerResponse> listAll(int page, int size);

    List<AirConditionerResponse> listAll(AirConditionerSearchRequest searchRequest);

    PageResponse<AirConditionerResponse> listAll(AirConditionerSearchRequest searchRequest, int page, int size);

    List<AirConditionerResponse> listAllByUserId(Long userId);

    PageResponse<AirConditionerResponse> listAllByUserId(Long userId, int page, int size);



/*
    int update(AirConditionerVO airConditionerVO) throws IOException;

    List<AirConditionerVO> listAirConditioners(AirConditioner example);

    List<AirConditionerVO> listAll();
*/
}
