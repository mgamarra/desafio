package br.com.challenge.core.data.vo;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CredentialsVO {
    @NotBlank
    private String name;

    @NotBlank
    private String password;

    public CredentialsVO() {

    }

    public CredentialsVO(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
