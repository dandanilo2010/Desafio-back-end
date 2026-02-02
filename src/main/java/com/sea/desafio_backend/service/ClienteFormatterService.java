package com.sea.desafio_backend.service;

import com.sea.desafio_backend.dto.ClienteDTO;
import org.springframework.stereotype.Service;

@Service
public class ClienteFormatterService {


    public ClienteDTO formatarCliente(ClienteDTO cliente) {
        return cliente;
    }
}
