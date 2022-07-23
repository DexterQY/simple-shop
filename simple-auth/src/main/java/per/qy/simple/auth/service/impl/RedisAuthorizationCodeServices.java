package per.qy.simple.auth.service.impl;

import cn.hutool.core.util.IdUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.SerializationCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
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
public class RedisAuthorizationCodeServices implements AuthorizationCodeServices {

    private final RedissonClient redissonClient;

    /**
     * 授权码有效期默认5分钟
     */
    @Value("${simple.auth.authorization-code.timeout:300}")
    private long timeout;

    public RedisAuthorizationCodeServices(RedissonClient redissonClient) {
        // 新创建一个使用JDK序列化的客户端，OAuth2Authentication未提供无参构造方法无法json反序列化
        Config config = redissonClient.getConfig();
        config.setCodec(new SerializationCodec());
        this.redissonClient = Redisson.create(config);
    }

    @Override
    public String createAuthorizationCode(OAuth2Authentication authentication) {
        String code = IdUtil.fastSimpleUUID();
        redissonClient.getBucket(RedisKeyConstant.AUTH_CODE_PRE + code)
                .set(authentication, timeout, TimeUnit.SECONDS);
        return code;
    }

    @Override
    public OAuth2Authentication consumeAuthorizationCode(String code) throws InvalidGrantException {
        String key = RedisKeyConstant.AUTH_CODE_PRE + code;
        OAuth2Authentication authentication = (OAuth2Authentication) redissonClient.getBucket(key).get();
        redissonClient.getBucket(key).delete();
        if (authentication == null) {
            throw new InvalidGrantException("Invalid authorization code: " + code);
        }
        return authentication;
    }
}
