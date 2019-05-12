package edu.hhu.air.conditioner.online.monitoring.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 覃国强
 * @date 2019/5/17 16:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvaluationAddRequest {

    private Integer satisfaction;
    private String content;
    private Long faultId;

}
