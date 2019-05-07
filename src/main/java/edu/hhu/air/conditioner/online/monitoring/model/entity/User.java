package edu.hhu.air.conditioner.online.monitoring.model.entity;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.RoleEnum;

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
 * @date 2019-01-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User implements Serializable {

    private static final long serialVersionUID = 7990835428341781323L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Timestamp gmtCreate;

    @Column(nullable = false)
    private Timestamp gmtModified;

    @Column(unique = true, columnDefinition = "char(20)")
    private String username;

    @Column(nullable = false, columnDefinition = "char(102)", length = 102)
    private String password;

    @Column(unique = true, nullable = false, columnDefinition = "char(30)", length = 30)
    private String email;

    @Column(columnDefinition = "char(11)", length = 11)
    private String phoneNumber;

    @Column(name = "is_activated", nullable = false, columnDefinition = "tinyint(1) UNSIGNED DEFAULT '0'")
    private Boolean activation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleEnum role;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
