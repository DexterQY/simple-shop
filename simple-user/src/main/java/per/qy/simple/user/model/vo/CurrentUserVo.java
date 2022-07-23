package per.qy.simple.user.model.vo;

import lombok.Data;

import java.util.List;

/**
 * 当前用户
 *
 * @author : QY
 * @date : 2022/5/30
 */
@Data
public class CurrentUserVo {

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
    /** 角色编码 */
    private List<String> roleCodes;
    /** 权限编码 */
    private List<String> permissionCodes;
}
