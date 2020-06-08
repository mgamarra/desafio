package br.com.challenge.core.data.vo;

import java.io.Serializable;
import java.util.TimeZone;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.challenge.core.base.jackson.databind.deserializer.HashidDeserializer;
import br.com.challenge.core.base.jackson.databind.serializer.HashidSerializer;
import br.com.challenge.core.data.entity.User;
import br.com.challenge.core.data.enumeration.UserLocale;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVO implements Serializable {
    @NotBlank
    private String name;

    @NotBlank
    private String cpf;

    @NotBlank
    @Email
    private String email;

    @Length(max = 100)
    private String password;

    @Length(max = 100)
    private String newPassword;

    @JsonDeserialize(using = HashidDeserializer.class)
    @JsonSerialize(using = HashidSerializer.class)
    private Long version;

    boolean enabled;

    boolean firstAccess;

    private UserLocale defaultLocale;

    private TimeZone timeZone;

    public UserVO() {
        super();
    }

    public UserVO(String name, String email, boolean enabled) {
        this.email = email;
        this.enabled = enabled;
    }

    public UserVO(User user) {
        this.name = user.getName();
        this.enabled = user.isEnabled();
    }
}
