package per.qy.simple.cloud.common.base.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import per.qy.simple.cloud.common.base.exception.ExceptionCode;

@Data
public class ResponseVo<T> {

    private static final int CODE_200 = 200;
    private static final String MESSAGE_200 = "访问成功";
    private static final ResponseVo SUCCESS_VO = new ResponseVo();

    @ApiModelProperty("状态码")
    private int code = CODE_200;
    @ApiModelProperty("状态信息")
    private String message = MESSAGE_200;
    @ApiModelProperty("业务数据")
    private T data;

    private ResponseVo() {
    }

    private ResponseVo(T data) {
        this.data = data;
    }

    private ResponseVo(T data, String message) {
        this.data = data;
        this.message = message;
    }

    private ResponseVo(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private ResponseVo(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResponseVo success() {
        return SUCCESS_VO;
    }

    public static <T> ResponseVo<T> success(T data) {
        return new ResponseVo<>(data);
    }

    public static <T> ResponseVo<T> success(T data, String message) {
        return new ResponseVo<>(data, message);
    }

    public static ResponseVo fail() {
        return new ResponseVo(ExceptionCode.CODE_500.getCode(), ExceptionCode.CODE_500.getMessage());
    }

    public static ResponseVo fail(String message) {
        return new ResponseVo(ExceptionCode.CODE_500.getCode(), message);
    }

    public static ResponseVo fail(ExceptionCode exceptionCode) {
        return new ResponseVo(exceptionCode.getCode(), exceptionCode.getMessage());
    }

    public static ResponseVo<String> fail(ExceptionCode exceptionCode, String data) {
        return new ResponseVo<>(exceptionCode.getCode(), exceptionCode.getMessage(), data);
    }

    public static ResponseVo fail(int code, String message) {
        return new ResponseVo(code, message);
    }
}
