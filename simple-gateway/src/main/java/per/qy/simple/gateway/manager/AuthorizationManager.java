package per.qy.simple.gateway.manager;

import cn.hutool.core.convert.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import per.qy.simple.common.base.constant.SimpleConstant;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * 鉴权管理器，判断当前路径是否有权限访问
 *
 * @author : QY
 * @date : 2022/5/28
 */
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext context) {
        ServerHttpRequest request = context.getExchange().getRequest();
        // 跨域预检请求直接放行
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return Mono.just(new AuthorizationDecision(true));
        }

        String path = request.getPath().value();
        PathMatcher pathMatcher = new AntPathMatcher();
        // 角色权限映射
        Map<Object, Object> roleMatchPaths =
                redisTemplate.opsForHash().entries(SimpleConstant.REDIS_ROLE_PERMISSION_KEY);
        return authentication
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authority -> {
                    for (Map.Entry<Object, Object> entry : roleMatchPaths.entrySet()) {
                        String roleCode = (String) entry.getKey();
                        if (authority.equals(roleCode)) {
                            List<String> paths = Convert.toList(String.class, entry.getValue());
                            for (String p : paths) {
                                if (pathMatcher.match(p, path)) {
                                    return true;
                                }
                            }
                        }
                    }
                    return false;
                })
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }
}
