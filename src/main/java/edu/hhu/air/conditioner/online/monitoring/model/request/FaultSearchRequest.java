package edu.hhu.air.conditioner.online.monitoring.model.request;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.FaultStateEnum;
import edu.hhu.air.conditioner.online.monitoring.constant.enums.FaultTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 覃国强
 * @date 2019/5/14 16:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FaultSearchRequest {

    private FaultTypeEnum type;
    private FaultStateEnum state;

}
