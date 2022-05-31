package per.qy.simple.auth.controller;

import cn.hutool.core.util.RandomUtil;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import per.qy.simple.auth.constant.RedisKeyConstant;
import per.qy.simple.auth.model.CaptchaVo;
import per.qy.simple.common.core.annotation.BaseResponse;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 验证码获取
 *
 * @author : QY
 * @date : 2022/5/24
 */
@BaseResponse
@RestController
public class CodeController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${bm.login.code.timeout:300}")
    private long codeTimeout;

    @GetMapping("/open/captcha")
    public CaptchaVo captcha() throws IOException, FontFormatException {
        SpecCaptcha captcha = new SpecCaptcha(88, 38, 4);
        captcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
        captcha.setFont(Captcha.FONT_1);
        String key = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(RedisKeyConstant.CAPTCHA_KEY_PRE + key, captcha.text(),
                codeTimeout, TimeUnit.SECONDS);
        return new CaptchaVo(key, captcha.toBase64());
    }

    @GetMapping("/open/phone/code")
    public String phoneCode(@RequestParam String phone) {
        String code = RandomUtil.randomNumbers(4);
        // todo 发送短信验证码
        redisTemplate.opsForValue().set(RedisKeyConstant.PHONE_CODE_KEY_PRE + phone, code,
                codeTimeout, TimeUnit.SECONDS);
        return code;
    }

    @GetMapping("/open/email/code")
    public String emailCode(@RequestParam String email) {
        String code = RandomUtil.randomNumbers(4);
        // todo 发送邮箱验证码
        redisTemplate.opsForValue().set(RedisKeyConstant.EMAIL_CODE_KEY_PRE + email, code,
                codeTimeout, TimeUnit.SECONDS);
        return code;
    }
}
