package per.qy.simple.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import per.qy.simple.auth.constant.AuthReqThreadLocal;
import per.qy.simple.auth.constant.AuthTypeEnum;
import per.qy.simple.auth.feign.UserMicroClient;
import per.qy.simple.auth.model.AuthDto;
import per.qy.simple.auth.service.IAuthService;
import per.qy.simple.common.base.exception.BusinessException;
import per.qy.simple.common.base.exception.ExceptionCode;
import per.qy.simple.common.base.model.ResponseVo;
import per.qy.simple.common.base.model.UserDto;

/**
 * 微信认证
 *
 * @author : QY
 * @date : 2022/5/26
 */
@Service
public class AuthByWxServiceImpl implements IAuthService {

    @Autowired
    private UserMicroClient userMicroClient;

    @Override
    public ResponseVo preHandle(AuthDto dto) {
        if (StrUtil.isBlank(dto.getCode())) {
            return ResponseVo.fail(ExceptionCode.ILLEGAL_PARAM);
        }
        return ResponseVo.success();
    }

    @Override
    public UserDetails auth() {
        AuthDto authDto = AuthReqThreadLocal.get();
        // todo 微信code换token，获取用户openId
        String openId = authDto.getCode();
        UserDto userDto = userMicroClient.getByWxOpenId(openId);
        if (userDto == null) {
            throw new BusinessException(ExceptionCode.UNAUTHORIZED, "用户不存在");
        }
        return getUserDetails(userDto);
    }

    @Override
    public boolean support(String authType) {
        return AuthTypeEnum.WX.getCode().equals(authType);
    }

    @Override
    public boolean notNeedPassword() {
        return true;
    }
}
