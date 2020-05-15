package per.qy.simple.cloud.common.utils;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Function: 校验工具类
 * Description:
 * Author: QY
 * Date: 2019/6/14
 */
public class ValidUtil {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[3456789]\\d{9}$");

    public static boolean isEmpty(String value) {
        return value == null || CommonParam.EMPTY_STR.equals(value);
    }

    public boolean isNotEmpty(String value) {
        return value != null && !CommonParam.EMPTY_STR.equals(value);
    }

    public static boolean isBlank(String value) {
        return value == null || CommonParam.EMPTY_STR.equals(value.replace(CommonParam.SPACE_STR, CommonParam.EMPTY_STR));
    }

    public static boolean isNotBlank(String value) {
        return value != null && !CommonParam.EMPTY_STR.equals(value.replace(CommonParam.SPACE_STR, CommonParam.EMPTY_STR));
    }

    public static boolean isEmpty(Object[] values) {
        return values == null || values.length <= CommonParam.NUM_0;
    }

    public static boolean isNotEmpty(Object[] values) {
        return values != null && values.length > CommonParam.NUM_0;
    }

    public static boolean isEmpty(Collection values) {
        return values == null || values.isEmpty();
    }

    public static boolean isNotEmpty(Collection values) {
        return values != null && !values.isEmpty();
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Map map) {
        return map != null && !map.isEmpty();
    }

    public static boolean isPhone(String phone) {
        if (isEmpty(phone)) {
            return false;
        }
        Matcher matcher = PHONE_PATTERN.matcher(phone);
        return matcher.matches();
    }
}
