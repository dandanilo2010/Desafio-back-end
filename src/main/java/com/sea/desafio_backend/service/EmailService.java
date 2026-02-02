package com.sea.desafio_backend.service;

import

        com.sea.desafio_backend.dto.EmailDTO;
import com.sea.desafio_backend.entity.ClienteEntity;
import com.sea.desafio_backend.entity.EmailEntity;
import com.sea.desafio_backend.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;


    public List<EmailEntity> criarEmails(List<EmailDTO> emailDTOs, ClienteEntity cliente) {
        return emailDTOs.stream().map(emailDTO -> {
            EmailEntity email = new EmailEntity();
            email.setEmail(emailDTO.getEmail());
            email.setCliente(cliente);
            return email;
        }).collect(Collectors.toList());
    }


    public List<EmailEntity> buscarPorClienteId(Long clienteId) {
        return emailRepository.findByClienteId(clienteId);
    }


    public boolean existeEmail(String email) {
        return emailRepository.existsByEmail(email);
    }


    public EmailEntity buscarPorEmail(String email) {
        return emailRepository.findByEmail(email).orElse(null);
    }
}
