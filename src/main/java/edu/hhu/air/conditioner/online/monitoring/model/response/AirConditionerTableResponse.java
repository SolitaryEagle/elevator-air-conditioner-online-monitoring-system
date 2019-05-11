package edu.hhu.air.conditioner.online.monitoring.model.response;

import edu.hhu.air.conditioner.online.monitoring.model.dto.AirConditionerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 覃国强
 * @date 2019/5/3 16:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirConditionerTableResponse {

    private Long id;
    private Date gmtCreate;
    private Date gmtModified;
    private String equipmentId;
    private String brand;
    private String model;
    private String seller;
    private String address;
    private Integer temperature;
    private String windSpeed;
    private BigDecimal kwh;
    private BigDecimal currentIntensity;
    private BigDecimal voltage;
    private BigDecimal power;
    private String equipmentState;

    public static AirConditionerTableResponse valueOf(AirConditionerDTO airConditionerDTO) {
        AirConditionerTableResponse response = new AirConditionerTableResponse();
        BeanUtils.copyProperties(airConditionerDTO, response);
        response.setGmtCreate(new Date(airConditionerDTO.getGmtCreate().getTime()));
        response.setGmtModified(new Date(airConditionerDTO.getGmtModified().getTime()));
        response.setAddress(airConditionerDTO.getAddressString());
        response.setWindSpeed(airConditionerDTO.getWindSpeed().getValue());
        response.setEquipmentState(airConditionerDTO.getState().getValue());
        return response;
    }


}
