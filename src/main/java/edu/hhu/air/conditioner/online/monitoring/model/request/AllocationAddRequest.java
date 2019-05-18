package edu.hhu.air.conditioner.online.monitoring.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 覃国强
 * @date 2019/5/16 10:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllocationAddRequest {

    private Long faultId;
    private Long repairUserId;

}
