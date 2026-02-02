package com.sea.desafio_backend.service;

import com.sea.desafio_backend.dto.TelefoneDTO;
import com.sea.desafio_backend.entity.ClienteEntity;
import com.sea.desafio_backend.entity.TelefoneEntity;
import com.sea.desafio_backend.repository.TelefoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class TelefoneService {

    @Autowired
    private TelefoneRepository telefoneRepository;


    public List<TelefoneEntity> criarTelefones(List<TelefoneDTO> telefoneDTOs, ClienteEntity cliente) {
        return telefoneDTOs.stream().map(telefoneDTO -> {
            if (telefoneDTO.getTipo() == null || telefoneDTO.getTipo().toString() == null || telefoneDTO.getTipo().toString().trim().isEmpty()) {
                throw new IllegalArgumentException("Tipo de telefone é obrigatório");
            }
            TelefoneEntity telefone = new TelefoneEntity();
            telefone.setNumero(telefoneDTO.getNumero().replaceAll("\\D", ""));
            telefone.setTipo(telefoneDTO.getTipo());
            telefone.setCliente(cliente);
            return telefone;
        }).collect(Collectors.toList());
    }


    public List<TelefoneEntity> buscarPorClienteId(Long clienteId) {
        return telefoneRepository.findByClienteId(clienteId);
    }


    public String formatarNumero(String numero) {
        if (numero != null) {
            if (numero.length() == 11) { // celular
                return "(" + numero.substring(0, 2) + ") " +
                        numero.substring(2, 7) + "-" +
                        numero.substring(7);
            } else if (numero.length() == 10) { // fixo
                return "(" + numero.substring(0, 2) + ") " +
                        numero.substring(2, 6) + "-" +
                        numero.substring(6);
            }
        }
        return numero;
    }
}
