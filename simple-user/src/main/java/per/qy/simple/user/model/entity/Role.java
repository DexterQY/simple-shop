package per.qy.simple.user.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import per.qy.simple.common.base.model.BaseEntity;

/**
 * 角色
 *
 * @author : QY
 * @date : 2022/5/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity {
    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 父id
     */
    private Long parentId;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 深度
     */
    private Integer depth;

    /**
     * 路径
     */
    private String path;
}