package per.qy.simple.auth.model;

import lombok.Data;

/**
 * 认证请求
 *
 * @author : QY
 * @date : 2022/5/26
 */
@Data
public class AuthDto {

    /**
     * 认证类型
     *
     * @see per.qy.simple.auth.constant.AuthTypeEnum
     */
    private String authType;
    /** 用户名 */
    private String username;
    /** 密码 */
    private String password;
    /** 手机 */
    private String phone;
    /** 邮箱 */
    private String email;
    /** 验证码或第三方授权码 */
    private String code;
    /** 验证码键值 */
    private String codeKey;
}
