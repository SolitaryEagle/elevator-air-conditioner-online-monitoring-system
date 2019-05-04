package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 覃国强
 * @date 2019/5/1 16:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Address implements Serializable {

    private static final long serialVersionUID = -6837515179014633088L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Timestamp gmtCreate;

    @Column(nullable = false)
    private Timestamp gmtModified;

    private String province;
    private String city;
    private String district;
    private String detail;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
