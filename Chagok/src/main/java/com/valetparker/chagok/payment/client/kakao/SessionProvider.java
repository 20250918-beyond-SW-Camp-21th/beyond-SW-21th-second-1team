package com.valetparker.chagok.payment.client.kakao;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Objects;

// 카카오페이의 tid를 결제준비에서 응답 받은 후 결제승인으로 tid값을 넘겨주기 위해 Session에 저장할 때 사용
public class SessionProvider {

    public static void addAttribute(String key, Object value) {
        Objects.requireNonNull(RequestContextHolder.getRequestAttributes())
                .setAttribute(key, value, RequestAttributes.SCOPE_SESSION);
    }

    public static Object getAttribute(String key) {
        return Objects.requireNonNull(RequestContextHolder.getRequestAttributes())
                .getAttribute(key, RequestAttributes.SCOPE_SESSION);
    }

    public static String getStringAttribute(String key) {
        return (String) getAttribute(key);
    }
}

