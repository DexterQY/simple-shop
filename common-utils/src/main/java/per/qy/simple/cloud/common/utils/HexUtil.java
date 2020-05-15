package per.qy.simple.cloud.common.utils;

/**
 * Function: 16进制字符串转换
 * Description:
 * Author: QY
 * Date: 2019/6/18
 */
public class HexUtil {

    /**
     * 16进制字符数组
     */
    private final static char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 转换字节数组为16进制字符串
     *
     * @param data
     * @return
     */
    public static String byteToHex(byte[] data) {
        // 一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray = new char[data.length * 2];
        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for (byte b : data) {
            resultCharArray[index++] = HEX_DIGITS[b >>> 4 & 0xf];
            resultCharArray[index++] = HEX_DIGITS[b & 0xf];
        }
        return new String(resultCharArray);
    }
}
