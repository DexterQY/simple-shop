package per.qy.simple.user.mapper;

import org.apache.ibatis.annotations.Param;
import per.qy.simple.user.model.entity.RolePermission;

import java.util.Collection;
import java.util.List;

/**
 * @author : QY
 * @date : 2022/5/30
 */
public interface RolePermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RolePermission record);

    int insertSelective(RolePermission record);

    RolePermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RolePermission record);

    int updateByPrimaryKey(RolePermission record);

    int batchInsert(@Param("list") List<RolePermission> list);

    List<RolePermission> listByRoleId(@Param("roleId") Long roleId);

    List<RolePermission> listByRoleIdIn(@Param("roleIdCollection") Collection<Long> roleIdCollection);
}