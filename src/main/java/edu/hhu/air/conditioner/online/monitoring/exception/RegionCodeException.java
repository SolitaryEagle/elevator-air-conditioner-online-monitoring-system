package edu.hhu.air.conditioner.online.monitoring.exception;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.ErrorCodeEnum;

/**
 * @author 覃国强
 * @date 2019-02-21
 */
public class RegionCodeException extends RuntimeException {

    private static final long serialVersionUID = -415519202785046341L;
    private ErrorCodeEnum errorCode;
    private String field;

    public RegionCodeException(ErrorCodeEnum errorCode) {
        this.errorCode = errorCode;
    }

    public RegionCodeException(ErrorCodeEnum errorCode, String field) {
        this.errorCode = errorCode;
        this.field = field;
    }

    public RegionCodeException(ErrorCodeEnum errorCode, String field, String message) {
        super(message);
        this.errorCode = errorCode.withMessage(message);
        this.field = field;
    }

    public RegionCodeException(ErrorCodeEnum errorCode, String field, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode.withMessage(message);
        this.field = field;
    }

    public RegionCodeException(ErrorCodeEnum errorCode, String field, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.field = field;
    }

    public RegionCodeException(ErrorCodeEnum errorCode, String field, String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode.withMessage(message);
        this.field = field;
    }

}
