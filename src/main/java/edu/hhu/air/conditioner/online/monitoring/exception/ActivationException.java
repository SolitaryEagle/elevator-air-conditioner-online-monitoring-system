package edu.hhu.air.conditioner.online.monitoring.exception;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.ErrorCodeEnum;

/**
 * @author 覃国强
 * @date 2019/5/9 09:26
 */
public class ActivationException extends BusinessException {

    private static final long serialVersionUID = 4923604506658858463L;

    public ActivationException(ErrorCodeEnum errorCode) {
        super(errorCode);
    }

    public ActivationException(ErrorCodeEnum errorCode, String field) {
        super(errorCode, field);
    }

    public ActivationException(ErrorCodeEnum errorCode, String field, String message) {
        super(errorCode, field, message);
    }

    public ActivationException(ErrorCodeEnum errorCode, String field, String message, Throwable cause) {
        super(errorCode, field, message, cause);
    }

    public ActivationException(ErrorCodeEnum errorCode, String field, Throwable cause) {
        super(errorCode, field, cause);
    }

    public ActivationException(ErrorCodeEnum errorCode, String field, String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(errorCode, field, message, cause, enableSuppression, writableStackTrace);
    }

}
