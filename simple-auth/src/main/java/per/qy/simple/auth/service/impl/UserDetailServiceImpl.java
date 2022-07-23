package per.qy.simple.auth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import per.qy.simple.auth.constant.AuthReqThreadLocal;
import per.qy.simple.auth.model.AuthUserDetails;
import per.qy.simple.auth.model.dto.AuthDto;
import per.qy.simple.auth.service.IAuthService;
import per.qy.simple.user.client.UserMicroClient;
import per.qy.simple.user.model.vo.UserVo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户信息服务
 *
 * @author : QY
 * @date : 2022/5/24
 */
@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private List<IAuthService> authServices;
    @Autowired
    private UserMicroClient userMicroClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthDto req = AuthReqThreadLocal.get();
        if (req != null) {
            // 不为空说明是password模式认证
            return auth(req);
        }
        // 否则为refresh_token模式认证
        UserVo userVo = userMicroClient.getByUsername(username);
        AuthUserDetails userDetails = new AuthUserDetails();
        BeanUtil.copyProperties(userVo, userDetails);
        if (CollUtil.isNotEmpty(userVo.getRoleCodes())) {
            List<SimpleGrantedAuthority> authorities = userVo.getRoleCodes().stream()
                    .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            userDetails.setAuthorities(authorities);
        }
        return userDetails;
    }

    private UserDetails auth(AuthDto req) {
        for (IAuthService authService : authServices) {
            if (authService.support(req.getAuthType())) {
                return authService.auth();
            }
        }
        return null;
    }
}
