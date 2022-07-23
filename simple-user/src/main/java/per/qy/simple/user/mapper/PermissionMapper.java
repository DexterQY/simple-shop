package per.qy.simple.user.mapper;

import org.apache.ibatis.annotations.Param;
import per.qy.simple.user.model.entity.Permission;
import per.qy.simple.user.model.dto.RolePermissionDto;

import java.util.List;

/**
 * @author : QY
 * @date : 2022/5/30
 */
public interface PermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    int batchInsert(@Param("list") List<Permission> list);

    List<RolePermissionDto> listJoinRole();
}