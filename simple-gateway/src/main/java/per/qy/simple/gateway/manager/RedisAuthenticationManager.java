package per.qy.simple.gateway.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author : QY
 * @date : 2022/5/28
 */
@Component
public class RedisAuthenticationManager implements ReactiveAuthenticationManager {

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
                    return Mono.just(oAuth2Authentication);
                }));
    }
}
