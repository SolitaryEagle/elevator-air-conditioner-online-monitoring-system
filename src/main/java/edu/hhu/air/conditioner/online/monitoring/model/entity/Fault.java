package edu.hhu.air.conditioner.online.monitoring.model.entity;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.FaultStateEnum;
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
    private Timestamp gmtCreate;
    private Timestamp gmtModified;

    @Enumerated(EnumType.STRING)
    private FaultTypeEnum type;

    @Enumerated(EnumType.STRING)
    private FaultStateEnum state;

    @Enumerated(EnumType.STRING)
    private RepairResultEnum repairResult;

    @Column(columnDefinition = "text")
    private String description;

    private String realName;
    private String contactAddress;
    private String phoneNumber;

    @Column(columnDefinition = "bigint(20) UNSIGNED")
    private Long airConditionerId;

    @Column(columnDefinition = "bigint(20) UNSIGNED")
    private Long reportUserId;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
