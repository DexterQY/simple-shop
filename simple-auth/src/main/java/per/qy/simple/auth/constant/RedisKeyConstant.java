package per.qy.simple.auth.constant;

/**
 * redis key 常量
 *
 * @author : QY
 * @date : 2022/5/25
 */
public interface RedisKeyConstant {

    String PREFIX = "auth:";
    String AUTH_CODE_PRE = PREFIX + "code:";
    String CAPTCHA_KEY_PRE = PREFIX + "captcha:";
    String PHONE_CODE_KEY_PRE = PREFIX + "phoneCode:";
    String EMAIL_CODE_KEY_PRE = PREFIX + "emailCode:";
}
