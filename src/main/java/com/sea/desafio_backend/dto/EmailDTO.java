package com.sea.desafio_backend.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.sea.desafio_backend.entity.EmailEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmailDTO {

    private Long id;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    private String email;

    public EmailDTO(EmailEntity email) {
        this.email = email.getEmail();
        this.id = email.getId();
    }
}
