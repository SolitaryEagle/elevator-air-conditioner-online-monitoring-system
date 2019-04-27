package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.model.entity;

import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.constant.AirConditionerStateEnum;
import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.constant.RegionCodeEnum;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.constant.WindSpeed;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author 覃国强
 * @date 2019-02-19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class AirConditioner implements Serializable {

    private static final long serialVersionUID = 4285839436404700704L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Timestamp gmtCreate;

    @Column(nullable = false)
    private Timestamp gmtModified;

    // 系统生成

    @Column(unique = true, nullable = false, columnDefinition = "char(12)", length = 12)
    private String equipmentNumber;

    // 用户填写

    private String brand;

    // 用户填写

    private String model;

    // 用户填写

    private String seller;

    // 用户填写

    @Column(nullable = false)
    private String address;

    // 系统生成

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "char(14)", length = 14)
    private RegionCodeEnum regionCode;

    // 系统生成

    @Column(nullable = false, precision = 20, scale = 14)
    private BigDecimal longitude;

    // 系统生成

    @Column(nullable = false, precision = 20, scale = 14)
    private BigDecimal latitude;

    // 系统生成

    @Column(nullable = false)
    private Integer temperature;


    // 系统生成

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WindSpeed windSpeed;

    // 系统生成

    @Column(nullable = false, precision = 20, scale = 2)
    private BigDecimal kwh;

    // 系统生成

    @Column(nullable = false, precision = 10, scale = 3)
    private BigDecimal currentIntensity;

    // 系统生成

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal voltage;

    // currentIntensity × voltage

    @Column(nullable = false, precision = 20, scale = 3)
    private BigDecimal power;

    // 用户填写

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AirConditionerStateEnum state;

    // 用户填写

    @Column(nullable = false, columnDefinition = "text")
    private String faultDescription;

    // 系统设置

    @Column(nullable = false, columnDefinition = "bigint(20) UNSIGNED")
    private Long userId;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
