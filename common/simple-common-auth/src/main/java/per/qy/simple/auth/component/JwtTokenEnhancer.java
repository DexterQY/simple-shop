package per.qy.simple.auth.component;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import per.qy.simple.auth.model.AuthUserDetails;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JWT信息增强
 *
 * @author : QY
 * @date : 2022/7/27
 */
public class JwtTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        if (accessToken instanceof DefaultOAuth2AccessToken) {
            AuthUserDetails principal = (AuthUserDetails) authentication.getPrincipal();
            principal.setPassword(null);
            if (principal.getAuthorities() == null) {
                principal.setAuthorities(List.of());
            }
            Map<String, Object> map = BeanUtil.beanToMap(principal,
                    new HashMap<>(MapUtil.DEFAULT_INITIAL_CAPACITY), false, true);
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(map);
        }
        return accessToken;
    }
}
