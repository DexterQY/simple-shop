package per.qy.simple.user.mapper;

import cn.hutool.core.util.IdUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import per.qy.simple.user.entity.Permission;

/**
 * @author : QY
 * @date : 2022/5/30
 */
public class PermissionMapperTest {
    private static PermissionMapper mapper;

    @BeforeClass
    public static void setUpMybatisDatabase() {
        SqlSessionFactory builder = new SqlSessionFactoryBuilder().build(PermissionMapperTest.class.getClassLoader().getResourceAsStream("mybatisTestConfiguration/PermissionMapperTestConfiguration.xml"));
        //you can use builder.openSession(false) to not commit to database
        mapper = builder.getConfiguration().getMapper(PermissionMapper.class, builder.openSession(true));
    }

    @Test
    public void testInsert() {
        Permission permission = new Permission();
        permission.setId(IdUtil.getSnowflakeNextId());
        permission.setCode("user");
        permission.setName("用户管理");
        permission.setMatchPath("/simple-user/user/**");
        permission.setStatus(1);
        permission.setParentId(0L);
        permission.setSeq(0);
        permission.setDepth(1);
        permission.setPath("#" + permission.getId() + "#");
        mapper.insert(permission);
    }
}
