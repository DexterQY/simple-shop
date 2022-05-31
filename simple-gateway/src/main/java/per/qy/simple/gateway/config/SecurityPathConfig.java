package per.qy.simple.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 路径安全配置
 *
 * @author : QY
 * @date : 2022/5/28
 */
@Configuration
@ConfigurationProperties("simple.security")
@Data
public class SecurityPathConfig {

    /** 拒绝访问路径 */
    private List<String> denyPaths;
    /** 忽略认证路径 */
    private List<String> ignoreAuthPaths;
}
