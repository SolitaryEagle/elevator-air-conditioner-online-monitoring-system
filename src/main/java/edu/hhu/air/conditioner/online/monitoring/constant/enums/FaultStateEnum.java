package edu.hhu.air.conditioner.online.monitoring.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 覃国强
 * @date 2019/5/13 20:03
 */
@Getter
@AllArgsConstructor
public enum FaultStateEnum {

    /**
     * Fault 处于新建状态
     */
    NEW(1, "新建"),

    /**
     * Fault 已分配
     */
    ALLOCATED(2, "已分配"),

    /**
     * Fault 正在等待维修
     */
    REPAIRING(3, "正在维修"),

    /**
     * Fault 已维修
     */
    REPAIRED(4, "已维修"),

    /**
     * Fault 已回访
     */
    REVISITED(5, "已回访"),

    /**
     * Fault 已被评价，并结束
     */
    END(6, "维修结束");

    private int value;
    private String message;
}
