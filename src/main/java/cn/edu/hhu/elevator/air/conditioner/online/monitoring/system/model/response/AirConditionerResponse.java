package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class AirConditionerResponse {

    private String equipmentId;
    private Date gmtCreate;
    private Date gmtModified;
    private Integer temperature;
    private String windSpeed;
    private BigDecimal kwh;
    private BigDecimal currentIntensity;
    private BigDecimal voltage;
    private BigDecimal power;
    private String equipmentState;
    private String brand;
    private String model;
    private String seller;
    private String address;

}
