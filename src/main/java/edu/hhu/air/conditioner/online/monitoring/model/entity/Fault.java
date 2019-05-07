package edu.hhu.air.conditioner.online.monitoring.model.entity;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.FaultTypeEnum;
import edu.hhu.air.conditioner.online.monitoring.constant.enums.RepairResultEnum;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
@Entity
public class Fault implements Serializable {

    private static final long serialVersionUID = 5659514972360842985L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Timestamp gmtCreate;

    @Column(nullable = false)
    private Timestamp gmtModified;

    @Column(name = "is_handled", nullable = false, columnDefinition = "tinyint(1) UNSIGNED DEFAULT '0'")
    private Boolean handle;

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FaultTypeEnum faultType;

    @Column(nullable = false, columnDefinition = "bigint(20) UNSIGNED")
    private Long airConditionerId;

    @Column(nullable = false, columnDefinition = "bigint(20) UNSIGNED")
    private Long reportUserId;

    @Column(nullable = false, columnDefinition = "bigint(20) UNSIGNED")
    private Long repairUserId;

    @Column(columnDefinition = "text")
    private String repairRecord;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RepairResultEnum repairResult;
    private Timestamp gmtRepair;

    @Column(columnDefinition = "text")
    private String repairMaterial;

    @Column(nullable = false, columnDefinition = "bigint(20) UNSIGNED")
    private Long revisitUserId;

    @Column(columnDefinition = "text")
    private String revisitResult;
    private Timestamp gmtRevisit;

    @Column(nullable = false, columnDefinition = "bigint(20) UNSIGNED")
    private Long evaluationUserId;
    private String userEvaluation;
    private Integer userSatisfaction;
    private Timestamp gmtEvaluation;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
