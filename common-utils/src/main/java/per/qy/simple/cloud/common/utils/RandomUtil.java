package per.qy.simple.cloud.common.utils;

import java.util.Random;

public class RandomUtil {

    /**
     * 随机字符数组，排除了[I,l,o,O]四个易与数字混淆字母
     */
    private static final char[] CHAR_ARRAY = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public static String randomNumber(int length) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(CHAR_ARRAY[random.nextInt(10)]);
        }
        return code.toString();
    }

    public static String random(int length) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(CHAR_ARRAY[random.nextInt(CHAR_ARRAY.length)]);
        }
        return code.toString();
    }

    /**
     * Function: 生成初始密码，包含小写字母和数字
     * Description:
     * Author: QY
     * Date: 2019/5/30
     */
    public static String randomPassword(int length) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        int num = 0;
        for (int i = 0; i < length; i++) {
            if (i > length - 3 && num < 2) {
                code.append(CHAR_ARRAY[random.nextInt(10)]);
                num++;
            } else if (i > length - 3 && num > length - 3) {
                code.append(CHAR_ARRAY[10 + random.nextInt(CHAR_ARRAY.length - 10)]);
            } else {
                int n = random.nextInt(CHAR_ARRAY.length);
                if (n < 10) {
                    num++;
                }
                code.append(CHAR_ARRAY[n]);
            }
        }
        return code.toString();
    }
}
