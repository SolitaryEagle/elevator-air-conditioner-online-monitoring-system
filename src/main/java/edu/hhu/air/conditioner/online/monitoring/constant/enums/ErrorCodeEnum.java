package edu.hhu.air.conditioner.online.monitoring.constant.enums;

/**
 * @author 覃国强
 * @date 2019-02-15
 */
public enum ErrorCodeEnum {

    // --- 4xx Client Error ---

    /**
     * 这意味着资源不存在
     */
    MISSING(460, "资源不存在"),
    /**
     * 这意味着缺失至少一个必要的字段
     */
    MISSING_FIELD(461, "资源的必要字段不存在"),
    /**
     * 这意味着字段的格式无效。该资源的文档应该能够为您提供更具体的信息。
     */
    INVALID(462, "资源的字段格式无效"),
    /**
     * 这意味着另一个资源与此域具有相同的值。这可能发生在必须具有某些唯一键（如标签名称）的资源中。
     */
    ALREADY_EXISTS(463, "资源已存在"),
    /**
     * 这意味着资源中某些需要重复输入的字段，输入不一致（如密码等）。
     */
    INCONSISTENT(464, "字段不一致"),
    /**
     * 用户账户未激活
     */
    ACCOUNT_INACTIVATED(480, "账户未激活"),
    /**
     * 无法自动登入
     */
    AUTO_LOGIN_ERROR(481, "自动登录失败"),

    // --- 5xx Server Error ---

    /**
     * 系统内部错误，当需要向调用者隐藏系统的真正错误时，使用此 ErrorCode
     */
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    /**
     * 这意味着系统加密器发生了错误
     */
    PASSWORD_ENCRYPTION_ERROR(520, "系统加密器错误"),

    /**
     * 这意味着系统解密器发生了错误
     */
    PASSWORD_DECRYPTION_ERROR(521, "系统解密器错误"),

    /**
     * 这意味着Json解析对象出错
     */
    JSON_PARSE_ERROR(522, "Json解析错误");

    private int code;
    private String message;

    ErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorCodeEnum withMessage(String message) {
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
