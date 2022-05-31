package per.qy.simple.auth.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import per.qy.simple.common.base.constant.YesOrNoEnum;

import java.util.Collection;

/**
 * UserDetails
 *
 * @author : QY
 * @date : 2022/5/27
 */
@Data
public class AuthUserDetails implements UserDetails {

    /** id */
    private Long id;
    /** 名称 */
    private String name;
    /** 用户名 */
    private String username;
    /** 密码 */
    private String password;
    /** 手机 */
    private String phone;
    /** 邮箱 */
    private String email;
    /** 状态 */
    private Integer status;
    /** 角色 */
    private String roleCodes;
    /** 角色 */
    private Collection<SimpleGrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status != null && status == YesOrNoEnum.YES.getCode();
    }
}
