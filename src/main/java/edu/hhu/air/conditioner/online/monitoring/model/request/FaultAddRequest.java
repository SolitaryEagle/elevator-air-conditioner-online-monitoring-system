package edu.hhu.air.conditioner.online.monitoring.model.request;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.FaultTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 覃国强
 * @date 2019/5/14 20:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FaultAddRequest {

    private String equipmentId;
    private FaultTypeEnum type;
    private String realName;
    private String contactAddress;
    private String phoneNumber;
    private String description;

}
