package per.qy.simple.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import per.qy.simple.user.model.vo.CurrentUserVo;
import per.qy.simple.user.service.UserService;

/**
 * 用户接口
 *
 * @author : QY
 * @date : 2022/5/26
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/current")
    public CurrentUserVo getCurrentUser() {
        return userService.getCurrentUser();
    }
}
