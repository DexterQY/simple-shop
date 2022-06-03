package per.qy.simple.auth.config;

import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import per.qy.simple.common.base.model.ResponseVo;

import java.nio.charset.StandardCharsets;

/**
 * feign配置
 *
 * @author : QY
 * @date : 2022/6/3
 */
@Configuration
public class FeignConfig {

    @Bean
    public Decoder feignDecoder() {
        // 自定义feign结果解码器
        return (response, type) -> {
            if (response.body() == null) {
                return null;
            }
            String body = IoUtil.read(response.body().asReader(StandardCharsets.UTF_8));
            ResponseVo vo;
            try {
                vo = JSONUtil.toBean(body, ResponseVo.class);
                if (vo.succeeded()) {
                    JSONObject data = (JSONObject) vo.getData();
                    return data.toBean(type);
                }
            } catch (Exception e) {
                String message = "feign decode error: body=" + body + " error=" + e.getMessage();
                throw new DecodeException(response.status(), message, response.request());
            }
            throw new DecodeException(response.status(), "feign request error: body=" + body,
                    response.request());
        };
    }
}
