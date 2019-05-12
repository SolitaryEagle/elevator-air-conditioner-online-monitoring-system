package edu.hhu.air.conditioner.online.monitoring.model.entity;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.RepairResultEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 覃国强
 * @date 2019/5/12 15:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Repair implements Serializable {

    private static final long serialVersionUID = 3106327583468778229L;

    @Id
    @GeneratedValue
    private Long id;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;

    @Enumerated(EnumType.STRING)
    private RepairResultEnum result;

    @Column(columnDefinition = "text")
    private String record;

    @Column(columnDefinition = "text")
    private String material;

    @Column(columnDefinition = "bigint(20) UNSIGNED")
    private Long faultId;

    @Column(columnDefinition = "bigint(20) UNSIGNED")
    private Long repairUserId;
}
