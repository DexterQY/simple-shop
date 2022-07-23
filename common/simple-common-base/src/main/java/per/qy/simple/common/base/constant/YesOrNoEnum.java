package per.qy.simple.common.base.constant;

import lombok.Getter;

/**
 * 是否
 *
 * @author : QY
 * @date : 2022/5/26
 */
@Getter
public enum YesOrNoEnum {

    /** 是 */
    YES(1, "是"),
    /** 否 */
    NO(0, "否");

    /** code */
    private final int code;
    /** text */
    private final String text;

    YesOrNoEnum(int code, String text) {
        this.code = code;
        this.text = text;
    }
}
