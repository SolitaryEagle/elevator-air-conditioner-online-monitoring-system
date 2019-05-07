package edu.hhu.air.conditioner.online.monitoring.exception;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.ErrorCodeEnum;

/**
 * @author 覃国强
 * @date 2019-02-15
 */
public class UserException extends BusinessException {

    private static final long serialVersionUID = -5011233412844904783L;

    public UserException(ErrorCodeEnum errorCode) {
        super(errorCode);
    }

    public UserException(ErrorCodeEnum errorCode, String field) {
        super(errorCode, field);
    }

    public UserException(ErrorCodeEnum errorCode, String field, String message) {
        super(errorCode, field, message);
    }

    public UserException(ErrorCodeEnum errorCode, String field, String message, Throwable cause) {
        super(errorCode, field, message, cause);
    }

    public UserException(ErrorCodeEnum errorCode, String field, Throwable cause) {
        super(errorCode, field, cause);
    }

    public UserException(ErrorCodeEnum errorCode, String field, String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(errorCode, field, message, cause, enableSuppression, writableStackTrace);
    }

}
