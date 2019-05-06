package edu.hhu.air.conditioner.online.monitoring.model.request;

import edu.hhu.air.conditioner.online.monitoring.constant.AirConditionerStateEnum;
import edu.hhu.air.conditioner.online.monitoring.constant.WindSpeedEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 覃国强
 * @date 2019/5/2 14:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirConditionerSearchRequest {

    private String brand;
    private String model;
    private String seller;
    private String address;
    private WindSpeedEnum windSpeed;
    private AirConditionerStateEnum equipmentState;
    private IntervalRequest temperature;
    private IntervalRequest kwh;
    private IntervalRequest currentIntensity;
    private IntervalRequest voltage;
    private IntervalRequest power;
    private Long userId;

}
