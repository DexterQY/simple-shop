package per.qy.simple.auth.filter;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.http.MediaType;
import per.qy.simple.auth.constant.AuthConstant;
import per.qy.simple.auth.constant.AuthReqThreadLocal;
import per.qy.simple.auth.model.AuthDto;
import per.qy.simple.auth.service.IAuthService;
import per.qy.simple.common.base.constant.SimpleConstant;
import per.qy.simple.common.base.model.ResponseVo;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * oauth端点过滤器
 * 不可注册为spring bean同时添加到oauth2，否则会执行两次
 *
 * @author : QY
 * @date : 2022/5/26
 */
//@Component
public class AuthFilter implements Filter {

    /** 认证端点 */
    private static final String OAUTH_TOKEN_PATH = "/oauth/token";

    private final List<IAuthService> authServices;

    public AuthFilter(List<IAuthService> authServices) {
        this.authServices = authServices;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            String grantType = request.getParameter("grant_type");
            if (request.getServletPath().contains(OAUTH_TOKEN_PATH) && AuthConstant.PWD_NAME.equals(grantType)) {
                Map<String, String> params = request.getParameterMap().entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue()[0]));
                AuthDto authDto = BeanUtil.fillBeanWithMap(params, new AuthDto(), true);

                // 参数校验等预处理
                ResponseVo vo = ResponseVo.fail("不支持的认证方式");
                IAuthService authService = getAuthService(authDto.getAuthType());
                if (authService != null) {
                    vo = authService.preHandle(authDto);
                }
                if (!vo.succeeded()) {
                    // 校验不通过
                    vo.setRequestId(request.getHeader(SimpleConstant.HEADER_REQUEST_ID_KEY));
                    HttpServletResponse response = (HttpServletResponse) servletResponse;
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    try (ServletOutputStream outputStream = response.getOutputStream()) {
                        outputStream.write(JSONUtil.toJsonStr(vo).getBytes(StandardCharsets.UTF_8));
                    }
                    return;
                }
                if (authService != null && authService.notNeedPassword()) {
                    // 无需校验密码参数的认证方式进行默认密码请求包装
                    request = new DefaultPasswordRequestWrapper(request);
                }

                // 预处理通过，请求参数设置到ThreadLocal
                AuthReqThreadLocal.set(authDto);

                try {
                    filterChain.doFilter(request, servletResponse);
                } finally {
                    AuthReqThreadLocal.remove();
                }
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private IAuthService getAuthService(String authType) {
        for (IAuthService authService : authServices) {
            if (authService.support(authType)) {
                return authService;
            }
        }
        return null;
    }
}
