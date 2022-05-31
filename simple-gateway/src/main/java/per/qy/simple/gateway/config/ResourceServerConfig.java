package per.qy.simple.gateway.config;

import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.web.server.ServerBearerTokenAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import per.qy.simple.common.base.constant.SimpleConstant;
import per.qy.simple.common.base.exception.ExceptionCode;
import per.qy.simple.common.base.model.ResponseVo;
import per.qy.simple.gateway.manager.AuthorizationManager;
import per.qy.simple.gateway.manager.RedisAuthenticationManager;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 资源服务器配置
 *
 * @author : QY
 * @date : 2022/5/28
 */
@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfig {

    @Autowired
    private SecurityPathConfig securityPathConfig;
    @Autowired
    private AuthorizationManager authorizationManager;
    @Autowired
    private RedisAuthenticationManager reactiveAuthenticationManager;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        // 认证过滤器
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(reactiveAuthenticationManager);
        authenticationWebFilter.setRequiresAuthenticationMatcher(requiresAuthenticationMatcher());
        authenticationWebFilter.setServerAuthenticationConverter(new ServerBearerTokenAuthenticationConverter());
        authenticationWebFilter.setAuthenticationSuccessHandler(serverAuthenticationSuccessHandler());
        authenticationWebFilter.setAuthenticationFailureHandler(serverAuthenticationFailureHandler());
        http.addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION);

        http.authorizeExchange()
                // 拒绝访问路径
                .pathMatchers(securityPathConfig.getDenyPaths().toArray(new String[0])).denyAll()
                // 忽略认证路径
                .pathMatchers(securityPathConfig.getIgnoreAuthPaths().toArray(new String[0])).permitAll()
                // 自定义鉴权
                .anyExchange().access(authorizationManager)
                .and()
                .exceptionHandling()
                // 自定义未授权异常返回
                .authenticationEntryPoint(serverAuthenticationEntryPoint())
                // 自定义权限不足异常返回
                .accessDeniedHandler(serverAccessDeniedHandler())
                .and().csrf().disable();
        return http.build();
    }

    private ServerWebExchangeMatcher requiresAuthenticationMatcher() {
        return exchange -> {
            String path = exchange.getRequest().getPath().value();
            PathMatcher pathMatcher = new AntPathMatcher();
            for (String matchPath : securityPathConfig.getDenyPaths()) {
                if (pathMatcher.match(matchPath, path)) {
                    return ServerWebExchangeMatcher.MatchResult.notMatch();
                }
            }
            for (String matchPath : securityPathConfig.getIgnoreAuthPaths()) {
                if (pathMatcher.match(matchPath, path)) {
                    return ServerWebExchangeMatcher.MatchResult.notMatch();
                }
            }
            return ServerWebExchangeMatcher.MatchResult.match();
        };
    }

    private ServerAuthenticationSuccessHandler serverAuthenticationSuccessHandler() {
        return (webFilterExchange, authentication) -> {
            ServerWebExchange exchange = webFilterExchange.getExchange();
            String userInfo = JSONUtil.toJsonStr(authentication.getPrincipal());
            userInfo = Base64.getEncoder().encodeToString(userInfo.getBytes(StandardCharsets.UTF_8));
            ServerHttpRequest request = exchange.getRequest().mutate()
                    .header(SimpleConstant.HEADER_USER_INFO_KEY, userInfo).build();
            exchange = exchange.mutate().request(request).build();
            return webFilterExchange.getChain().filter(exchange);
        };
    }

    private ServerAuthenticationFailureHandler serverAuthenticationFailureHandler() {
        return (webFilterExchange, exception) -> {
            ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
            response.setStatusCode(HttpStatus.OK);
            response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            String body = JSONUtil.toJsonStr(ResponseVo.fail(ExceptionCode.UNAUTHORIZED,
                    exception.getMessage()));
            DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(buffer));
        };
    }

    private ServerAuthenticationEntryPoint serverAuthenticationEntryPoint() {
        return (exchange, ex) -> {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.OK);
            response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            String body = JSONUtil.toJsonStr(ResponseVo.fail(ExceptionCode.UNAUTHORIZED));
            DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(buffer));
        };
    }

    private ServerAccessDeniedHandler serverAccessDeniedHandler() {
        return (exchange, denied) -> {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.OK);
            response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            String body = JSONUtil.toJsonStr(ResponseVo.fail(ExceptionCode.PERMISSION_DENIED));
            DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(buffer));
        };
    }
}
