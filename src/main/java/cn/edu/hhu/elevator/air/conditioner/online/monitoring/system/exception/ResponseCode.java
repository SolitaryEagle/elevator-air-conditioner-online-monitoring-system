package cn.edu.hhu.elevator.air.conditioner.online.monitoring.system.exception;

/**
 * @author 覃国强
 * @date 2019-02-15
 */
public enum ResponseCode {

    /**
     * 这意味着资源不存在
     */
    MISSING(420, "资源不存在"),
    /**
     * 这意味着缺失至少一个必要的字段
     */
    MISSING_FIELD(421, "资源的必要字段不存在"),
    /**
     * 这意味着字段的格式无效。该资源的文档应该能够为您提供更具体的信息。
     */
    INVALID(422, "资源的字段格式无效"),
    /**
     * 这意味着另一个资源与此域具有相同的值。这可能发生在必须具有某些唯一键（如标签名称）的资源中。
     */
    ALREADY_EXISTS(423, "资源已存在"),
    /**
     * 这意味着资源中某些需要重复输入的字段，输入不一致（如密码等）。
     */
    INCONSISTENT(424, "字段不一致"),


    /**
     * 这意味着系统加密器发生了错误
     */
    PASSWORD_ENCRYPTION_ERROR(510, "系统加密器错误");




    private int code;
    private String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseCode withMessage(String message) {
        this.message = message;
        return this;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
