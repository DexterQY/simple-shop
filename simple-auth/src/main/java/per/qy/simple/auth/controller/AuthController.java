package per.qy.simple.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import per.qy.simple.common.base.exception.BusinessException;
import per.qy.simple.common.base.exception.ExceptionCode;
import per.qy.simple.common.core.util.RequestUtil;

/**
 * 认证
 *
 * @author : QY
 * @date : 2022/7/4
 */
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

        OAuth2Authentication authentication = tokenStore.readAuthentication(token);
        return authorizationCodeServices.createAuthorizationCode(authentication);
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
