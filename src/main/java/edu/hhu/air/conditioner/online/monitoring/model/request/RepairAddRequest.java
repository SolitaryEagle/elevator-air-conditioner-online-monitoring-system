package edu.hhu.air.conditioner.online.monitoring.model.request;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.RepairResultEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 覃国强
 * @date 2019/5/17 11:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepairAddRequest {

    private String record;
    private String material;
    private Long faultId;
    private RepairResultEnum result;

}
