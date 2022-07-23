package per.qy.simple.user.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import per.qy.simple.common.base.model.BaseEntity;

/**
 * 角色权限
 *
 * @author : QY
 * @date : 2022/5/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RolePermission extends BaseEntity {
    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 权限id
     */
    private Long permissionId;

    /**
     * 权限编码
     */
    private String permissionCode;
}