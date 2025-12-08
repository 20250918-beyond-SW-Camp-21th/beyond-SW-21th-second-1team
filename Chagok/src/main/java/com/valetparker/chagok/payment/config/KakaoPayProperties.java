package com.valetparker.chagok.payment.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "kakaopay")
public class KakaoPayProperties {
    private String secretKey;
    private String cid;

    // 리다이렉트 URL 필드 추가
    private String redirectUrlSuccess;
    private String redirectUrlCancel;
    private String redirectUrlFail;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    // Getter/Setter 추가

    public String getRedirectUrlSuccess() {
        return redirectUrlSuccess;
    }

    public void setRedirectUrlSuccess(String redirectUrlSuccess) {
        this.redirectUrlSuccess = redirectUrlSuccess;
    }

    public String getRedirectUrlCancel() {
        return redirectUrlCancel;
    }

    public void setRedirectUrlCancel(String redirectUrlCancel) {
        this.redirectUrlCancel = redirectUrlCancel;
    }

    public String getRedirectUrlFail() {
        return redirectUrlFail;
    }

    public void setRedirectUrlFail(String redirectUrlFail) {
        this.redirectUrlFail = redirectUrlFail;
    }
}