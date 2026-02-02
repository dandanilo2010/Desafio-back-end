package com.sea.desafio_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sea.desafio_backend.enums.TipoTelefone;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "telefone")
@Getter
@Setter
public class TelefoneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 11)
    private String numero;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTelefone tipo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id")
    @JsonIgnore
    private ClienteEntity cliente;
}
