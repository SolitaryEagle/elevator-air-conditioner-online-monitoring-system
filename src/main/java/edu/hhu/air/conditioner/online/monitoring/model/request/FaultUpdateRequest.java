package edu.hhu.air.conditioner.online.monitoring.model.request;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.FaultTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 覃国强
 * @date 2019/5/15 15:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FaultUpdateRequest {

    private Long id;
    private String realName;
    private String phoneNumber;
    private String contactAddress;
    private FaultTypeEnum type;
    private String description;

}
