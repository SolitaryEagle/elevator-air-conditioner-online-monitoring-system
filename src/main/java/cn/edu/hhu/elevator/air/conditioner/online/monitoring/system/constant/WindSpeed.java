package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.constant;

import lombok.Getter;

import java.util.Random;

/**
 * @author 覃国强
 * @date 2019/4/28 15:33
 */
@Getter
public enum WindSpeed {

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

    WindSpeed(String value) {
        this.value = value;
    }

    public static WindSpeed random() {
        WindSpeed[] values = values();
        int index = new Random().nextInt(values.length);
        return values[index];
    }

}
