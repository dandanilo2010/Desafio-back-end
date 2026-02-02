package com.sea.desafio_backend.dto;

import com.sea.desafio_backend.entity.TelefoneEntity;
import com.sea.desafio_backend.enums.TipoTelefone;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TelefoneDTO {

    private Long id;
    @NotBlank(message = "Número do telefone é obrigatório")
    @Pattern(
            regexp = "^\\(?\\d{2}\\)?\\s?9?\\d{4}-?\\d{4}$",
            message = "Telefone deve estar no formato válido (com DDD)"
    )
    private String numero;

    @NotNull(message = "Tipo do telefone é obrigatório")
    private TipoTelefone tipo;

    public TelefoneDTO(TelefoneEntity telefone) {
        this.numero = telefone.getNumero();
        this.tipo = telefone.getTipo();
        this.id = telefone.getId();
    }
}
