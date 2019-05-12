package edu.hhu.air.conditioner.online.monitoring.model.response;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.FaultStateEnum;
import edu.hhu.air.conditioner.online.monitoring.constant.enums.FaultTypeEnum;
import edu.hhu.air.conditioner.online.monitoring.constant.enums.RepairResultEnum;
import edu.hhu.air.conditioner.online.monitoring.model.entity.AirConditioner;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Allocation;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Evaluation;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Repair;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Revisit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 覃国强
 * @date 2019/5/15 15:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FaultDetailResponse {

    private Long id;
    private Date gmtCreate;
    private Date gmtModified;
    private FaultTypeEnum type;
    private FaultStateEnum state;
    private RepairResultEnum repairResult;
    private String description;
    private String realName;
    private String contactAddress;
    private String phoneNumber;
    private AirConditioner airConditioner;
    private UserResponse reportUser;
    private Allocation allocation;
    private Repair repair;
    private Revisit revisit;
    private Evaluation evaluation;

}
