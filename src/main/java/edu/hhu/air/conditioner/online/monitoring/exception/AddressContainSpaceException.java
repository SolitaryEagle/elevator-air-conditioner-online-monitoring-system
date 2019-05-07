package edu.hhu.air.conditioner.online.monitoring.exception;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.ErrorCodeEnum;

/**
 * @author 覃国强
 * @date 2019-02-15
 */
public class AddressContainSpaceException extends BusinessException {

    private static final long serialVersionUID = 84602962938549538L;

    public AddressContainSpaceException(ErrorCodeEnum errorCode) {
        super(errorCode);
    }

    public AddressContainSpaceException(ErrorCodeEnum errorCode, String field) {
        super(errorCode, field);
    }

    public AddressContainSpaceException(ErrorCodeEnum errorCode, String field, String message) {
        super(errorCode, field, message);
    }

    public AddressContainSpaceException(ErrorCodeEnum errorCode, String field, String message, Throwable cause) {
        super(errorCode, field, message, cause);
    }

    public AddressContainSpaceException(ErrorCodeEnum errorCode, String field, Throwable cause) {
        super(errorCode, field, cause);
    }

    public AddressContainSpaceException(ErrorCodeEnum errorCode, String field, String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(errorCode, field, message, cause, enableSuppression, writableStackTrace);
    }

}
