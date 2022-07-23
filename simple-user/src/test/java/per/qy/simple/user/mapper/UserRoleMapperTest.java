package per.qy.simple.user.mapper;

import cn.hutool.core.util.IdUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import per.qy.simple.user.model.entity.UserRole;

/**
 * @author : QY
 * @date : 2022/5/30
 */
public class UserRoleMapperTest {
    private static UserRoleMapper mapper;

    @BeforeClass
    public static void setUpMybatisDatabase() {
        SqlSessionFactory builder = new SqlSessionFactoryBuilder().build(UserRoleMapperTest.class.getClassLoader().getResourceAsStream("mybatisTestConfiguration/UserRoleMapperTestConfiguration.xml"));
        //you can use builder.openSession(false) to not commit to database
        mapper = builder.getConfiguration().getMapper(UserRoleMapper.class, builder.openSession(true));
    }

    @Test
    public void testInsert() {
        UserRole userRole = new UserRole();
        userRole.setId(IdUtil.getSnowflakeNextId());
        userRole.setUserId(1530916443968294912L);
        userRole.setRoleId(1530949462938726400L);
        userRole.setRoleCode("admin");
        mapper.insert(userRole);
    }
}
