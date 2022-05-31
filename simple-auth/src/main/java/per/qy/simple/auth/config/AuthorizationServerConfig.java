package per.qy.simple.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import per.qy.simple.auth.filter.AuthFilter;
import per.qy.simple.auth.service.IAuthService;
import per.qy.simple.auth.service.impl.ConfigClientDetailsServiceImpl;

import java.util.List;

/**
 * 授权服务器配置
 *
 * @author : QY
 * @date : 2022/5/24
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private ConfigClientDetailsServiceImpl clientDetailsService;
    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private List<IAuthService> authServices;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.passwordEncoder(new BCryptPasswordEncoder())
                // 允许/oauth/token端点client_id和client_secret放url参数
                .allowFormAuthenticationForClients()
                .addTokenEndpointAuthenticationFilter(new AuthFilter(authServices));
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(tokenStore)
                // 授权码服务
                .authorizationCodeServices(authorizationCodeServices)
                // 密码模式需要配置
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }
}
