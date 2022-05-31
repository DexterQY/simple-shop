package per.qy.simple.common.core.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import per.qy.simple.common.base.constant.SimpleConstant;
import per.qy.simple.common.base.exception.BusinessException;
import per.qy.simple.common.base.exception.ExceptionCode;
import per.qy.simple.common.base.model.UserDto;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * RequestUtil
 *
 * @author : QY
 * @date : 2022/5/29
 */
public class RequestUtil {

    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        }
        return null;
    }

    public static UserDto getCurrentUser() {
        return getCurrentUser(getRequest(), false);
    }

    public static UserDto getCurrentUser(HttpServletRequest request) {
        return getCurrentUser(request, false);
    }

    public static UserDto getCurrentUser(boolean allowReturnNull) {
        return getCurrentUser(getRequest(), allowReturnNull);
    }

    public static UserDto getCurrentUser(HttpServletRequest request, boolean allowReturnNull) {
        if (request != null) {
            String userInfo = request.getHeader(SimpleConstant.HEADER_USER_INFO_KEY);
            if (StrUtil.isNotEmpty(userInfo)) {
                try {
                    userInfo = new String(Base64.getDecoder().decode(userInfo), StandardCharsets.UTF_8);
                    UserDto userDto = JSONUtil.toBean(userInfo, UserDto.class);
                    if (userDto.getId() != null) {
                        return userDto;
                    }
                } catch (Exception ignored) {
                }
            }
        }
        if (allowReturnNull) {
            return null;
        }
        throw new BusinessException(ExceptionCode.UNAUTHORIZED);
    }
}
