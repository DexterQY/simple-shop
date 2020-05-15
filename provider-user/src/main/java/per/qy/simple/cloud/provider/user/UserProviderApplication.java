package per.qy.simple.cloud.provider.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(scanBasePackages = "per.qy.simple.cloud")
@ServletComponentScan
@MapperScan(basePackages = {"per.qy.simple.cloud.**.dao"})
public class UserProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserProviderApplication.class, args);
    }
}
