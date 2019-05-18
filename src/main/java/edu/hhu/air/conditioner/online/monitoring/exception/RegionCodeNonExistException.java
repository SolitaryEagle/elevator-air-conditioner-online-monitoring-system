package edu.hhu.air.conditioner.online.monitoring.exception;

import edu.hhu.air.conditioner.online.monitoring.constant.enums.ErrorCodeEnum;

/**
 * @author 覃国强
 * @date 2019-02-15
 */
public class RegionCodeNonExistException extends BusinessException {

    private static final long serialVersionUID = -1743357322394228082L;

    public RegionCodeNonExistException(ErrorCodeEnum errorCode) {
        super(errorCode);
    }

    public RegionCodeNonExistException(ErrorCodeEnum errorCode, String field) {
        super(errorCode, field);
    }

    public RegionCodeNonExistException(ErrorCodeEnum errorCode, String field, String message) {
        super(errorCode, field, message);
    }

    public RegionCodeNonExistException(ErrorCodeEnum errorCode, String field, String message, Throwable cause) {
        super(errorCode, field, message, cause);
    }

    public RegionCodeNonExistException(ErrorCodeEnum errorCode, String field, Throwable cause) {
        super(errorCode, field, cause);
    }

    public RegionCodeNonExistException(ErrorCodeEnum errorCode, String field, String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(errorCode, field, message, cause, enableSuppression, writableStackTrace);
    }

}
