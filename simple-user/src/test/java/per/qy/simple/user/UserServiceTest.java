package per.qy.simple.user;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import per.qy.simple.user.mapper.RoleMapper;
import per.qy.simple.user.mapper.RolePermissionMapper;
import per.qy.simple.user.mapper.UserMapper;
import per.qy.simple.user.mapper.UserRoleMapper;
import per.qy.simple.user.model.entity.User;
import per.qy.simple.user.model.vo.UserVo;
import per.qy.simple.user.service.UserService;

import java.util.List;

/**
 * UserServiceTest
 *
 * @author : QY
 * @date : 2022/8/11
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserMapper userMapper;
    @Mock
    private UserRoleMapper userRoleMapper;
    @Mock
    private RoleMapper roleMapper;
    @Mock
    private RolePermissionMapper rolePermissionMapper;

    @Test
    public void getByUsername() {
        User user = new User();
        user.setName("admin");
        user.setUsername("admin");
        user.setPhone("123456");
        user.setEmail("123456");
        user.setStatus(1);
        user.setId(1L);
        Mockito.when(userMapper.getByUsername("admin")).thenReturn(user);
        Mockito.when(userRoleMapper.listByUserId(1L)).thenReturn(List.of());
        UserVo admin = userService.getByUsername("admin");
        Assert.assertNotNull(admin);
        System.out.println(admin);
    }
}
