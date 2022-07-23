package per.qy.simple.auth.constant;

import per.qy.simple.auth.model.dto.AuthDto;

/**
 * 认证参数ThreadLocal
 *
 * @author : QY
 * @date : 2022/5/26
 */
public class AuthReqThreadLocal {

    private static final ThreadLocal<AuthDto> THREAD_LOCAL = new ThreadLocal<>();

    public static void set(AuthDto req) {
        THREAD_LOCAL.set(req);
    }

    public static AuthDto get() {
        return THREAD_LOCAL.get();
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
