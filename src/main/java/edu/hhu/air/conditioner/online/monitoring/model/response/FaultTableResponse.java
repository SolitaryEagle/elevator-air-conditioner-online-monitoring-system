package edu.hhu.air.conditioner.online.monitoring.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 覃国强
 * @date 2019/5/14 15:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FaultTableResponse {

    private Long id;
    private String equipmentId;
    private String type;
    private String state;
    private Date gmtModified;
    private String description;

}
