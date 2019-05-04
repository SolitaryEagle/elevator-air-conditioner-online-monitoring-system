package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * @author 覃国强
 * @date 2019/5/2 15:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EarthCoordinate {

    private BigDecimal longitude;
    private BigDecimal latitude;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
