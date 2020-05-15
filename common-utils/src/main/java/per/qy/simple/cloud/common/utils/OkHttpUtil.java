package per.qy.simple.cloud.common.utils;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

public class OkHttpUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(OkHttpUtil.class);
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType MEDIA_TYPE_XML = MediaType.parse("text/xml; charset=utf-8");
    private static final int SUCCESS_CODE = 200;
    private static final OkHttpClient CLIENT;

    static {
        X509TrustManager x509tm = new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }
        };
        CLIENT = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).sslSocketFactory(createSslSocketFactory(x509tm), x509tm)
                .hostnameVerifier((hostname, session) -> true).build();
    }

    private static SSLSocketFactory createSslSocketFactory(TrustManager trustManager) {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{trustManager}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            LOGGER.error("", e);
        }
        return ssfFactory;
    }

    public static String httpGet(String url) {
        Request request = new Request.Builder().url(url).get().build();
        return execute(request);
    }

    public static String httpPost(String url, RequestBody body) {
        Request request = new Request.Builder().url(url).post(body).build();
        return execute(request);
    }

    public static String httpPostJson(String url, String json) {
        RequestBody body = RequestBody.create(json, MEDIA_TYPE_JSON);
        Request request = new Request.Builder().url(url).post(body).build();
        return execute(request);
    }

    public static String httpPostXml(String url, String xml) {
        RequestBody body = RequestBody.create(xml, MEDIA_TYPE_XML);
        Request request = new Request.Builder().url(url).post(body).build();
        return execute(request);
    }

    public static String execute(Request request) {
        try (Response response = CLIENT.newCall(request).execute()) {
            if (response.code() == SUCCESS_CODE) {
                ResponseBody body = response.body();
                if (body != null) {
                    return body.string();
                }
            }
        } catch (IOException e) {
            LOGGER.error(request.toString(), e);
        }
        return null;
    }
}
