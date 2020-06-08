package br.com.challenge.core.base.exception;

import br.com.challenge.core.data.enumeration.MessageCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private MessageCode code;

    private Object[] params;

    public BusinessException() {
        super();
    }

    public BusinessException(MessageCode code, Object... params) {
        this(null, code, params);
    }

    public BusinessException(Throwable cause, MessageCode code, Object... params) {
        super(code.name(), cause);
        this.code = code;
        this.params = params;
    }

    public static void throwNew(MessageCode code, Object... params) {
        throw new BusinessException(code, params);
    }
}
