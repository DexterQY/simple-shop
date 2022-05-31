package per.qy.simple.auth;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * AuthTest
 *
 * @author : QY
 * @date : 2022/5/25
 */
public class AuthTest {

    @Test
    public void encodePassword() {
        System.out.println(new BCryptPasswordEncoder().encode("abc"));
    }

    @Test
    public void basicAuth() {
        System.out.println(Base64.getEncoder()
                .encodeToString("web:123456".getBytes(StandardCharsets.UTF_8)));
    }
}
