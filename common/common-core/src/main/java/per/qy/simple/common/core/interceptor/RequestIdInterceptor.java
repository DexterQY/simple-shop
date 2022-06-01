package per.qy.simple.common.core.interceptor;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import per.qy.simple.common.base.constant.SimpleConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 日志链路追踪拦截器
 *
 * @author : QY
 * @date : 2022/5/27
 */
public class RequestIdInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestId = request.getHeader(SimpleConstant.HEADER_REQUEST_ID_KEY);
        if (StrUtil.isBlank(requestId)) {
            requestId = IdUtil.fastSimpleUUID();
        }
        MDC.put(SimpleConstant.HEADER_REQUEST_ID_KEY, requestId);
        return true;
    }
}
