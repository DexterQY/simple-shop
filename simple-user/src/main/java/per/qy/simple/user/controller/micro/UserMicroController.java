package per.qy.simple.user.controller.micro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import per.qy.simple.user.client.UserMicroClient;
import per.qy.simple.user.model.vo.UserVo;
import per.qy.simple.user.service.UserService;

/**
 * 用户微服务接口
 *
 * @author : QY
 * @date : 2022/5/26
 */
@RestController
@RequestMapping("/micro/user")
public class UserMicroController implements UserMicroClient {

    @Autowired
    private UserService userService;

    @Override
    @GetMapping("/getByUsername")
    public UserVo getByUsername(@RequestParam String username) {
        return userService.getByUsername(username);
    }

    @Override
    @GetMapping("/getByPhone")
    public UserVo getByPhone(@RequestParam String phone) {
        return userService.getByPhone(phone);
    }

    @Override
    @GetMapping("/getByEmail")
    public UserVo getByEmail(@RequestParam String email) {
        return userService.getByEmail(email);
    }

    @Override
    @GetMapping("/getByWxOpenId")
    public UserVo getByWxOpenId(@RequestParam String wxOpenId) {
        return userService.getByWxOpenId(wxOpenId);
    }
}
