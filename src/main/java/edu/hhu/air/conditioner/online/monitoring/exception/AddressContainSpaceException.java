package edu.hhu.air.conditioner.online.monitoring.exception;

/**
 * @author 覃国强
 * @date 2019-02-15
 */
public class AddressContainSpaceException extends BusinessException {

    private static final long serialVersionUID = -9072871078787631067L;

    public AddressContainSpaceException(ResponseCode responseCode) {
        super(responseCode);
    }

    public AddressContainSpaceException(ResponseCode responseCode, String field) {
        super(responseCode, field);
    }

    public AddressContainSpaceException(ResponseCode responseCode, String field, String message) {
        super(responseCode, field, message);
    }

    public AddressContainSpaceException(ResponseCode responseCode, String field, String message, Throwable cause) {
        super(responseCode, field, message, cause);
    }

    public AddressContainSpaceException(ResponseCode responseCode, String field, Throwable cause) {
        super(responseCode, field, cause);
    }

    public AddressContainSpaceException(ResponseCode responseCode, String field, String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(responseCode, field, message, cause, enableSuppression, writableStackTrace);
    }

}
