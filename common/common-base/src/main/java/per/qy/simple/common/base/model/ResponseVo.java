package per.qy.simple.common.base.model;

import lombok.Data;
import per.qy.simple.common.base.exception.ExceptionCode;

/**
 * 统一响应
 *
 * @author : QY
 * @date : 2022/5/26
 */
@Data
public class ResponseVo {

    private static final int SUCCESS_CODE = 0;
    private static final ResponseVo SUCCESS_VO = new ResponseVo();

    /** requestId */
    private String requestId;
    /** 状态码 */
    private int code = SUCCESS_CODE;
    /** 错误信息 */
    private String message;
    /** 业务数据 */
    private Object data;

    private ResponseVo() {
    }

    private ResponseVo(Object data) {
        this.data = data;
    }

    private ResponseVo(Object data, String message) {
        this.data = data;
        this.message = message;
    }

    private ResponseVo(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private ResponseVo(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResponseVo success() {
        return SUCCESS_VO;
    }

    public static ResponseVo success(Object data) {
        return new ResponseVo(data);
    }

    public static ResponseVo success(Object data, String message) {
        return new ResponseVo(data, message);
    }

    public static ResponseVo fail() {
        return new ResponseVo(ExceptionCode.SERVER_ERROR.getCode(),
                ExceptionCode.SERVER_ERROR.getMessage());
    }

    public static ResponseVo fail(String message) {
        return new ResponseVo(ExceptionCode.SERVER_ERROR.getCode(), message);
    }

    public static ResponseVo fail(ExceptionCode exceptionCode) {
        return new ResponseVo(exceptionCode.getCode(), exceptionCode.getMessage());
    }

    public static ResponseVo fail(ExceptionCode exceptionCode, String message) {
        return new ResponseVo(exceptionCode.getCode(), message);
    }

    public static ResponseVo fail(int code, String message) {
        return new ResponseVo(code, message);
    }

    public boolean succeeded() {
        return this.code == SUCCESS_CODE;
    }
}
