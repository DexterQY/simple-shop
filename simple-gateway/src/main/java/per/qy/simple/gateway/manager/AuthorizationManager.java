package per.qy.simple.gateway.manager;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import per.qy.simple.auth.model.AuthUserDetails;
import per.qy.simple.common.base.constant.SimpleConstant;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

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

        return authentication
                .filter(Authentication::isAuthenticated)
                .map(auth -> {
                    AuthUserDetails principal = (AuthUserDetails) auth.getPrincipal();
                    if (CollUtil.isNotEmpty(principal.getRoleCodes())) {
                        List<Object> codes = principal.getRoleCodes().stream()
                                .map(code -> (Object) code).collect(Collectors.toList());
                        // 角色权限映射
                        List<Object> matchPaths = redisTemplate.opsForHash()
                                .multiGet(SimpleConstant.REDIS_ROLE_PERMISSION, codes);
                        if (CollUtil.isNotEmpty(matchPaths)) {
                            for (Object matchPathList : matchPaths) {
                                List<String> paths = Convert.toList(String.class, matchPathList);
                                for (String p : paths) {
                                    for (String s : p.split(",")) {
                                        if (pathMatcher.match(s, path)) {
                                            return new AuthorizationDecision(true);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return new AuthorizationDecision(false);
                });
    }
}
