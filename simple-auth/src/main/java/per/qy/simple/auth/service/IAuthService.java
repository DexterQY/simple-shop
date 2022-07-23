package per.qy.simple.auth.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import per.qy.simple.auth.constant.AuthConstant;
import per.qy.simple.auth.model.AuthUserDetails;
import per.qy.simple.auth.model.dto.AuthDto;
import per.qy.simple.common.base.model.ResponseVo;
import per.qy.simple.user.model.vo.UserVo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 认证服务
 *
 * @author : QY
 * @date : 2022/5/26
 */
public interface IAuthService {

    /**
     * 预处理
     *
     * @param dto 认证参数
     * @return vo
     */
    ResponseVo preHandle(AuthDto dto);

    /**
     * 用户信息认证
     *
     * @return 用户信息
     */
    UserDetails auth();

    /**
     * 支持的认证类型
     *
     * @param authType 认证类型
     * @return 是否支持
     */
    boolean support(String authType);

    /**
     * 是否不需要密码参数
     *
     * @return 是否
     */
    boolean notNeedPassword();

    /**
     * 获取用户信息
     *
     * @param userVo userVo
     * @return UserDetails
     */
    default AuthUserDetails getUserDetails(UserVo userVo) {
        AuthUserDetails userDetails = new AuthUserDetails();
        BeanUtil.copyProperties(userVo, userDetails);
        if (CollUtil.isNotEmpty(userVo.getRoleCodes())) {
            List<SimpleGrantedAuthority> authorities = userVo.getRoleCodes().stream()
                    .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            userDetails.setAuthorities(authorities);
        }
        if (notNeedPassword()) {
            userDetails.setPassword(AuthConstant.DEFAULT_PWD_ENCODE);
        }
        return userDetails;
    }
}
