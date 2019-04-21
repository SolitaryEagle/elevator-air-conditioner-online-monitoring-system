package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.exception;

/**
 * @author 覃国强
 * @date 2019-02-15
 */
public class EncryptionException extends RuntimeException {

    private static final long serialVersionUID = -5213226395922621967L;
    private ResponseCode responseCode;
    private String field;

    public EncryptionException(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public EncryptionException(ResponseCode responseCode, String field) {
        this.responseCode = responseCode;
        this.field = field;
    }

    public EncryptionException(ResponseCode responseCode, String field, String message) {
        super(message);
        this.responseCode = responseCode.withMessage(message);
        this.field = field;
    }

    public EncryptionException(ResponseCode responseCode, String field, String message, Throwable cause) {
        super(message, cause);
        this.responseCode = responseCode.withMessage(message);
        this.field = field;
    }

    public EncryptionException(ResponseCode responseCode, String field, Throwable cause) {
        super(cause);
        this.responseCode = responseCode;
        this.field = field;
    }

    public EncryptionException(ResponseCode responseCode, String field, String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.responseCode = responseCode.withMessage(message);
        this.field = field;
    }

}
