package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.request;

import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.dto.AddressDTO;
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
