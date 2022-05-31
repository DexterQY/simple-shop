package per.qy.simple.auth.service.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.stereotype.Component;
import per.qy.simple.auth.constant.RedisKeyConstant;

import java.util.concurrent.TimeUnit;

/**
 * 授权码服务
 *
 * @author : QY
 * @date : 2022/5/25
 */
@Component
public class RedisAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisAuthorizationCodeServices(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void store(String code, OAuth2Authentication authentication) {
        // 授权码有效期5分钟
        redisTemplate.opsForValue().set(RedisKeyConstant.AUTH_CODE_PRE + code,
                authentication, 5L, TimeUnit.MINUTES);
    }

    @Override
    protected OAuth2Authentication remove(String code) {
        String key = RedisKeyConstant.AUTH_CODE_PRE + code;
        OAuth2Authentication authentication = (OAuth2Authentication) redisTemplate.opsForValue().get(key);
        redisTemplate.delete(key);
        return authentication;
    }
}
