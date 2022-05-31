package per.qy.simple.user.mapper;

import org.apache.ibatis.annotations.Param;
import per.qy.simple.user.entity.Role;

import java.util.Collection;
import java.util.List;

/**
 * @author : QY
 * @date : 2022/5/29
 */
public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    int batchInsert(@Param("list") List<Role> list);

    List<Long> listIdByCodeIn(@Param("codeCollection") Collection<String> codeCollection);
}