package edu.hhu.air.conditioner.online.monitoring.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 覃国强
 * @date 2019/5/12 15:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Allocation implements Serializable {

    private static final long serialVersionUID = 104955132689453309L;

    @Id
    @GeneratedValue
    private Long id;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;

    @Column(columnDefinition = "bigint(20) UNSIGNED")
    private Long faultId;

    @Column(columnDefinition = "bigint(20) UNSIGNED")
    private Long repairUserId;

    @Column(columnDefinition = "bigint(20) UNSIGNED")
    private Long allocationUserId;

}
