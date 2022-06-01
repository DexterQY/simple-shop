package per.qy.simple.common.core.interceptor;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import per.qy.simple.common.base.constant.SimpleConstant;
import per.qy.simple.common.base.model.ResponseVo;
import per.qy.simple.common.core.annotation.BaseResponse;

import java.util.List;

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
        ResponseVo vo;
        if (body instanceof ResponseVo) {
            vo = (ResponseVo) body;
        } else {
            vo = ResponseVo.success(body);
        }
        List<String> requestIds = request.getHeaders().get(SimpleConstant.HEADER_REQUEST_ID_KEY);
        if (requestIds != null && !requestIds.isEmpty()) {
            vo.setRequestId(requestIds.get(0));
        }
        return vo;
    }
}
