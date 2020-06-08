package br.com.challenge.core.data.vo.rest;

import java.io.Serializable;

import br.com.challenge.core.data.enumeration.MessageSeverity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageVO implements Serializable {

    private MessageSeverity severity;
    private String code;
    private String message;
    private Object[] params;

    public MessageVO() {
        super();
    }

    public MessageVO(MessageSeverity severity, String code, String message, Object... params) {
        this.severity = severity;
        this.code = code;
        this.message = message;
        this.params = params;
    }


    public static MessageVO instance(MessageSeverity severity, String code, String message, Object... params) {
        return new MessageVO(severity, code, message, params);
    }
}
