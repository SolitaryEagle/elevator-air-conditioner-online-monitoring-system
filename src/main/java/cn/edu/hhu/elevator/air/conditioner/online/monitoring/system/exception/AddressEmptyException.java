package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.exception;

/**
 * @author 覃国强
 * @date 2019-02-15
 */
public class AddressEmptyException extends BusinessException {

    private static final long serialVersionUID = -9072871078787631067L;

    public AddressEmptyException(ResponseCode responseCode) {
        super(responseCode);
    }

    public AddressEmptyException(ResponseCode responseCode, String field) {
        super(responseCode, field);
    }

    public AddressEmptyException(ResponseCode responseCode, String field, String message) {
        super(responseCode, field, message);
    }

    public AddressEmptyException(ResponseCode responseCode, String field, String message, Throwable cause) {
        super(responseCode, field, message, cause);
    }

    public AddressEmptyException(ResponseCode responseCode, String field, Throwable cause) {
        super(responseCode, field, cause);
    }

    public AddressEmptyException(ResponseCode responseCode, String field, String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(responseCode, field, message, cause, enableSuppression, writableStackTrace);
    }

}
