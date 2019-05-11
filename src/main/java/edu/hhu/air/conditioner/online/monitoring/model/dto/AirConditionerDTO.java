package edu.hhu.air.conditioner.online.monitoring.model.dto;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.AirConditionerStateEnum;
import edu.hhu.air.conditioner.online.monitoring.constant.enums.RegionCodeEnum;
import edu.hhu.air.conditioner.online.monitoring.constant.enums.WindSpeedEnum;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Address;
import edu.hhu.air.conditioner.online.monitoring.model.entity.AirConditioner;
import edu.hhu.air.conditioner.online.monitoring.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author 覃国强
 * @date 2019/5/2 14:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirConditionerDTO {

    private Long id;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
    private String equipmentId;
    private String brand;
    private String model;
    private String seller;
    private String addressString;
    private RegionCodeEnum regionCode;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private Integer temperature;
    private WindSpeedEnum windSpeed;
    private BigDecimal kwh;
    private BigDecimal currentIntensity;
    private BigDecimal voltage;
    private BigDecimal power;
    private AirConditionerStateEnum state;
    private String faultDescription;
    private User user;
    private Address address;

    public AirConditioner toAirConditioner() {
        AirConditioner airConditioner = new AirConditioner();
        BeanUtils.copyProperties(this, airConditioner);
        if (!Objects.isNull(user)) {
            airConditioner.setUserId(user.getId());
        }
        if (!Objects.isNull(address)) {
            airConditioner.setAddressId(address.getId());
        }
        return airConditioner;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
