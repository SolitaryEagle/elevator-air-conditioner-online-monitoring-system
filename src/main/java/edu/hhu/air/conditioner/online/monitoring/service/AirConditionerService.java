package edu.hhu.air.conditioner.online.monitoring.service;

import edu.hhu.air.conditioner.online.monitoring.model.dto.AirConditionerDTO;
import edu.hhu.air.conditioner.online.monitoring.model.entity.AirConditioner;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.io.IOException;
import java.util.List;

/**
 * @author 覃国强
 * @date 2019-02-20
 */
public interface AirConditionerService {

    AirConditionerDTO add(AirConditionerDTO airConditionerDTO) throws IOException;

    void deleteByEquipmentId(String equipmentId);

    AirConditionerDTO updateByEquipmentId(AirConditionerDTO airConditionerDTO) throws IOException;

    long count();

    AirConditionerDTO getById(Long id);

    List<AirConditionerDTO> listAll();

    Page<AirConditionerDTO> listAll(int page, int size);

    List<AirConditionerDTO> listAll(Specification<AirConditioner> specification);

    Page<AirConditionerDTO> listAll(Specification<AirConditioner> specification, int page, int size);

    List<AirConditionerDTO> listAllByUserId(Long userId);

    Page<AirConditionerDTO> listAllByUserId(Long userId, int page, int size);

    AirConditionerDTO getByEquipmentId(String equipmentId);





/*
    int update(AirConditionerVO airConditionerVO) throws IOException;

    List<AirConditionerVO> listAirConditioners(AirConditioner example);

    List<AirConditionerVO> listAll();
*/
}
