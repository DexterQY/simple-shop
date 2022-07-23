package per.qy.simple.user.mapper;

import org.apache.ibatis.annotations.Param;
import per.qy.simple.user.model.entity.User;

import java.util.List;

/**
 * @author : QY
 * @date : 2022/5/29
 */
public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int batchInsert(@Param("list") List<User> list);

    /**
     * 根据用户名查询
     *
     * @param username username
     * @return User
     */
    User getByUsername(String username);

    /**
     * 根据用户名查询
     *
     * @param phone phone
     * @return User
     */
    User getByPhone(String phone);

    /**
     * 根据用户名查询
     *
     * @param email email
     * @return User
     */
    User getByEmail(String email);

    /**
     * 根据用户名查询
     *
     * @param wxOpenId wxOpenId
     * @return User
     */
    User getByWxOpenId(String wxOpenId);
}