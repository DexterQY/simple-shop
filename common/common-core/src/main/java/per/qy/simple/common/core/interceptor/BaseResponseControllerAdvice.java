package per.qy.simple.common.core.interceptor;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import per.qy.simple.common.base.model.ResponseVo;
import per.qy.simple.common.core.annotation.BaseResponse;

/**
 * 统一响应结构处理
 *
 * @author : QY
 * @date : 2022/5/27
 */
@RestControllerAdvice(annotations = BaseResponse.class)
public class BaseResponseControllerAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof ResponseVo) {
            return body;
        }
        return ResponseVo.success(body);
    }
}
