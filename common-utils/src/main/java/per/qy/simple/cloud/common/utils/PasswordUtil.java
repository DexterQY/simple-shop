package per.qy.simple.cloud.common.utils;

import java.nio.ByteBuffer;

public class PasswordUtil {

    /**
     * 密码加盐sha3加密
     *
     * @param password
     * @param salt
     * @return
     */
    public static String encrypt(String password, long salt) {
        byte[] passwordBytes = HashUtil.sha3Byte(password.getBytes());
        ByteBuffer buffer = ByteBuffer.allocate(passwordBytes.length + Long.BYTES);
        buffer.put(passwordBytes);
        buffer.putLong(salt);
        return HashUtil.sha3(buffer.array());
    }
}
