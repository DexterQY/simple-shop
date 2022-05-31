package per.qy.simple.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * SpringBootApplication
 *
 * @author : QY
 * @date : 2022/5/23
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "per.qy.simple")
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class);
    }
}
