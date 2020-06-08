package br.com.challenge.core.util;

import java.security.SecureRandom;
import java.util.regex.Pattern;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public final class PasswordUtil {

    private static final Pattern hasUppercase = Pattern.compile("[A-Z]");

    private static final Pattern hasLowercase = Pattern.compile("[a-z]");

    private static final Pattern hasSpecialChar = Pattern.compile("[^a-zA-Z0-9 ]");

    private static final Integer minimumSize = 8;


    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder(12, new SecureRandom()).encode("123456"));
    }

    public static String encode(String plain) {
        return ApplicationContextUtil.getBean(PasswordEncoder.class).encode(plain);
    }

    public static boolean matches(String plain, String crypted) {
        return plain != null && crypted != null
                && ApplicationContextUtil.getBean(PasswordEncoder.class).matches(plain, crypted);
    }

    public static boolean isValid(Object value) {
        if (value instanceof String) {
            String password = (String) value;
            return password.length() >= minimumSize
                    && hasUppercase.matcher(password).find()
                    && hasLowercase.matcher(password).find()
                    && hasSpecialChar.matcher(password).find();
        }
        return false;
    }
}
