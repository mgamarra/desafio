package br.com.challenge.core.data.vo.rest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.challenge.core.data.enumeration.MessageCode;
import br.com.challenge.core.util.ApplicationContextUtil;
import br.com.challenge.core.util.I18N;
import lombok.Getter;

/**
 * Simples VO para envelopar as respostas do sistema sempre em um formato padrão.
 */
@Getter
public class EnvelopeVO implements Serializable {

    private List<MessageVO> messages;
    private Object data;

    private EnvelopeVO() {
        messages = new ArrayList<>();
    }

    private String translate(MessageCode messageCode, Object... params) {
        return ApplicationContextUtil.getBean(I18N.class).getString(messageCode, params);
    }

    public static EnvelopeVO instance() {
        return new EnvelopeVO();
    }

    public EnvelopeVO message(MessageCode messageCode, Object... params) {
        if (messageCode != null)
            this.getMessages().add(MessageVO.instance(messageCode.getSeverity(), messageCode.name(), translate(messageCode, params), params));

        return this;
    }

    public EnvelopeVO data(Object object) {
        if (this.data != null)
            throw new RuntimeException("Data object already informed: " + this.data);

        this.data = object;
        return this;
    }
}

