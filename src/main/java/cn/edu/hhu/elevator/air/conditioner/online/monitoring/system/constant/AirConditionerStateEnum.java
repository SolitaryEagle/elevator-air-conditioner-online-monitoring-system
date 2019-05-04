package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 覃国强
 * @date 2019-02-19
 */
@Getter
@AllArgsConstructor
public enum AirConditionerStateEnum {

    /**
     * 良好状态
     */
    GOOD("良好"),

    /**
     * 故障状态
     */
    FAULT("故障");

    private String value;

}
