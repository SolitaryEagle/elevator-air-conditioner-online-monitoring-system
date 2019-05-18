package edu.hhu.air.conditioner.online.monitoring.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author 覃国强
 * @date 2019/5/2 15:09
 */
public final class TimestampUtils {

    private TimestampUtils() {}

    public static Timestamp now() {
        return Timestamp.valueOf(LocalDateTime.now());
    }


    public static Timestamp fromDate(Date date) {
        return new Timestamp(date.getTime());
    }
}
