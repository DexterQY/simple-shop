package per.qy.simple.common.base.model;

import lombok.Data;

import java.util.List;

/**
 * 用户
 *
 * @author : QY
 * @date : 2022/5/26
 */
@Data
public class UserDto {

    /** id */
    private Long id;
    /** 名称 */
    private String name;
    /** 用户名 */
    private String username;
    /** 密码 */
    private String password;
    /** 手机 */
    private String phone;
    /** 邮箱 */
    private String email;
    /** 状态 */
    private Integer status;
    /** 角色编码 */
    private List<String> roleCodes;
}
