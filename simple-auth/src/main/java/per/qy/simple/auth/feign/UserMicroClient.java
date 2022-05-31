package per.qy.simple.auth.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import per.qy.simple.common.base.model.UserDto;

/**
 * 用户微服务接口
 *
 * @author : QY
 * @date : 2022/5/27
 */
@FeignClient(value = "simple-user", path = "/micro/user")
public interface UserMicroClient {

    @GetMapping("/getByUsername")
    UserDto getByUsername(@RequestParam("username") String username);

    @GetMapping("/getByPhone")
    UserDto getByPhone(@RequestParam("phone") String phone);

    @GetMapping("/getByEmail")
    UserDto getByEmail(@RequestParam("email") String email);

    @GetMapping("/getByWxOpenId")
    UserDto getByWxOpenId(@RequestParam("wxOpenId") String wxOpenId);
}
