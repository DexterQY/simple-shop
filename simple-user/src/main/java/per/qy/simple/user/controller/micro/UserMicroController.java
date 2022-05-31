package per.qy.simple.user.controller.micro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import per.qy.simple.common.base.model.UserDto;
import per.qy.simple.user.service.UserService;

/**
 * 用户微服务接口
 *
 * @author : QY
 * @date : 2022/5/26
 */
@RestController
@RequestMapping("/micro/user")
public class UserMicroController {

    @Autowired
    private UserService userService;

    @GetMapping("/getByUsername")
    public UserDto getByUsername(@RequestParam String username) {
        return userService.getByUsername(username);
    }

    @GetMapping("/getByPhone")
    public UserDto getByPhone(@RequestParam String phone) {
        return userService.getByPhone(phone);
    }

    @GetMapping("/getByEmail")
    public UserDto getByEmail(@RequestParam String email) {
        return userService.getByEmail(email);
    }

    @GetMapping("/getByWxOpenId")
    public UserDto getByWxOpenId(@RequestParam String wxOpenId) {
        return userService.getByWxOpenId(wxOpenId);
    }
}
