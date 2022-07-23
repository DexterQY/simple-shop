package per.qy.simple.common.core.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import per.qy.simple.common.core.interceptor.RequestIdInterceptor;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

/**
 * WebMvcConfig
 *
 * @author : QY
 * @date : 2022/5/27
 */
@ConditionalOnMissingClass("org.springframework.web.reactive.config.WebFluxConfigurer")
@ConditionalOnClass(WebMvcConfigurer.class)
@Configuration
public class DefaultWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 日志链路追踪拦截器
        registry.addInterceptor(new RequestIdInterceptor()).addPathPatterns("/**");
    }

    /**
     * jackson消息转换器
     *
     * @return HttpMessageConverters
     */
    @Bean
    @ConditionalOnMissingBean(HttpMessageConverters.class)
    public HttpMessageConverters jackson2HttpMessageConverters() {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();

        // long类型序列化json时，转成String格式，避免前端读取long类型截断
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);

        // 忽略json中存在，实体对象中不存在的属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 设置全局时间格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        // 设置默认时区，避免夏令时问题，数据库时区也必须一致
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        objectMapper.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        return new HttpMessageConverters(jackson2HttpMessageConverter);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 因返回结果做了统一包装，需去掉String类型的转换器，避免Controller返回String类型优先命中这个转换器报错
        converters.removeIf(converter -> converter instanceof StringHttpMessageConverter);
    }
}
