package per.qy.simple.user.model;

import lombok.Data;

import java.util.List;

/**
 * 用户视图
 *
 * @author : QY
 * @date : 2022/5/30
 */
@Data
public class UserVo {

    /** id */
    private Long id;
    /** 名称 */
    private String name;
    /** 用户名 */
    private String username;
    /** 手机 */
    private String phone;
    /** 邮箱 */
    private String email;
    /** 状态 */
    private Integer status;
    /** 角色编码 */
    private List<String> roleCodes;
    /** 权限编码 */
    private List<String> permissionCodes;
}
