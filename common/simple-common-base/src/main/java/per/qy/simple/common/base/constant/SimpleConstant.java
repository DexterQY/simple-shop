package per.qy.simple.common.base.constant;

/**
 * 常量
 *
 * @author : QY
 * @date : 2022/5/29
 */
public interface SimpleConstant {

    /** 当前登录用户信息 header key */
    String HEADER_CURRENT_USER = "x-current-user";
    /** requestId header key */
    String HEADER_REQUEST_ID = "x-request-id";

    /** 角色权限 redis key */
    String REDIS_ROLE_PERMISSION = "user:rolePermission";
}
