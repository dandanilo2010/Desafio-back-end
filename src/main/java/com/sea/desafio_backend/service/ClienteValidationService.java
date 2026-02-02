package com.sea.desafio_backend.service;

import com.sea.desafio_backend.dto.ClienteDTO;
import com.sea.desafio_backend.dto.EmailDTO;
import com.sea.desafio_backend.repository.ClienteRepository;
import com.sea.desafio_backend.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClienteValidationService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmailRepository emailRepository;

    public void validarClienteDTO(ClienteDTO clienteDTO) {
        validarNome(clienteDTO);
        validarCpf(clienteDTO);
        validarEndereco(clienteDTO);
        validarEmails(clienteDTO);
        validarTelefones(clienteDTO);
    }

    public void validarClienteDTOParaAtualizacao(ClienteDTO clienteDTO, Long id) {
        validarClienteDTO(clienteDTO);
        
        // Verifica CPF duplicado para outro cliente
        String cpfSemMascara = clienteDTO.getCpf().replaceAll("\\D", "");
        clienteRepository.findByCpf(cpfSemMascara).ifPresent(outro -> {
            if (!outro.getId().equals(id)) {
                throw new IllegalArgumentException("CPF já cadastrado para outro cliente");
            }
        });
        
        // Verifica emails duplicados para outro cliente
        for (EmailDTO emailDTO : clienteDTO.getEmails()) {
            emailRepository.findByEmail(emailDTO.getEmail()).ifPresent(outroEmail -> {
                if (!outroEmail.getCliente().getId().equals(id)) {
                    throw new IllegalArgumentException("Email já cadastrado para outro cliente");
                }
            });
        }
    }


    public void validarCpfDuplicado(String cpf) {
        String cpfSemMascara = cpf.replaceAll("\\D", "");
        if (clienteRepository.existsByCpf(cpfSemMascara)) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }
    }


    public void validarEmailDuplicado(String email) {
        if (emailRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
    }

    private void validarNome(ClienteDTO clienteDTO) {

        String nome = clienteDTO.getNome();

        if (nome == null || nome.trim().isEmpty())
            throw new IllegalArgumentException("Nome é obrigatório");

        nome = nome.trim();

        if (nome.length() < 3 || nome.length() > 100)
            throw new IllegalArgumentException("Nome deve ter entre 3 e 100 caracteres");

        if (!nome.matches("[A-Za-zÀ-ÿ0-9 ]+"))
            throw new IllegalArgumentException("Nome deve conter apenas letras, números e espaços");
    }

    private void validarCpf(ClienteDTO clienteDTO) {
        String cpf = clienteDTO.getCpf();

        if (cpf == null || cpf.trim().isEmpty())
            throw new IllegalArgumentException("CPF é obrigatório");

        String cpfSemMascara = cpf.replaceAll("\\D", "");

        if (!cpfSemMascara.matches("\\d{11}"))
            throw new IllegalArgumentException("CPF deve conter 11 números, com ou sem máscara");
    }

    private void validarEndereco(ClienteDTO clienteDTO) {
        // Endereço
        if (clienteDTO.getEndereco() == null)
            throw new IllegalArgumentException("Endereço é obrigatório");

        if (clienteDTO.getEndereco().getCep() == null || clienteDTO.getEndereco().getCep().trim().isEmpty())
            throw new IllegalArgumentException("CEP é obrigatório");

        // Não valida logradouro, bairro, cidade, UF, pois eles serão preenchidos pelo serviço externo


    }

    private void validarEmails(ClienteDTO clienteDTO) {
        // Emails
        if (clienteDTO.getEmails() == null || clienteDTO.getEmails().isEmpty())
            throw new IllegalArgumentException("É necessário cadastrar pelo menos 1 email");

        for (EmailDTO email : clienteDTO.getEmails()) {
            if (email.getEmail() == null || email.getEmail().trim().isEmpty())
                throw new IllegalArgumentException("Email é obrigatório");

            if (!email.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$"))
                throw new IllegalArgumentException("Email inválido: " + email.getEmail());
        }
    }

    private void validarTelefones(ClienteDTO clienteDTO) {
        // Telefones
        if (clienteDTO.getTelefones() == null || clienteDTO.getTelefones().isEmpty())
            throw new IllegalArgumentException("É necessário cadastrar pelo menos 1 telefone");
    }
}
