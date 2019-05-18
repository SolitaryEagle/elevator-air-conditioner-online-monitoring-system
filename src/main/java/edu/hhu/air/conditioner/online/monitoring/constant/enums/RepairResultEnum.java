package edu.hhu.air.conditioner.online.monitoring.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 覃国强
 * @date 2019-03-12
 */
@Getter
@AllArgsConstructor
public enum RepairResultEnum {

    /**
     * 成功
     */
    SUCCESS(1, "成功"),
    /**
     * 失败
     */
    FAILURE(2, "失败"),

    REPAIRMAN_REJECT(3, "维修工拒绝")
    ;

    private int value;
    private String message;

}
