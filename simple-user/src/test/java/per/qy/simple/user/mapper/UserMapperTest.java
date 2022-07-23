package per.qy.simple.user.mapper;

import cn.hutool.core.util.IdUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import per.qy.simple.user.model.entity.User;

/**
 * @author : QY
 * @date : 2022/5/29
 */
public class UserMapperTest {
    private static UserMapper mapper;

    @BeforeClass
    public static void setUpMybatisDatabase() {
        SqlSessionFactory builder = new SqlSessionFactoryBuilder().build(UserMapperTest.class.getClassLoader().getResourceAsStream("mybatisTestConfiguration/UserMapperTestConfiguration.xml"));
        //you can use builder.openSession(false) to not commit to database
        mapper = builder.getConfiguration().getMapper(UserMapper.class, builder.openSession(true));
    }

    @Test
    public void testInsert() {
        User user = new User();
        user.setId(IdUtil.getSnowflakeNextId());
        user.setName("admin");
        user.setUsername("admin");
        user.setPassword(new BCryptPasswordEncoder().encode("123456"));
        user.setPhone("13312341234");
        user.setEmail("admin@qq.com");
        user.setStatus(1);
        mapper.insert(user);
    }
}
