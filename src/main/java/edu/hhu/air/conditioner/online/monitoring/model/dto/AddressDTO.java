package edu.hhu.air.conditioner.online.monitoring.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 覃国强
 * @date 2019/5/2 16:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDTO {

    private String province;
    private String city;
    private String district;
    private String detail;

    @Override
    public String toString() {
        return province + city + district + detail;
    }
}
