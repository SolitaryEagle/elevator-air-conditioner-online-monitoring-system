package edu.hhu.air.conditioner.online.monitoring.model.request;

import edu.hhu.air.conditioner.online.monitoring.model.dto.AddressDTO;
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
public class AirConditionerRequest {

    private String brand;
    private String model;
    private String seller;
    private AddressDTO address;

}