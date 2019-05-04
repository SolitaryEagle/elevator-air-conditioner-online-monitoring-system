package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.exception;

/**
 * @author 覃国强
 * @date 2019-02-21
 */
public class BaiduMapException extends BusinessException {

    private static final long serialVersionUID = -7301587164810244828L;

    public BaiduMapException(ResponseCode responseCode) {
        super(responseCode);
    }

    public BaiduMapException(ResponseCode responseCode, String field) {
        super(responseCode, field);
    }

    public BaiduMapException(ResponseCode responseCode, String field, String message) {
        super(responseCode, field, message);
    }

    public BaiduMapException(ResponseCode responseCode, String field, String message, Throwable cause) {
        super(responseCode, field, message, cause);
    }

    public BaiduMapException(ResponseCode responseCode, String field, Throwable cause) {
        super(responseCode, field, cause);
    }

    public BaiduMapException(ResponseCode responseCode, String field, String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(responseCode, field, message, cause, enableSuppression, writableStackTrace);
    }

}
