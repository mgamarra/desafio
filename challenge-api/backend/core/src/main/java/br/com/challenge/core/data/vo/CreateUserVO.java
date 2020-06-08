package br.com.challenge.core.data.vo;

import br.com.challenge.core.data.entity.Address;
import br.com.challenge.core.util.Json;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

@Data
public class CreateUserVO {

    @NotBlank
    private String name;

    @NotBlank
    @Length(max = 11)
    @CPF
    private String cpf;

    private List<String> emails;

    private List<Map<String,String>> phones;

    @Length(max = 100)
    private String password;

    private String city;
    private String zipCode;
    private String neighborhood;
    private String uf;
    private String addressDescription;
}
