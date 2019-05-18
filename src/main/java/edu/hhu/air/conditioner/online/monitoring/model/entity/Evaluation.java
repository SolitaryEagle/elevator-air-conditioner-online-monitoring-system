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
 * @date 2019/5/12 16:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Evaluation implements Serializable {

    private static final long serialVersionUID = -458478303589271990L;

    @Id
    @GeneratedValue
    private Long id;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
    private Integer satisfaction;

    @Column(columnDefinition = "text")
    private String content;

    @Column(columnDefinition = "bigint(20) UNSIGNED")
    private Long faultId;

    @Column(columnDefinition = "bigint(20) UNSIGNED")
    private Long evaluationUserId;

}
