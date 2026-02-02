package com.sea.desafio_backend.service;

import com.sea.desafio_backend.dto.EnderecoDTO;
import com.sea.desafio_backend.entity.EnderecoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EnderecoService {

    @Autowired
    private ViaCepService viaCepService;


    public EnderecoEntity criarEndereco(EnderecoDTO enderecoDTO) {
        String cepSemMascara = enderecoDTO.getCep().replaceAll("\\D", "");
        EnderecoDTO viaCepDTO = viaCepService.consultarCep(cepSemMascara);

        EnderecoEntity endereco = new EnderecoEntity();
        endereco.setCep(cepSemMascara);

        // Preenche com ViaCEP, garantindo que nenhum campo obrigatório fique null
        endereco.setLogradouro(viaCepDTO.getLogradouro() != null ? viaCepDTO.getLogradouro() : "");
        endereco.setBairro(viaCepDTO.getBairro() != null ? viaCepDTO.getBairro() : "");
        endereco.setCidade(viaCepDTO.getCidade() != null ? viaCepDTO.getCidade() : "");
        endereco.setUf(viaCepDTO.getUf() != null ? viaCepDTO.getUf() : "");

        // Sobrescreve campos enviados pelo usuário
        if (enderecoDTO.getLogradouro() != null && !enderecoDTO.getLogradouro().trim().isEmpty()) {
            endereco.setLogradouro(enderecoDTO.getLogradouro());
        }
        if (enderecoDTO.getBairro() != null && !enderecoDTO.getBairro().trim().isEmpty()) {
            endereco.setBairro(enderecoDTO.getBairro());
        }
        if (enderecoDTO.getCidade() != null && !enderecoDTO.getCidade().trim().isEmpty()) {
            endereco.setCidade(enderecoDTO.getCidade());
        }
        if (enderecoDTO.getUf() != null && !enderecoDTO.getUf().trim().isEmpty()) {
            endereco.setUf(enderecoDTO.getUf());
        }

        endereco.setComplemento(enderecoDTO.getComplemento()); // opcional
        return endereco;
    }

    public String formatarCep(String cep) {
        if (cep != null && cep.length() == 8) {
            return cep.substring(0, 5) + "-" + cep.substring(5);
        }
        return cep;
    }
}
