package edu.hhu.air.conditioner.online.monitoring.exception;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.ErrorCodeEnum;

/**
 * @author 覃国强
 * @date 2019/5/10 15:05
 */
public class AirConditionerException extends BusinessException {

    private static final long serialVersionUID = -5584475426304738973L;

    public AirConditionerException(ErrorCodeEnum errorCode) {
        super(errorCode);
    }

    public AirConditionerException(ErrorCodeEnum errorCode, String field) {
        super(errorCode, field);
    }

    public AirConditionerException(ErrorCodeEnum errorCode, String field, String message) {
        super(errorCode, field, message);
    }

    public AirConditionerException(ErrorCodeEnum errorCode, String field, String message, Throwable cause) {
        super(errorCode, field, message, cause);
    }

    public AirConditionerException(ErrorCodeEnum errorCode, String field, Throwable cause) {
        super(errorCode, field, cause);
    }

    public AirConditionerException(ErrorCodeEnum errorCode, String field, String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(errorCode, field, message, cause, enableSuppression, writableStackTrace);
    }

}
