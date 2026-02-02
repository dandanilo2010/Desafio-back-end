package com.sea.desafio_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.sea.desafio_backend.entity.EnderecoEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EnderecoDTO {

    private Long id;

    @NotBlank(message = "CEP é obrigatório")
    @Pattern(
            regexp = "^\\d{5}-?\\d{3}$",
            message = "CEP deve estar no formato 00000-000 ou 00000000"
    )
    private String cep;


    private String logradouro;

    private String bairro;

    @JsonProperty("localidade")
    private String cidade;


    private String uf;

    private String complemento;

    public EnderecoDTO(EnderecoEntity endereco) {
        this.cep = endereco.getCep();
        this.logradouro = endereco.getLogradouro();
        this.bairro = endereco.getBairro();
        this.cidade = endereco.getCidade();
        this.uf = endereco.getUf();
        this.complemento = endereco.getComplemento();
        this.id = endereco.getId();
    }
}