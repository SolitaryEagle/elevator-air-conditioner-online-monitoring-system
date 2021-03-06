package edu.hhu.air.conditioner.online.monitoring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 覃国强
 * @date 2019/5/4 12:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Interval {

    private Number min;
    private Number max;

}
