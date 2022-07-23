package per.qy.simple.auth.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 图形验证码
 *
 * @author : QY
 * @date : 2022/5/27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaVo {

    /** key */
    private String key;
    /** 图形base64数据 */
    private String data;
}
