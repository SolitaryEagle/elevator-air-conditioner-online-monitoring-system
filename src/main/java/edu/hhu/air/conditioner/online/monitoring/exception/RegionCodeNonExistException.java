package edu.hhu.air.conditioner.online.monitoring.exception;

/**
 * @author 覃国强
 * @date 2019-02-15
 */
public class RegionCodeNonExistException extends BusinessException {

    private static final long serialVersionUID = -9072871078787631067L;

    public RegionCodeNonExistException(ResponseCode responseCode) {
        super(responseCode);
    }

    public RegionCodeNonExistException(ResponseCode responseCode, String field) {
        super(responseCode, field);
    }

    public RegionCodeNonExistException(ResponseCode responseCode, String field, String message) {
        super(responseCode, field, message);
    }

    public RegionCodeNonExistException(ResponseCode responseCode, String field, String message, Throwable cause) {
        super(responseCode, field, message, cause);
    }

    public RegionCodeNonExistException(ResponseCode responseCode, String field, Throwable cause) {
        super(responseCode, field, cause);
    }

    public RegionCodeNonExistException(ResponseCode responseCode, String field, String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(responseCode, field, message, cause, enableSuppression, writableStackTrace);
    }

}
