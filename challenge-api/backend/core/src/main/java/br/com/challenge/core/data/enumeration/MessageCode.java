package br.com.challenge.core.data.enumeration;

import static br.com.challenge.core.data.enumeration.MessageSeverity.*;

import lombok.Getter;

@Getter
public enum MessageCode {
    //200
    I_THE_X_Y_HAS_BEEN_Z(200, SUCCESS),
    I_RECORD_HAS_BEEN_X(200, SUCCESS),

    I_USER_HAS_BEEN_CREATED(200, SUCCESS),


    /////////////////////////////////////////////////////////////////
    //400
    E_INVALID_CREDENTIALS(400, ERROR),
    E_USER_NOT_UNIQUE(400, ERROR),
    E_WRONG_PASSWORD_CONFIRM(400, ERROR),
    E_USER_NOT_REGISTERED(400, ERROR),
    E_USER_ALREADY_REGISTERED(400, ERROR),
    E_SESSION_EXPIRED(400, ERROR),
    E_INVALID_ACCOUNT(400, ERROR),
    E_ERROR_PROCESSING_REQUEST(400, ERROR),
    E_MISSING_PARAMETERS(400, ERROR),
    E_UPDATING_OLD_DATA(400, ERROR),
    E_FORBIDDEN_PERMISSION(400, ERROR),
    E_PATTERN_INVALID_PARAMETER_VALUE(400, ERROR),
    E_RECORD_NOT_FOUND(400, ERROR),
    E_INVALID_CONFIRMATION_TOKEN(400, ERROR),
    E_FORGOT_PASSWORD_USERNAME_EMAIL_NOT_FOUND(400, ERROR),
    E_UNAUTHORIZED(401, ERROR),


    W_CREDENTIALS_HAS_BEEN_CHANGED(400, WARNING),

    ;




    private Integer code;
    private MessageSeverity severity;

    MessageCode(int code, MessageSeverity severity) {
        this.code = code;
        this.severity = severity;
    }
}
