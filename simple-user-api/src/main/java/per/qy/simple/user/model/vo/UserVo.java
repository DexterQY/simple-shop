package per.qy.simple.user.model.vo;

import lombok.Data;

import java.util.Collection;
import java.util.List;

/**
 * 用户
 *
 * @author : QY
 * @date : 2022/7/4
 */
@Data
public class UserVo {

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
    private Collection<String> roleCodes;
}
