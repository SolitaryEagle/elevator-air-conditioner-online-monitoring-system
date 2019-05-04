package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.dto;

import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 覃国强
 * @date 2019/5/2 14:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirConditionerDTO {

    private String brand;
    private String model;
    private String seller;
    private AddressDTO addressDTO;
    private User user;

}
