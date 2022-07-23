package per.qy.simple.user.mapper;

import org.apache.ibatis.annotations.Param;
import per.qy.simple.user.model.entity.UserRole;

import java.util.List;

/**
 * @author : QY
 * @date : 2022/5/31
 */
public interface UserRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);

    int batchInsert(@Param("list") List<UserRole> list);

    List<UserRole> listByUserId(@Param("userId") Long userId);
}
