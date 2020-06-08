package br.com.challenge.core.data.enumeration;

import lombok.Getter;

@Getter
public enum NotificationType {
    USER_NEW_ACCOUNT,
    ACCOUNT_VALIDATED,

    USER_REMOVED_ACCOUNT,
    USER_PASSWORD_CHANGED,
    USER_REQUEST_PASSWORD_RESET,
    USER_FORGOT_PASSWORD,
    USER_EMAIL_CHANGE,
    ;

    private String template;
    private String subject;
    private String title;

    NotificationType() {
        this.template = this.name();

        String prefix = this.name().toLowerCase().replace('_', '.');
        this.subject = String.format("%s.subject", prefix);
        this.title = String.format("%s.title", prefix);
    }
}
