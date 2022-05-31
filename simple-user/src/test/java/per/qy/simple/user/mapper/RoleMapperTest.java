package per.qy.simple.user.mapper;

import cn.hutool.core.util.IdUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import per.qy.simple.user.entity.Role;

/**
 * @author : QY
 * @date : 2022/5/30
 */
public class RoleMapperTest {
    private static RoleMapper mapper;

    @BeforeClass
    public static void setUpMybatisDatabase() {
        SqlSessionFactory builder = new SqlSessionFactoryBuilder().build(RoleMapperTest.class.getClassLoader().getResourceAsStream("mybatisTestConfiguration/RoleMapperTestConfiguration.xml"));
        //you can use builder.openSession(false) to not commit to database
        mapper = builder.getConfiguration().getMapper(RoleMapper.class, builder.openSession(true));
    }

    @Test
    public void testInsert() {
        Role role = new Role();
        role.setId(IdUtil.getSnowflakeNextId());
        role.setCode("admin");
        role.setName("admin");
        role.setStatus(1);
        role.setParentId(0L);
        role.setSeq(0);
        role.setDepth(1);
        role.setPath("#" + role.getId() + "#");
        mapper.insert(role);
    }
}
