package edu.hhu.air.conditioner.online.monitoring.exception;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.ErrorCodeEnum;

/**
 * @author 覃国强
 * @date 2019-02-21
 */
public class FaultException extends RuntimeException {

    private static final long serialVersionUID = 6920352937957943004L;
    private ErrorCodeEnum errorCode;
    private String field;

    public FaultException(ErrorCodeEnum errorCode) {
        this.errorCode = errorCode;
    }

    public FaultException(ErrorCodeEnum errorCode, String field) {
        this.errorCode = errorCode;
        this.field = field;
    }

    public FaultException(ErrorCodeEnum errorCode, String field, String message) {
        super(message);
        this.errorCode = errorCode.withMessage(message);
        this.field = field;
    }

    public FaultException(ErrorCodeEnum errorCode, String field, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode.withMessage(message);
        this.field = field;
    }

    public FaultException(ErrorCodeEnum errorCode, String field, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.field = field;
    }

    public FaultException(ErrorCodeEnum errorCode, String field, String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode.withMessage(message);
        this.field = field;
    }

}
