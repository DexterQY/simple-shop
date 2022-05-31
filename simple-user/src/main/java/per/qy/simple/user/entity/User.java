package per.qy.simple.user.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import per.qy.simple.common.base.entity.BaseEntity;

/**
 * 用户
 *
 * @author : QY
 * @date : 2022/5/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    /**
     * 姓名
     */
    private String name;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 微信openId
     */
    private String wxOpenId;

    /**
     * 状态 (0=无效,1=有效)
     */
    private Integer status;
}