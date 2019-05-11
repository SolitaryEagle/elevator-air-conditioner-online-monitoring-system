package edu.hhu.air.conditioner.online.monitoring.exception;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.ErrorCodeEnum;

/**
 * @author 覃国强
 * @date 2019/5/10 13:34
 */
public class AddressException extends BusinessException {

    private static final long serialVersionUID = 478958921428506230L;

    public AddressException(ErrorCodeEnum errorCode) {
        super(errorCode);
    }

    public AddressException(ErrorCodeEnum errorCode, String field) {
        super(errorCode, field);
    }

    public AddressException(ErrorCodeEnum errorCode, String field, String message) {
        super(errorCode, field, message);
    }

    public AddressException(ErrorCodeEnum errorCode, String field, String message, Throwable cause) {
        super(errorCode, field, message, cause);
    }

    public AddressException(ErrorCodeEnum errorCode, String field, Throwable cause) {
        super(errorCode, field, cause);
    }

    public AddressException(ErrorCodeEnum errorCode, String field, String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(errorCode, field, message, cause, enableSuppression, writableStackTrace);
    }

}
