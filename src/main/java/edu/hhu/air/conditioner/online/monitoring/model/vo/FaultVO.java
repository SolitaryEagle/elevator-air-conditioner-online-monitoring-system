package edu.hhu.air.conditioner.online.monitoring.model.vo;

import edu.hhu.air.conditioner.online.monitoring.constant.FaultTypeEnum;
import edu.hhu.air.conditioner.online.monitoring.constant.RepairResultEnum;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author 覃国强
 * @date 2019-03-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FaultVO implements Serializable {

    private static final long serialVersionUID = -5738691563021321041L;
    private Long id;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
    private Boolean handle;

    @NotBlank
    private String description;

    @NotBlank
    private FaultTypeEnum faultType;

    @NotNull
    private Long airConditionerId;
    private Long reportUserId;
    private Long repairUserId;
    private String repairRecord;
    private RepairResultEnum repairResult;
    private Timestamp gmtRepair;
    private String repairMaterial;
    private Long revisitUserId;
    private String revisitResult;
    private Timestamp gmtRevisit;
    private Long evaluationUserId;
    private String userEvaluation;
    private Integer userSatisfaction;
    private Timestamp gmtEvaluation;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
