package br.com.challenge.core.data.vo;

import lombok.Data;

@Data
public class AddressVO {

    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String unidade;
    private String ibge;
    private String gia;
}
