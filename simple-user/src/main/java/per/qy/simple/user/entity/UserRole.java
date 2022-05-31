package per.qy.simple.user.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import per.qy.simple.common.base.entity.BaseEntity;

/**
 * 用户角色
 *
 * @author : QY
 * @date : 2022/5/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserRole extends BaseEntity {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 角色编码
     */
    private String roleCode;
}