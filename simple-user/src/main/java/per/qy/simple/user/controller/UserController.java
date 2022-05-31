package per.qy.simple.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import per.qy.simple.common.base.model.UserDto;
import per.qy.simple.common.core.annotation.BaseResponse;
import per.qy.simple.common.core.util.RequestUtil;
import per.qy.simple.user.model.UserVo;
import per.qy.simple.user.service.UserService;

/**
 * 用户接口
 *
 * @author : QY
 * @date : 2022/5/26
 */
@BaseResponse
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/current")
    public UserVo getCurrentUser() {
        return userService.getCurrentUser();
    }
}
