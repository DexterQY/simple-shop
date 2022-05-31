package per.qy.simple.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * SpringBootApplication
 *
 * @author : QY
 * @date : 2022/5/26
 */
@MapperScan("per.qy.simple.user.mapper")
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "per.qy.simple")
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class);
    }
}
