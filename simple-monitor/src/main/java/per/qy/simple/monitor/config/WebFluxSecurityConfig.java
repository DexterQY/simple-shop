package per.qy.simple.monitor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;

/**
 * 安全配置
 *
 * @author : QY
 * @date : 2022/5/28
 */
@Configuration
@EnableWebFluxSecurity
public class WebFluxSecurityConfig {

    @Value("${spring.boot.admin.contextPath:}")
    private String adminContextPath;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange()
                // 1.配置所有静态资源和登录页可以公开访问
                .pathMatchers(adminContextPath + "/actuator/**").permitAll()
                .pathMatchers(adminContextPath + "/assets/**").permitAll()
                .pathMatchers(adminContextPath + "/login").permitAll()
                .anyExchange().authenticated()
                .and()
                // 2.配置登录和登出路径
                .formLogin().loginPage(adminContextPath + "/login")
                .authenticationSuccessHandler(new RedirectServerAuthenticationSuccessHandler(adminContextPath + "/"))
                .and()
                .logout().logoutUrl(adminContextPath + "/logout")
                .and()
                // 3.开启http basic支持，admin-client注册时需要使用
                .httpBasic().and()
                .csrf().disable();
        // 4.开启基于cookie的csrf保护
//                .csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse());
        // 5.忽略这些路径的csrf保护以便admin-client注册
//                .ignoringAntMatchers(adminContextPath + "/instances", adminContextPath + "/actuator/**");
        return http.build();
    }
}
