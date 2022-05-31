package per.qy.simple.user.mapper;
import cn.hutool.core.util.IdUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import per.qy.simple.user.entity.RolePermission;

/**
 * @author : QY
 * @date : 2022/5/30
 */
public class RolePermissionMapperTest {
    private static RolePermissionMapper mapper;

    @BeforeClass
    public static void setUpMybatisDatabase() {
        SqlSessionFactory builder = new SqlSessionFactoryBuilder().build(RolePermissionMapperTest.class.getClassLoader().getResourceAsStream("mybatisTestConfiguration/RolePermissionMapperTestConfiguration.xml"));
        //you can use builder.openSession(false) to not commit to database
        mapper = builder.getConfiguration().getMapper(RolePermissionMapper.class, builder.openSession(true));
    }

    @Test
    public void testInsert() {
        RolePermission rolePermission = new RolePermission();
        rolePermission.setId(IdUtil.getSnowflakeNextId());
        rolePermission.setRoleId(1530949462938726400L);
        rolePermission.setPermissionId(1530952382564352000L);
        rolePermission.setPermissionCode("user");
        mapper.insert(rolePermission);
    }
}
