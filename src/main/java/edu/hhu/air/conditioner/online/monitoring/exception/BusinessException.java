package edu.hhu.air.conditioner.online.monitoring.exception;

import lombok.Getter;

/**
 * @author 覃国强
 * @date 2019-02-21
 */
@Getter
public class BusinessException extends Exception {

    private static final long serialVersionUID = 4148993212167064741L;
    private ResponseCode responseCode;
    private String field;

    public BusinessException(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public BusinessException(ResponseCode responseCode, String field) {
        this.responseCode = responseCode;
        this.field = field;
    }

    public BusinessException(ResponseCode responseCode, String field, String message) {
        super(message);
        this.responseCode = responseCode.withMessage(message);
        this.field = field;
    }

    public BusinessException(ResponseCode responseCode, String field, String message, Throwable cause) {
        super(message, cause);
        this.responseCode = responseCode.withMessage(message);
        this.field = field;
    }

    public BusinessException(ResponseCode responseCode, String field, Throwable cause) {
        super(cause);
        this.responseCode = responseCode;
        this.field = field;
    }

    public BusinessException(ResponseCode responseCode, String field, String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.responseCode = responseCode.withMessage(message);
        this.field = field;
    }

}
