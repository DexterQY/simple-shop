package per.qy.simple.cloud.common.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

/**
 * Function:
 * Description:
 * Author: QY
 * Date: 2019/6/25
 */
public class SecureUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecureUtil.class);
    private static final String ALGORITHM_SHA256RSA = "SHA256withRSA";
    private static final String BC_PROVIDER = "BC";
    private static final String X_509 = "X.509";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static KeyStore getKeyStore(String keyStorePath, String keyStorePwd, String keyStoreType) {
        FileInputStream input = null;
        try {
            KeyStore keyStore = KeyStore.getInstance(keyStoreType, BC_PROVIDER);
            input = new FileInputStream(keyStorePath);
            char[] pwdChar = keyStorePwd.toCharArray();
            keyStore.load(input, pwdChar);
            return keyStore;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
        return null;
    }

    public static String getSignCertId(KeyStore keyStore) {
        try {
            Enumeration<String> aliasenum = keyStore.aliases();
            String keyAlias = null;
            if (aliasenum.hasMoreElements()) {
                keyAlias = aliasenum.nextElement();
            }
            X509Certificate cert = (X509Certificate) keyStore.getCertificate(keyAlias);
            return cert.getSerialNumber().toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public static PrivateKey getPrivateKey(KeyStore keyStore, String keyStorePwd) {
        try {
            Enumeration<String> aliasenum = keyStore.aliases();
            String keyAlias = null;
            if (aliasenum.hasMoreElements()) {
                keyAlias = aliasenum.nextElement();
            }
            PrivateKey privateKey = (PrivateKey) keyStore.getKey(keyAlias, keyStorePwd.toCharArray());
            return privateKey;
        } catch (KeyStoreException | UnrecoverableKeyException | NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public static byte[] signBySha256Rsa(PrivateKey privateKey, byte[] data)
            throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance(ALGORITHM_SHA256RSA, BC_PROVIDER);
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }

    public static boolean verifySignBySha256Rsa(PublicKey publicKey, byte[] signData, byte[] srcData)
            throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance(ALGORITHM_SHA256RSA, BC_PROVIDER);
        signature.initVerify(publicKey);
        signature.update(srcData);
        return signature.verify(signData);
    }

    public static X509Certificate getCertificateByPath(String path) {
        InputStream in = null;
        try {
            CertificateFactory cf = CertificateFactory.getInstance(X_509, BC_PROVIDER);
            in = new FileInputStream(path);
            return (X509Certificate) cf.generateCertificate(in);
        } catch (CertificateException | FileNotFoundException | NoSuchProviderException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
        return null;
    }

    public static X509Certificate getCertificateByStr(String x509CertString) {
        InputStream in = null;
        try {
            CertificateFactory cf = CertificateFactory.getInstance(X_509, BC_PROVIDER);
            in = new ByteArrayInputStream(x509CertString.getBytes(StandardCharsets.ISO_8859_1));
            return (X509Certificate) cf.generateCertificate(in);
        } catch (CertificateException | NoSuchProviderException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
        return null;
    }
}
