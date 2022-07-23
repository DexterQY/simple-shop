package per.qy.simple.common.base.exception;

import lombok.Getter;

/**
 * 错误码
 *
 * @author : QY
 * @date : 2022/5/26
 */
@Getter
public enum ExceptionCode {

    /** 服务器异常 */
    SERVER_ERROR(9999, "服务器异常"),
    /** 非法参数异常 */
    ILLEGAL_PARAM(10000, "非法参数异常"),
    /** 访问未授权 */
    UNAUTHORIZED(20000, "访问未授权"),
    /** 权限不足 */
    PERMISSION_DENIED(30000, "权限不足"),
    /** 数据库异常 */
    SQL_ERROR(40000, "数据库异常"),
    /** 远程服务异常 */
    REMOTE_SERVICE_ERROR(50000, "远程服务异常");

    /** code */
    private final int code;
    /** message */
    private final String message;

    ExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
