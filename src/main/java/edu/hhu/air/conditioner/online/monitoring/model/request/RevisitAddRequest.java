package edu.hhu.air.conditioner.online.monitoring.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 覃国强
 * @date 2019/5/17 15:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RevisitAddRequest {

    private String record;
    private Long faultId;

}
