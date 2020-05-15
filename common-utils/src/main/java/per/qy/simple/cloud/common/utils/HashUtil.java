package per.qy.simple.cloud.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Function: 哈希工具类
 * Description:
 * Author: QY
 * Date: 2019/6/18
 */
public class HashUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HashUtil.class);
    private static final String MD5 = "MD5";
    private static final String SHA = "SHA";
    private static final String SHA256 = "SHA-256";
    private static final String SHA3 = "SHA3-512";

    public static String md5(String str) {
        return md5(getBytes(str));
    }

    public static String md5(byte[] data) {
        return hash(MD5, data);
    }

    public static String sha(String str) {
        return sha(getBytes(str));
    }

    public static String sha(byte[] data) {
        return hash(SHA, data);
    }

    public static String sha256(String str) {
        return sha256(getBytes(str));
    }

    public static String sha256(byte[] data) {
        return hash(SHA256, data);
    }

    public static String sha3(String str) {
        return sha3(getBytes(str));
    }

    public static String sha3(byte[] data) {
        return hash(SHA3, data);
    }

    public static byte[] sha3Byte(byte[] data) {
        return hashByte(SHA3, data);
    }

    private static String hash(String algorithm, byte[] data) {
        byte[] hash = hashByte(algorithm, data);
        if (hash == null) {
            return null;
        }
        return HexUtil.byteToHex(hash);
    }

    private static byte[] hashByte(String algorithm, byte[] data) {
        if (data == null) {
            return null;
        }
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(algorithm);
            return messageDigest.digest(data);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(algorithm, e);
        }
        return null;
    }

    private static byte[] getBytes(String str) {
        return str.getBytes(StandardCharsets.UTF_8);
    }
}
