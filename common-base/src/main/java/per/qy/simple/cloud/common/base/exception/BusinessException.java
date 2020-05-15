package per.qy.simple.cloud.common.base.exception;

public final class BusinessException extends RuntimeException {

    private int code;
    private String message;
    private String data;

    public BusinessException() {
        this(ExceptionCode.CODE_500);
    }

    public BusinessException(String message) {
        this.code = ExceptionCode.CODE_500.getCode();
        this.message = message;
    }

    public BusinessException(String message, String data) {
        this.code = ExceptionCode.CODE_500.getCode();
        this.message = message;
        this.data = data;
    }

    public BusinessException(ExceptionCode exceptionCode) {
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getData() {
        return data;
    }
}
