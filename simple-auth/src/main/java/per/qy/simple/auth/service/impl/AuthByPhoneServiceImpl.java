package per.qy.simple.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import per.qy.simple.auth.constant.AuthReqThreadLocal;
import per.qy.simple.auth.constant.AuthTypeEnum;
import per.qy.simple.auth.constant.RedisKeyConstant;
import per.qy.simple.auth.model.dto.AuthDto;
import per.qy.simple.auth.service.IAuthService;
import per.qy.simple.common.base.exception.BusinessException;
import per.qy.simple.common.base.exception.ExceptionCode;
import per.qy.simple.common.base.model.ResponseVo;
import per.qy.simple.user.client.UserMicroClient;
import per.qy.simple.user.model.vo.UserVo;

/**
 * 手机短信认证
 *
 * @author : QY
 * @date : 2022/5/26
 */
@Service
public class AuthByPhoneServiceImpl implements IAuthService {

    @Autowired
    private UserMicroClient userMicroClient;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public ResponseVo preHandle(AuthDto dto) {
        if (StrUtil.hasBlank(dto.getPhone(), dto.getCode())) {
            return ResponseVo.fail(ExceptionCode.ILLEGAL_PARAM);
        }
        // 验证码校验
        String key = RedisKeyConstant.PHONE_CODE_KEY_PRE + dto.getPhone();
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
        UserVo userVo = userMicroClient.getByPhone(authDto.getPhone());
        if (userVo == null) {
            throw new BusinessException(ExceptionCode.UNAUTHORIZED, "用户不存在");
        }
        return getUserDetails(userVo);
    }

    @Override
    public boolean support(String authType) {
        return AuthTypeEnum.PHONE.getCode().equals(authType);
    }

    @Override
    public boolean notNeedPassword() {
        return true;
    }
}
