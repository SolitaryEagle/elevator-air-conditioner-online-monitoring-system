package edu.hhu.air.conditioner.online.monitoring.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 覃国强
 * @date 2019/5/17 15:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FaultRepairedTableResponse {

    private Long id;
    private String equipmentId;
    private String type;
    private String realName;
    private String allocationUser;
    private String repairUser;
    private String phoneNumber;
    private Date gmtModified;
    private String description;

}
