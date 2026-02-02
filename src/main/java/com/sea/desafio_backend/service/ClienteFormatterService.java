package com.sea.desafio_backend.service;

import com.sea.desafio_backend.dto.ClienteDTO;
import com.sea.desafio_backend.dto.EnderecoDTO;
import com.sea.desafio_backend.entity.ClienteEntity;
import com.sea.desafio_backend.entity.EnderecoEntity;
import com.sea.desafio_backend.entity.TelefoneEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClienteFormatterService {

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private TelefoneService telefoneService;


    public ClienteDTO formatarCliente(ClienteDTO cliente) {
        formatarCpf(cliente);
        formatarEndereco(cliente);
        formatarTelefones(cliente);
        return cliente;
    }


    private void formatarCpf(ClienteDTO cliente) {
        String cpf = cliente.getCpf();
        if (cpf != null && cpf.length() == 11) {
            cliente.setCpf(cpf.substring(0, 3) + "." +
                    cpf.substring(3, 6) + "." +
                    cpf.substring(6, 9) + "-" +
                    cpf.substring(9));
        }
    }


    private void formatarEndereco(ClienteDTO cliente) {
        EnderecoDTO endereco = cliente.getEndereco();
        if (endereco != null && endereco.getCep() != null && endereco.getCep().length() == 8) {
            endereco.setCep(enderecoService.formatarCep(endereco.getCep()));
        }
    }


    private void formatarTelefones(ClienteDTO cliente) {
        cliente.getTelefones().forEach(telefone -> {
            String numero = telefone.getNumero();
            telefone.setNumero(telefoneService.formatarNumero(numero));
        });
    }
}
