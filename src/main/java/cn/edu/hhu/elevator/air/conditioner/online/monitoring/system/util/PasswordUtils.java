package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.util;

/**
 * @author 覃国强
 * @date 2019-02-16
 */
public final class PasswordUtils {

    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 16;

    private PasswordUtils() {}

    public static boolean isNotValid(String password) {

        return password.length() < MIN_LENGTH || password.length() > MAX_LENGTH;
    }

}
