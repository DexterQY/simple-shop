package per.qy.simple.user.fallback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import per.qy.simple.user.client.UserMicroClient;
import per.qy.simple.user.model.vo.UserVo;

/**
 * 熔断降级
 *
 * @author : QY
 * @date : 2022/8/3
 */
@Slf4j
@Component
public class UserMicroClientFallbackFactory implements FallbackFactory<UserMicroClient> {

    @Override
    public UserMicroClient create(Throwable cause) {
        log.error("feign request error", cause);
        return new UserMicroClient() {
            @Override
            public UserVo getByUsername(String username) {
                return null;
            }

            @Override
            public UserVo getByPhone(String phone) {
                return null;
            }

            @Override
            public UserVo getByEmail(String email) {
                return null;
            }

            @Override
            public UserVo getByWxOpenId(String wxOpenId) {
                return null;
            }
        };
    }
}
