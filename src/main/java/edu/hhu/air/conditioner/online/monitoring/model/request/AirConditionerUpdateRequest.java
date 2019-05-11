package edu.hhu.air.conditioner.online.monitoring.model.request;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.AirConditionerStateEnum;
import edu.hhu.air.conditioner.online.monitoring.model.dto.AirConditionerDTO;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * @author 覃国强
 * @date 2019/5/11 15:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirConditionerUpdateRequest {

    private String equipmentId;
    private String brand;
    private String model;
    private String seller;
    private Address address;

    public AirConditionerDTO toAirConditionerDTO() {
        AirConditionerDTO result = new AirConditionerDTO();
        BeanUtils.copyProperties(this, result);
        return result;
    }
}
