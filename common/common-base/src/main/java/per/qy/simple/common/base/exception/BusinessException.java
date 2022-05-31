package per.qy.simple.common.base.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务异常
 *
 * @author : QY
 * @date : 2022/5/26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public final class BusinessException extends RuntimeException {

    private int code;
    private String message;

    public BusinessException() {
        this(ExceptionCode.SERVER_ERROR);
    }

    public BusinessException(String message) {
        this.code = ExceptionCode.SERVER_ERROR.getCode();
        this.message = message;
    }

    public BusinessException(ExceptionCode exceptionCode) {
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }

    public BusinessException(ExceptionCode exceptionCode, String message) {
        this.code = exceptionCode.getCode();
        this.message = message;
    }
}
