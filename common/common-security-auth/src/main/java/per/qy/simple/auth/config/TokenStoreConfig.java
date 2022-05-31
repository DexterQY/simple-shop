package per.qy.simple.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * token存储配置
 *
 * @author : QY
 * @date : 2022/5/29
 */
@Configuration
public class TokenStoreConfig {

    @Bean
    public TokenStore tokenStore(RedisConnectionFactory redisConnectionFactory) {
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
        // OAuth2Authentication没有提供无参构造方法，无法json反序列化
        // 自定义为json序列化
//        redisTokenStore.setSerializationStrategy(new StandardStringSerializationStrategy() {
//            private final ObjectMapper objectMapper = new ObjectMapper();
//
//            @Override
//            protected <T> T deserializeInternal(byte[] bytes, Class<T> aClass) {
//                try {
//                    return objectMapper.readValue(bytes, aClass);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    throw new SerializationException("Cannot deserialize", e);
//                }
//            }
//
//            @Override
//            protected byte[] serializeInternal(Object o) {
//                try {
//                    return objectMapper.writeValueAsBytes(o);
//                } catch (JsonProcessingException e) {
//                    throw new SerializationException("Cannot serialize", e);
//                }
//            }
//        });
        return redisTokenStore;
    }
}
