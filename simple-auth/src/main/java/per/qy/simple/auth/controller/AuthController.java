package per.qy.simple.auth.controller;

import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import per.qy.simple.auth.model.AuthUserDetails;
import per.qy.simple.common.base.exception.BusinessException;
import per.qy.simple.common.base.exception.ExceptionCode;
import per.qy.simple.common.core.util.RequestUtil;

import java.lang.reflect.Field;

/**
 * 认证
 *
 * @author : QY
 * @date : 2022/7/4
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    /**
     * 获取生成授权码
     *
     * @return code
     */
    @PostMapping("/authorize/code")
    public String authorizeCode() {
        String token = RequestUtil.getBearerToken();
        if (token == null) {
            throw new BusinessException(ExceptionCode.UNAUTHORIZED);
        }

        OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(token);

        if (tokenStore instanceof JwtTokenStore) {
            // jwt模式手动修改principal
            OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
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
        return authorizationCodeServices.createAuthorizationCode(oAuth2Authentication);
    }

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    public void logout() {
        String token = RequestUtil.getBearerToken();
        if (token == null) {
            return;
        }
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
        if (accessToken != null) {
            tokenStore.removeAccessToken(accessToken);
            if (accessToken.getRefreshToken() != null) {
                tokenStore.removeRefreshToken(accessToken.getRefreshToken());
            }
        }
    }
}
