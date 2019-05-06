package edu.hhu.air.conditioner.online.monitoring.model.vo;

import edu.hhu.air.conditioner.online.monitoring.constant.AirConditionerStateEnum;
import edu.hhu.air.conditioner.online.monitoring.constant.RegionCodeEnum;
import edu.hhu.air.conditioner.online.monitoring.constant.WindSpeedEnum;
import edu.hhu.air.conditioner.online.monitoring.model.entity.User;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author 覃国强
 * @date 2019-02-22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirConditionerVO implements Serializable {

    private static final long serialVersionUID = 9097578993481009421L;
    private Long id;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
    private String equipmentNumber;
    private String brand;
    private String model;
    private String seller;

    @NotBlank
    private String address;
    private RegionCodeEnum regionCode;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private Integer temperature;
    private WindSpeedEnum windSpeedEnum;
    private BigDecimal kwh;
    private BigDecimal currentIntensity;
    private BigDecimal voltage;
    private BigDecimal power;
    private AirConditionerStateEnum state;
    private String faultDescription;
    private User user;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
