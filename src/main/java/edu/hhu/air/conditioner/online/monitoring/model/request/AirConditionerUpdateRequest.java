package edu.hhu.air.conditioner.online.monitoring.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;

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
    private AddressRequest address;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
