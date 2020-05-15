package per.qy.simple.cloud.common.base.exception;

public enum ExceptionCode {

    CODE_403(403, "权限不足，禁止访问"),
    CODE_500(500, "服务器异常"),
    CODE_1001(1001, "尚未登录或登录超时"),
    CODE_1002(1002, "您的账号已在别处登录，此处将退出登录"),
    CODE_1003(1003, "token不存在"),
    CODE_1004(1004, "必填参数为空或参数格式错误"),
    CODE_1005(1005, "请求方法不支持"),
    ;

    private int code;
    private String message;

    private ExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
