package br.com.challenge.core.util;

import java.util.Collection;
import java.util.Map;

import br.com.challenge.core.data.enumeration.MessageCode;
import br.com.challenge.core.base.exception.BusinessException;

public abstract class Assert {

    /*
     * IsTrue and IsFalse
     */

    public static void isTrue(boolean condition, MessageCode message, Object... messageParams) {
        if (!condition)
            throw new BusinessException(message, messageParams);
    }

    public static void isFalse(boolean condition, MessageCode message, Object... messageParams) {
        if (condition)
            throw new BusinessException(message, messageParams);
    }


    /*
     * IsNull and IsNotNull
     */

    public static void isNull(Object value, MessageCode message, Object... messageParams) {
        if (value != null)
            throw new BusinessException(message, messageParams);
    }

    public static void isNotNull(Object value, MessageCode message, Object... messageParams) {
        if (value == null)
            throw new BusinessException(message, messageParams);
    }


    /*
     * IsEmpty
     */

    public static void isEmpty(String value, MessageCode message, Object... messageParams) {
        if (value != null && value.trim().length() > 0)
            throw new BusinessException(message, messageParams);
    }

    public static void isEmpty(Collection<?> value, MessageCode message, Object... messageParams) {
        if (value != null && !value.isEmpty())
            throw new BusinessException(message, messageParams);
    }

    public static void isEmpty(Map<?, ?> value, MessageCode message, Object... messageParams) {
        if (value != null && !value.isEmpty())
            throw new BusinessException(message, messageParams);
    }

    public static <T> void isEmpty(T[] value, MessageCode message, Object... messageParams) {
        if (value != null && value.length > 0)
            throw new BusinessException(message, messageParams);
    }


    /*
     * IsNotEmpty
     */

    public static void isNotEmpty(String value, MessageCode message, Object... messageParams) {
        if (value == null || value.trim().length() == 0)
            throw new BusinessException(message, messageParams);
    }

    public static void isNotEmpty(Collection<?> value, MessageCode message, Object... messageParams) {
        isNotNull(value, message, messageParams);
        if (value.isEmpty())
            throw new BusinessException(message, messageParams);
    }

    public static void isNotEmpty(Map<?, ?> value, MessageCode message, Object... messageParams) {
        isNotNull(value, message, messageParams);
        if (value.isEmpty())
            throw new BusinessException(message, messageParams);
    }

    public static <T> void isNotEmpty(T[] value, MessageCode message, Object... messageParams) {
        isNotNull(value, message, messageParams);
        if (value.length == 0)
            throw new BusinessException(message, messageParams);
    }
}
