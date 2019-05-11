package edu.hhu.air.conditioner.online.monitoring.model.response;

import edu.hhu.air.conditioner.online.monitoring.model.dto.AirConditionerDTO;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

/**
 * @author 覃国强
 * @date 2019/5/11 21:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirConditionerMapResponse {

    private String equipmentId;
    private String addressString;
    private Address address;
    private BigDecimal longitude;
    private BigDecimal latitude;

    public static AirConditionerMapResponse valueOf(AirConditionerDTO airConditionerDTO) {
        AirConditionerMapResponse response = new AirConditionerMapResponse();
        BeanUtils.copyProperties(airConditionerDTO, response);
        return response;
    }

}
