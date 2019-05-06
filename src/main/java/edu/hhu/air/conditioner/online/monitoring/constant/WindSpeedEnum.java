package edu.hhu.air.conditioner.online.monitoring.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Random;

/**
 * @author 覃国强
 * @date 2019/4/28 15:33
 */
@Getter
@AllArgsConstructor
public enum WindSpeedEnum {

    /**
     * 快
     */
    FAST("快"),

    /**
     * 慢
     */
    SLOW("慢"),

    /**
     * 停止
     */
    STOP("停止");

    private String value;

    public static WindSpeedEnum random() {
        WindSpeedEnum[] values = values();
        int index = new Random().nextInt(values.length);
        return values[index];
    }

}
