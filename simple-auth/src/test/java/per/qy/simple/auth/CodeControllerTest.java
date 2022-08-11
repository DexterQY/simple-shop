package per.qy.simple.auth;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import per.qy.simple.auth.controller.CodeController;
import per.qy.simple.auth.model.vo.CaptchaVo;

import java.awt.FontFormatException;
import java.io.IOException;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * CodeControllerTest
 *
 * @author : QY
 * @date : 2022/8/11
 */
@RunWith(MockitoJUnitRunner.class)
public class CodeControllerTest {

    @InjectMocks
    private CodeController codeController;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void captcha() throws IOException, FontFormatException {
        Mockito.when(redisTemplate.opsForValue()).thenReturn(new ValueOperations<>() {
            @Override
            public void set(String key, Object value) {

            }

            @Override
            public void set(String key, Object value, long timeout, TimeUnit unit) {

            }

            @Override
            public Boolean setIfAbsent(String key, Object value) {
                return null;
            }

            @Override
            public Boolean setIfAbsent(String key, Object value, long timeout, TimeUnit unit) {
                return null;
            }

            @Override
            public Boolean setIfPresent(String key, Object value) {
                return null;
            }

            @Override
            public Boolean setIfPresent(String key, Object value, long timeout, TimeUnit unit) {
                return null;
            }

            @Override
            public void multiSet(Map<? extends String, ?> map) {

            }

            @Override
            public Boolean multiSetIfAbsent(Map<? extends String, ?> map) {
                return null;
            }

            @Override
            public Object get(Object key) {
                return null;
            }

            @Override
            public Object getAndDelete(String key) {
                return null;
            }

            @Override
            public Object getAndExpire(String key, long timeout, TimeUnit unit) {
                return null;
            }

            @Override
            public Object getAndExpire(String key, Duration timeout) {
                return null;
            }

            @Override
            public Object getAndPersist(String key) {
                return null;
            }

            @Override
            public Object getAndSet(String key, Object value) {
                return null;
            }

            @Override
            public List<Object> multiGet(Collection<String> keys) {
                return null;
            }

            @Override
            public Long increment(String key) {
                return null;
            }

            @Override
            public Long increment(String key, long delta) {
                return null;
            }

            @Override
            public Double increment(String key, double delta) {
                return null;
            }

            @Override
            public Long decrement(String key) {
                return null;
            }

            @Override
            public Long decrement(String key, long delta) {
                return null;
            }

            @Override
            public Integer append(String key, String value) {
                return null;
            }

            @Override
            public String get(String key, long start, long end) {
                return null;
            }

            @Override
            public void set(String key, Object value, long offset) {

            }

            @Override
            public Long size(String key) {
                return null;
            }

            @Override
            public Boolean setBit(String key, long offset, boolean value) {
                return null;
            }

            @Override
            public Boolean getBit(String key, long offset) {
                return null;
            }

            @Override
            public List<Long> bitField(String key, BitFieldSubCommands subCommands) {
                return null;
            }

            @Override
            public RedisOperations<String, Object> getOperations() {
                return null;
            }
        });
        CaptchaVo captcha = codeController.captcha();
        Assert.assertNotNull(captcha);
        System.out.println(captcha);
    }
}
