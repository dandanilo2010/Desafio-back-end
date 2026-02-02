package com.sea.desafio_backend.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.sea.desafio_backend.entity.ClienteEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ClienteDTO {

    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nome;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(
            regexp = "^\\d{11}$|^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$",
            message = "CPF deve estar com ou sem máscara"
    )
    private String cpf;

    @Valid
    private EnderecoDTO endereco;

    @Valid
    @NotEmpty(message = "É necessário informar pelo menos um email")
    private List<EmailDTO> emails;

    @Valid
    @NotEmpty(message = "É necessário informar pelo menos um telefone")
    private List<TelefoneDTO> telefones;

    public ClienteDTO(ClienteEntity cliente) {
        this.nome = cliente.getNome();
        this.endereco = new EnderecoDTO(cliente.getEndereco());
        this.cpf = cliente.getCpf();
        this.emails = cliente.getEmails().stream().map(email -> new EmailDTO(email)).collect(Collectors.toList());
        this.telefones = cliente.getTelefones().stream().map(telefones ->new TelefoneDTO(telefones)).collect(Collectors.toList());
        this.id = cliente.getId();
    }
}
