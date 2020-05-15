package per.qy.simple.cloud.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RsaUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(RsaUtil.class);
    private static final String RSA = "RSA";

    public static KeyPair getRSAKeyPair() {
        return getRSAKeyPair(512);
    }

    public static KeyPair getRSAKeyPair(int keySize) {
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(RSA);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("", e);
        }
        if (keyPairGenerator != null) {
            keyPairGenerator.initialize(keySize);
            return keyPairGenerator.generateKeyPair();
        }
        return null;
    }

    public static String encrypt(String data, String publicKey) {
        try {
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(RSA)
                    .generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey)));
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes()));
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return null;
    }

    public static String decrypt(String data, String privateKey) {
        try {
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(RSA)
                    .generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(data)));
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return null;
    }
}
