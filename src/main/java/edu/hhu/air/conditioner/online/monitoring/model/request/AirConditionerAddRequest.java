package edu.hhu.air.conditioner.online.monitoring.model.request;

import edu.hhu.air.conditioner.online.monitoring.model.dto.AirConditionerDTO;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.BeanUtils;

/**
 * @author 覃国强
 * @date 2019/5/2 14:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirConditionerAddRequest {

    private String brand;
    private String model;
    private String seller;
    private Address address;

    public AirConditionerDTO toAirConditionerDTO() {
        AirConditionerDTO result = new AirConditionerDTO();
        BeanUtils.copyProperties(this, result);
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
