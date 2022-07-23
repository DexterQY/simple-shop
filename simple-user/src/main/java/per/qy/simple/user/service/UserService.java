package per.qy.simple.user.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import per.qy.simple.common.base.model.CurrentUser;
import per.qy.simple.common.core.util.RequestUtil;
import per.qy.simple.user.mapper.RoleMapper;
import per.qy.simple.user.mapper.RolePermissionMapper;
import per.qy.simple.user.mapper.UserMapper;
import per.qy.simple.user.mapper.UserRoleMapper;
import per.qy.simple.user.model.entity.RolePermission;
import per.qy.simple.user.model.entity.User;
import per.qy.simple.user.model.entity.UserRole;
import per.qy.simple.user.model.vo.CurrentUserVo;
import per.qy.simple.user.model.vo.UserVo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * UserService
 *
 * @author : QY
 * @date : 2022/5/26
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    public UserVo getByUsername(String username) {
        User user = userMapper.getByUsername(username);
        return getUserVoFromUser(user);
    }

    public UserVo getByPhone(String phone) {
        User user = userMapper.getByPhone(phone);
        return getUserVoFromUser(user);
    }

    public UserVo getByEmail(String email) {
        User user = userMapper.getByEmail(email);
        return getUserVoFromUser(user);
    }

    public UserVo getByWxOpenId(String wxOpenId) {
        User user = userMapper.getByWxOpenId(wxOpenId);
        return getUserVoFromUser(user);
    }

    private UserVo getUserVoFromUser(User user) {
        if (user == null) {
            return null;
        }
        UserVo vo = new UserVo();
        BeanUtil.copyProperties(user, vo);
        // 查询关联角色
        List<UserRole> userRoles = userRoleMapper.listByUserId(user.getId());
        if (CollUtil.isNotEmpty(userRoles)) {
            List<String> roleCodes = userRoles.stream()
                    .map(UserRole::getRoleCode).collect(Collectors.toList());
            vo.setRoleCodes(roleCodes);
        }
        return vo;
    }

    public CurrentUserVo getCurrentUser() {
        CurrentUser user = RequestUtil.getCurrentUser();
        CurrentUserVo vo = new CurrentUserVo();
        BeanUtil.copyProperties(user, vo);
        if (CollUtil.isNotEmpty(vo.getRoleCodes())) {
            List<Long> roleIds = roleMapper.listIdByCodeIn(vo.getRoleCodes());
            if (CollUtil.isNotEmpty(roleIds)) {
                List<RolePermission> rolePermissions = rolePermissionMapper.listByRoleIdIn(roleIds);
                if (CollUtil.isNotEmpty(rolePermissions)) {
                    List<String> permissionCodes = rolePermissions.stream()
                            .map(RolePermission::getPermissionCode).collect(Collectors.toList());
                    vo.setPermissionCodes(permissionCodes);
                }
            }
        }
        return vo;
    }
}
