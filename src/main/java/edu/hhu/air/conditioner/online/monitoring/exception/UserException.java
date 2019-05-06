package edu.hhu.air.conditioner.online.monitoring.exception;

/**
 * @author 覃国强
 * @date 2019-02-15
 */
public class UserException extends BusinessException {

    private static final long serialVersionUID = -509363418131343068L;

    public UserException(ResponseCode responseCode) {
        super(responseCode);
    }

    public UserException(ResponseCode responseCode, String field) {
        super(responseCode, field);
    }

    public UserException(ResponseCode responseCode, String field, String message) {
        super(responseCode, field, message);
    }

    public UserException(ResponseCode responseCode, String field, String message, Throwable cause) {
        super(responseCode, field, message, cause);
    }

    public UserException(ResponseCode responseCode, String field, Throwable cause) {
        super(responseCode, field, cause);
    }

    public UserException(ResponseCode responseCode, String field, String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(responseCode, field, message, cause, enableSuppression, writableStackTrace);
    }

}
