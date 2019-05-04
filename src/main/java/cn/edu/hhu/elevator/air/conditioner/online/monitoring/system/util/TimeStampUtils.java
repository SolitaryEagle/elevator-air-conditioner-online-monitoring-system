package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author 覃国强
 * @date 2019/5/2 15:09
 */
public final class TimeStampUtils {

    private TimeStampUtils() {}

    public static Timestamp now() {
        return Timestamp.valueOf(LocalDateTime.now());
    }

}
