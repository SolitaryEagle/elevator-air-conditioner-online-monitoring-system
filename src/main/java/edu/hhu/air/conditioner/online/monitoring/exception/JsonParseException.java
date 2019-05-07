package edu.hhu.air.conditioner.online.monitoring.exception;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.ErrorCodeEnum;

/**
 * @author 覃国强
 * @date 2019/5/9 11:04
 */
public class JsonParseException extends BusinessException {

    private static final long serialVersionUID = 3668092780178780947L;

    public JsonParseException(ErrorCodeEnum errorCode) {
        super(errorCode);
    }

    public JsonParseException(ErrorCodeEnum errorCode, String field) {
        super(errorCode, field);
    }

    public JsonParseException(ErrorCodeEnum errorCode, String field, String message) {
        super(errorCode, field, message);
    }

    public JsonParseException(ErrorCodeEnum errorCode, String field, String message, Throwable cause) {
        super(errorCode, field, message, cause);
    }

    public JsonParseException(ErrorCodeEnum errorCode, String field, Throwable cause) {
        super(errorCode, field, cause);
    }

    public JsonParseException(ErrorCodeEnum errorCode, String field, String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(errorCode, field, message, cause, enableSuppression, writableStackTrace);
    }

}
