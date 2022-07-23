package per.qy.simple.common.core.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import per.qy.simple.common.base.constant.SimpleConstant;
import per.qy.simple.common.base.exception.BusinessException;
import per.qy.simple.common.base.exception.ExceptionCode;
import per.qy.simple.common.base.model.CurrentUser;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

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

    public static String getBearerToken() {
        return getBearerToken(getRequest());
    }

    public static String getBearerToken(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String token = request.getHeader("authorization");
        if (token == null || token.length() < 8) {
            return null;
        }
        return token.substring(7);
    }

    public static CurrentUser getCurrentUser() {
        return getCurrentUser(getRequest(), false);
    }

    public static CurrentUser getCurrentUser(HttpServletRequest request) {
        return getCurrentUser(request, false);
    }

    public static CurrentUser getCurrentUser(boolean allowReturnNull) {
        return getCurrentUser(getRequest(), allowReturnNull);
    }

    public static CurrentUser getCurrentUser(HttpServletRequest request, boolean allowReturnNull) {
        if (request != null) {
            String userInfo = request.getHeader(SimpleConstant.HEADER_CURRENT_USER);
            if (StrUtil.isNotEmpty(userInfo)) {
                try {
                    userInfo = URLDecoder.decode(userInfo, StandardCharsets.UTF_8);
                    CurrentUser currentUser = JSONUtil.toBean(userInfo, CurrentUser.class);
                    if (currentUser.getId() != null) {
                        return currentUser;
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
