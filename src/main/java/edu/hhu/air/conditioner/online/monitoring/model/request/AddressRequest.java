package edu.hhu.air.conditioner.online.monitoring.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author 覃国强
 * @date 2019/5/13 15:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressRequest {

    private String province;
    private String city;
    private String district;
    private String detail;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String toSimpleString() {
        return province + city + district + detail;
    }

}
