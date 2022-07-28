package per.qy.simple.gateway.manager;

import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.stereotype.Component;
import per.qy.simple.auth.model.AuthUserDetails;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;

/**
 * @author : QY
 * @date : 2022/5/28
 */
@Slf4j
@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    private TokenStore tokenStore;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.justOrEmpty(authentication)
                .filter(a -> a instanceof BearerTokenAuthenticationToken)
                .cast(BearerTokenAuthenticationToken.class)
                .map(BearerTokenAuthenticationToken::getToken)
                .flatMap((token -> {
                    OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
                    if (oAuth2AccessToken == null) {
                        return Mono.error(new InvalidBearerTokenException("invalid access token,please check"));
                    } else if (oAuth2AccessToken.isExpired()) {
                        return Mono.error(new InvalidBearerTokenException("access token has expired,please reacquire token"));
                    }

                    OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(token);
                    if (oAuth2Authentication == null) {
                        return Mono.error(new InvalidBearerTokenException("invalid access token"));
                    }

                    if (tokenStore instanceof JwtTokenStore) {
                        // jwt模式手动修改principal，兼容后续AuthorizationManager校验
                        AuthUserDetails userDetails = BeanUtil.fillBeanWithMap(oAuth2AccessToken.getAdditionalInformation(),
                                new AuthUserDetails(), true);
                        UsernamePasswordAuthenticationToken userAuthentication =
                                (UsernamePasswordAuthenticationToken) oAuth2Authentication.getUserAuthentication();
                        Class<? extends UsernamePasswordAuthenticationToken> userAuthenticationClass = userAuthentication.getClass();
                        try {
                            Field principal = userAuthenticationClass.getDeclaredField("principal");
                            principal.setAccessible(true);
                            principal.set(userAuthentication, userDetails);
                        } catch (NoSuchFieldException | IllegalAccessException e) {
                            log.error("jwt set principal error", e);
                        }
                    }
                    return Mono.just(oAuth2Authentication);
                }));
    }
}
