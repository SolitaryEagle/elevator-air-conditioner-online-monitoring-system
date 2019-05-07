package edu.hhu.air.conditioner.online.monitoring.exception;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.ErrorCodeEnum;

/**
 * @author 覃国强
 * @date 2019-02-15
 */
public class AddressEmptyException extends BusinessException {

    private static final long serialVersionUID = 1186007272325500007L;

    public AddressEmptyException(ErrorCodeEnum errorCode) {
        super(errorCode);
    }

    public AddressEmptyException(ErrorCodeEnum errorCode, String field) {
        super(errorCode, field);
    }

    public AddressEmptyException(ErrorCodeEnum errorCode, String field, String message) {
        super(errorCode, field, message);
    }

    public AddressEmptyException(ErrorCodeEnum errorCode, String field, String message, Throwable cause) {
        super(errorCode, field, message, cause);
    }

    public AddressEmptyException(ErrorCodeEnum errorCode, String field, Throwable cause) {
        super(errorCode, field, cause);
    }

    public AddressEmptyException(ErrorCodeEnum errorCode, String field, String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(errorCode, field, message, cause, enableSuppression, writableStackTrace);
    }

}
