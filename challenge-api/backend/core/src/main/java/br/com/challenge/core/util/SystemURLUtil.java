package br.com.challenge.core.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemURLUtil {

    @Value("${appname.base.address}")
    public String APP_ADDRESS;


    public static final String PARAM_HASH = "hash";

    public static final String VALIDATE_TOKEN = "/validate-token?token={" + PARAM_HASH + "}";
    public static final String LOGIN = "/login";
    public static final String REGISTER = "/register";
    public static final String PASSWORD_RESET = "/password-reset?token={" + PARAM_HASH + "}";


    public String replace(String url, String param, String value) {
        return url.replace(String.format("{%s}", param), value);
    }

    public String appendAppLink(String url) {
        return String.format("%s%s", APP_ADDRESS, url);
    }
}
