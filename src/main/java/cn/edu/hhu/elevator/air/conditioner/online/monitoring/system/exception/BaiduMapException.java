package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.exception;

/**
 * @author 覃国强
 * @date 2019-02-21
 */
public class BaiduMapException extends RuntimeException {

    private static final long serialVersionUID = 4148993212167064741L;
    private ResponseCode responseCode;
    private String field;

    public BaiduMapException(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public BaiduMapException(ResponseCode responseCode, String field) {
        this.responseCode = responseCode;
        this.field = field;
    }

    public BaiduMapException(ResponseCode responseCode, String field, String message) {
        super(message);
        this.responseCode = responseCode.withMessage(message);
        this.field = field;
    }

    public BaiduMapException(ResponseCode responseCode, String field, String message, Throwable cause) {
        super(message, cause);
        this.responseCode = responseCode.withMessage(message);
        this.field = field;
    }

    public BaiduMapException(ResponseCode responseCode, String field, Throwable cause) {
        super(cause);
        this.responseCode = responseCode;
        this.field = field;
    }

    public BaiduMapException(ResponseCode responseCode, String field, String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.responseCode = responseCode.withMessage(message);
        this.field = field;
    }

}
