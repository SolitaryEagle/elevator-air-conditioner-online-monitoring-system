package edu.hhu.air.conditioner.online.monitoring.util;

import java.util.Objects;

/**
 * @author 覃国强
 * @date 2019/5/4 15:22
 */
public class ObjectUtils {

    public static boolean nonNull(Object object) {
        return !Objects.isNull(object);
    }

}
