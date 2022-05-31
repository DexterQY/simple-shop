package per.qy.simple.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import per.qy.simple.auth.constant.AuthReqThreadLocal;
import per.qy.simple.auth.constant.AuthTypeEnum;
import per.qy.simple.auth.constant.RedisKeyConstant;
import per.qy.simple.auth.feign.UserMicroClient;
import per.qy.simple.auth.model.AuthDto;
import per.qy.simple.auth.service.IAuthService;
import per.qy.simple.common.base.exception.BusinessException;
import per.qy.simple.common.base.exception.ExceptionCode;
import per.qy.simple.common.base.model.ResponseVo;
import per.qy.simple.common.base.model.UserDto;

/**
 * 用户名密码认证
 *
 * @author : QY
 * @date : 2022/5/26
 */
@Service
public class AuthByUsernameServiceImpl implements IAuthService {

    @Autowired
    private UserMicroClient userMicroClient;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public ResponseVo preHandle(AuthDto dto) {
        if (StrUtil.hasBlank(dto.getUsername(), dto.getPassword(), dto.getCodeKey(), dto.getCode())) {
            return ResponseVo.fail(ExceptionCode.ILLEGAL_PARAM);
        }
        // 验证码校验
        String key = RedisKeyConstant.CAPTCHA_KEY_PRE + dto.getCodeKey();
        String code = (String) redisTemplate.opsForValue().get(key);
        redisTemplate.delete(key);
        if (code == null) {
            return ResponseVo.fail(ExceptionCode.ILLEGAL_PARAM, "验证码已失效，请刷新重试");
        }
        if (!code.equals(dto.getCode())) {
            return ResponseVo.fail(ExceptionCode.ILLEGAL_PARAM, "验证码错误");
        }
        return ResponseVo.success();
    }

    @Override
    public UserDetails auth() {
        AuthDto authDto = AuthReqThreadLocal.get();
        UserDto userDto = userMicroClient.getByUsername(authDto.getUsername());
        if (userDto == null) {
            throw new BusinessException(ExceptionCode.UNAUTHORIZED, "用户名或密码错误");
        }
        return getUserDetails(userDto);
    }

    @Override
    public boolean support(String authType) {
        return AuthTypeEnum.USERNAME.getCode().equals(authType);
    }

    @Override
    public boolean notNeedPassword() {
        return false;
    }
}
