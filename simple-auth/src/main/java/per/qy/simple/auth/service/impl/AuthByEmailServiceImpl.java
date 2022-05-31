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
 * 邮箱认证
 *
 * @author : QY
 * @date : 2022/5/26
 */
@Service
public class AuthByEmailServiceImpl implements IAuthService {

    @Autowired
    private UserMicroClient userMicroClient;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public ResponseVo preHandle(AuthDto dto) {
        if (StrUtil.hasBlank(dto.getEmail(), dto.getCode())) {
            return ResponseVo.fail(ExceptionCode.ILLEGAL_PARAM);
        }
        // 验证码校验
        String key = RedisKeyConstant.EMAIL_CODE_KEY_PRE + dto.getEmail();
        String code = (String) redisTemplate.opsForValue().get(key);
        redisTemplate.delete(key);
        if (code == null) {
            return ResponseVo.fail(ExceptionCode.ILLEGAL_PARAM, "验证码已失效，请重新发送");
        }
        if (!code.equals(dto.getCode())) {
            return ResponseVo.fail(ExceptionCode.ILLEGAL_PARAM, "验证码错误");
        }
        return ResponseVo.success();
    }

    @Override
    public UserDetails auth() {
        AuthDto authDto = AuthReqThreadLocal.get();
        UserDto userDto = userMicroClient.getByEmail(authDto.getEmail());
        if (userDto == null) {
            throw new BusinessException(ExceptionCode.UNAUTHORIZED, "用户不存在");
        }
        return getUserDetails(userDto);
    }

    @Override
    public boolean support(String authType) {
        return AuthTypeEnum.EMAIL.getCode().equals(authType);
    }

    @Override
    public boolean notNeedPassword() {
        return true;
    }
}
