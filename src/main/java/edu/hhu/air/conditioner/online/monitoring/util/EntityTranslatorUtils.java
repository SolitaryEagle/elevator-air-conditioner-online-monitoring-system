package edu.hhu.air.conditioner.online.monitoring.util;

import org.springframework.beans.BeanUtils;

/**
 * @author 覃国强
 * @date 2019/5/13 15:54
 */
public class EntityTranslatorUtils {

    public static <T, R> R translate(T t, Class<R> clazz) throws IllegalAccessException, InstantiationException {
        Object r = clazz.newInstance();
        BeanUtils.copyProperties(t, r);
        return (R) r;
    }

}
