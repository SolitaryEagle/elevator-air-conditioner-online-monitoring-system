package edu.hhu.air.conditioner.online.monitoring.exception;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.ErrorCodeEnum;

/**
 * @author 覃国强
 * @date 2019-02-21
 */
public class BaiduMapException extends BusinessException {

    private static final long serialVersionUID = -5458001466792439761L;

    public BaiduMapException(ErrorCodeEnum errorCode) {
        super(errorCode);
    }

    public BaiduMapException(ErrorCodeEnum errorCode, String field) {
        super(errorCode, field);
    }

    public BaiduMapException(ErrorCodeEnum errorCode, String field, String message) {
        super(errorCode, field, message);
    }

    public BaiduMapException(ErrorCodeEnum errorCode, String field, String message, Throwable cause) {
        super(errorCode, field, message, cause);
    }

    public BaiduMapException(ErrorCodeEnum errorCode, String field, Throwable cause) {
        super(errorCode, field, cause);
    }

    public BaiduMapException(ErrorCodeEnum errorCode, String field, String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(errorCode, field, message, cause, enableSuppression, writableStackTrace);
    }

}
