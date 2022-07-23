package per.qy.simple.user.service;

import cn.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import per.qy.simple.common.base.constant.SimpleConstant;
import per.qy.simple.user.mapper.PermissionMapper;
import per.qy.simple.user.model.dto.RolePermissionDto;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 权限服务
 *
 * @author : QY
 * @date : 2022/5/31
 */
@Service
public class PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostConstruct
    public void initRoleMatchPaths() {
        List<RolePermissionDto> dtos = permissionMapper.listJoinRole();
        if (CollUtil.isEmpty(dtos)) {
            redisTemplate.delete(SimpleConstant.REDIS_ROLE_PERMISSION);
            return;
        }

        Map<String, List<String>> roleMatchPaths = dtos.stream().collect(Collectors.groupingBy(RolePermissionDto::getRoleCode,
                Collectors.mapping(RolePermissionDto::getPermissionMatchPath, Collectors.toList())));

        redisTemplate.delete(SimpleConstant.REDIS_ROLE_PERMISSION);
        redisTemplate.opsForHash().putAll(SimpleConstant.REDIS_ROLE_PERMISSION, roleMatchPaths);
    }
}
