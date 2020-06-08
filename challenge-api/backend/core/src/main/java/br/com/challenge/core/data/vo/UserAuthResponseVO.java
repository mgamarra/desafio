package br.com.challenge.core.data.vo;

import java.io.Serializable;

import br.com.challenge.core.data.enumeration.Group;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.challenge.core.base.jackson.databind.deserializer.HashidDeserializer;
import br.com.challenge.core.base.jackson.databind.serializer.HashidSerializer;
import br.com.challenge.core.data.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAuthResponseVO implements Serializable {
    private String name;
    private String cpf;
    private String email;
    private Group userGroup;
    boolean enabled;

    public UserAuthResponseVO() {
        super();
    }

    public UserAuthResponseVO(String name, String email, boolean enabled, Group userGroup) {
        this.name = name;
        this.email = email;
        this.enabled = enabled;
        this.userGroup = userGroup;
    }

    public UserAuthResponseVO(User user) {

    }
}
