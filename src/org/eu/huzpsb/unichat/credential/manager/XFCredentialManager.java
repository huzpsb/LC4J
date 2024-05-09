package org.eu.huzpsb.unichat.credential.manager;

import org.eu.huzpsb.unichat.credential.Credential;
import org.eu.huzpsb.unichat.credential.CredentialType;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Locale;

public class XFCredentialManager implements CredentialManager {
    private final String appId;
    private final String appKey;
    private final String secretKey;

    public XFCredentialManager(String appId, String appKey, String secretKey) {
        this.appId = appId;
        this.appKey = appKey;
        this.secretKey = secretKey;
    }

    @Override
    public Credential getCredential() {
        try {
            ZonedDateTime now = ZonedDateTime.now(ZoneId.of("GMT"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
            String date = now.format(formatter);
            String tmp = "host: spark-api.xf-yun.com\ndate: " + date + "\nGET /v3.1/chat HTTP/1.1";
            byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
            byte[] textBytes = tmp.getBytes(StandardCharsets.UTF_8);
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(keyBytes, "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] tmp_sha = sha256_HMAC.doFinal(textBytes);
            String signature = Base64.getEncoder().encodeToString(tmp_sha);
            String authorization_origin = String.format("api_key=\"%s\", algorithm=\"hmac-sha256\", headers=\"host date request-line\", signature=\"%s\"", appKey, signature);
            String authorization = Base64.getEncoder().encodeToString(authorization_origin.getBytes(StandardCharsets.UTF_8));
            String url = "wss://spark-api.xf-yun.com/v3.1/chat?" +
                    "authorization=" + URLEncoder.encode(authorization, "UTF-8") +
                    "&date=" + URLEncoder.encode(date, "UTF-8") +
                    "&host=" + URLEncoder.encode("spark-api.xf-yun.com", "UTF-8");
            return new Credential(CredentialType.EP_AID, url, appId);
        } catch (Exception ex) {
            return new Credential(CredentialType.NONE, null);
        }
    }
}
