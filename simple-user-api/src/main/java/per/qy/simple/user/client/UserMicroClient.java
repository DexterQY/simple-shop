package per.qy.simple.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import per.qy.simple.user.model.vo.UserVo;

/**
 * 用户微服务接口
 *
 * @author : QY
 * @date : 2022/5/27
 */
@FeignClient(value = "simple-user", path = "/micro/user")
public interface UserMicroClient {

    @GetMapping("/getByUsername")
    UserVo getByUsername(@RequestParam("username") String username);

    @GetMapping("/getByPhone")
    UserVo getByPhone(@RequestParam("phone") String phone);

    @GetMapping("/getByEmail")
    UserVo getByEmail(@RequestParam("email") String email);

    @GetMapping("/getByWxOpenId")
    UserVo getByWxOpenId(@RequestParam("wxOpenId") String wxOpenId);
}
