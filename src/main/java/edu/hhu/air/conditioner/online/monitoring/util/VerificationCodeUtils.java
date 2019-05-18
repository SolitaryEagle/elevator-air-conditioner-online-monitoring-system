package edu.hhu.air.conditioner.online.monitoring.util;

import java.util.Random;

/**
 * @author 覃国强
 * @date 2019-02-15
 */
public final class VerificationCodeUtils {

    private VerificationCodeUtils() {}

    /**
     * 生成一个6位数的验证码
     */
    public static String generate() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

}
