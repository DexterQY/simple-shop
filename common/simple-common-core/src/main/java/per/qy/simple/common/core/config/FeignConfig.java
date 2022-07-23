package per.qy.simple.common.core.config;

import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import per.qy.simple.common.base.constant.SimpleConstant;
import per.qy.simple.common.base.model.ResponseVo;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * feign配置
 *
 * @author : QY
 * @date : 2022/7/4
 */
@ConditionalOnClass(RequestInterceptor.class)
@Configuration
public class FeignConfig {

    @Bean
    public Decoder feignDecoder() {
        // 自定义feign结果解码器，忽略ResponseVo提取实际返回内容
        return (response, type) -> {
            if (response.body() == null) {
                return null;
            }
            String body = IoUtil.read(response.body().asReader(StandardCharsets.UTF_8));
            ResponseVo vo;
            try {
                vo = JSONUtil.toBean(body, ResponseVo.class);
                if (vo.succeeded()) {
                    JSON data = (JSON) vo.getData();
                    return JSONUtil.toBean(data, type, true);
                }
            } catch (Exception e) {
                String message = "feign decode error: body=" + body + " error=" + e.getMessage();
                throw new DecodeException(response.status(), message, response.request());
            }
            throw new DecodeException(response.status(), "feign request error: body=" + body,
                    response.request());
        };
    }

    @Bean
    public RequestInterceptor feignRequestInterceptor() {
        return requestTemplate -> {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (requestAttributes != null) {
                HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
                String requestId = request.getHeader(SimpleConstant.HEADER_REQUEST_ID);
                if (requestId != null) {
                    // 请求头设置requestId
                    requestTemplate.header(SimpleConstant.HEADER_REQUEST_ID, requestId);
                }
            }
        };
    }

    /**
     * Feign调用日志打印，需配置包日志级别为debug
     * <p>
     * NONE【性能最佳，适用于生产】：不记录任何日志（默认值）。
     * BASIC【适用于生产环境追踪问题】：仅记录请求方法、URL、响应状态代码以及执行时间。
     * HEADERS：记录BASIC级别的基础上，记录请求和响应的header。
     * FULL【比较适用于开发及测试环境定位问题】：记录请求和响应的header、body和元数据。
     *
     * @return feignLoggerLevel
     */
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }
}
