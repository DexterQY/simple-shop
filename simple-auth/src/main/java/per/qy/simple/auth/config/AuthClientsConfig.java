package per.qy.simple.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.util.Map;

/**
 * 客户端信息配置
 *
 * @author : QY
 * @date : 2022/5/24
 */
@RefreshScope
@Configuration
@ConfigurationProperties("simple.auth")
@Data
public class AuthClientsConfig {

    /** 客户端信息 */
    private Map<String, BaseClientDetails> clients;
}
