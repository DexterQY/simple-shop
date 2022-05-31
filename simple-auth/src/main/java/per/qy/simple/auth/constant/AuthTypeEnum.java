package per.qy.simple.auth.constant;

import lombok.Getter;

/**
 * 认证类型
 *
 * @author : QY
 * @date : 2022/5/26
 */
@Getter
public enum AuthTypeEnum {

    /** 用户名密码认证 */
    USERNAME("username", "用户名密码认证"),
    /** 手机短信认证 */
    PHONE("phone", "手机短信认证"),
    /** 邮箱认证 */
    EMAIL("email", "邮箱认证"),
    /** 微信认证 */
    WX("wx", "微信认证");

    /** code */
    private final String code;
    /** text */
    private final String text;

    AuthTypeEnum(String code, String text) {
        this.code = code;
        this.text = text;
    }
}
