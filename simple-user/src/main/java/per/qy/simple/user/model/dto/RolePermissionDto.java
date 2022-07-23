package per.qy.simple.user.model.dto;

import lombok.Data;

/**
 * 角色权限
 *
 * @author : QY
 * @date : 2022/5/31
 */
@Data
public class RolePermissionDto {

    /** 角色id */
    private Long roleId;
    /** 角色编码 */
    private String roleCode;
    /** 权限id */
    private Long permissionId;
    /** 权限编码 */
    private String permissionCode;
    /** 权限匹配路径 */
    private String permissionMatchPath;
}
