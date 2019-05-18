package edu.hhu.air.conditioner.online.monitoring.model.response;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.AirConditionerStateEnum;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 覃国强
 * @date 2019/5/11 09:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirConditionerDetailResponse {

    private Long id;
    private Date gmtCreate;
    private Date gmtModified;
    private String equipmentId;
    private String brand;
    private String model;
    private String seller;
    private Integer temperature;
    private String windSpeed;
    private BigDecimal kwh;
    private BigDecimal currentIntensity;
    private BigDecimal voltage;
    private BigDecimal power;
    private AirConditionerStateEnum equipmentState;
    private String faultDescription;
    private Address address;
    private UserResponse user;
    private String addressString;

}
