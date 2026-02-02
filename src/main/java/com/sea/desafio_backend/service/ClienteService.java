package com.sea.desafio_backend.service;

import com.sea.desafio_backend.dto.*;
import com.sea.desafio_backend.entity.*;
import com.sea.desafio_backend.exception.ClienteNotFoundException;
import com.sea.desafio_backend.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteValidationService validationService;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TelefoneService telefoneService;

    @Autowired
    private ClienteFormatterService formatterService;

    // CRIAR
    public ClienteDTO salvarCliente(ClienteDTO clienteDTO) {

        validationService.validarClienteDTO(clienteDTO);

        ClienteEntity cliente = new ClienteEntity();
        cliente.setNome(clienteDTO.getNome());

        String cpfSemMascara = clienteDTO.getCpf().replaceAll("\\D", "");
        validationService.validarCpfDuplicado(cpfSemMascara);
        cliente.setCpf(cpfSemMascara);

        EnderecoEntity endereco = enderecoService.criarEndereco(clienteDTO.getEndereco());
        cliente.setEndereco(endereco);

        List<EmailEntity> emails = emailService.criarEmails(clienteDTO.getEmails(), cliente);
        cliente.setEmails(emails);

        List<TelefoneEntity> telefones = telefoneService.criarTelefones(clienteDTO.getTelefones(), cliente);
        cliente.setTelefones(telefones);

        ClienteEntity salvo = clienteRepository.save(cliente);

        ClienteDTO retorno = new ClienteDTO(salvo);
        formatterService.formatarCliente(retorno);

        return retorno;
    }

    public ClienteDTO atualizarCliente(Long id, ClienteDTO clienteDTO) {

        // Validação do DTO
        validationService.validarClienteDTOParaAtualizacao(clienteDTO, id);

        // Buscar o cliente existente
        ClienteEntity cliente = buscarEntityPorId(id);
        cliente.setNome(clienteDTO.getNome());
        cliente.setCpf(clienteDTO.getCpf().replaceAll("\\D", ""));

        // Atualizar endereço
        EnderecoEntity endereco = enderecoService.criarEndereco(clienteDTO.getEndereco());
        cliente.setEndereco(endereco);

        // Atualizar emails
        cliente.getEmails().clear();
        if (clienteDTO.getEmails() != null) {
            List<EmailEntity> emailsAtualizados = new ArrayList<>();
            for (EmailDTO emailDto : clienteDTO.getEmails()) {
                EmailEntity email = new EmailEntity();
                email.setEmail(emailDto.getEmail());
                email.setCliente(cliente);
                emailsAtualizados.add(email);
            }
            cliente.getEmails().addAll(emailsAtualizados);
        }

        // Atualizar telefones
        cliente.getTelefones().clear();
        if (clienteDTO.getTelefones() != null) {
            List<TelefoneEntity> telefonesAtualizados = new ArrayList<>();
            for (TelefoneDTO telDto : clienteDTO.getTelefones()) {
                TelefoneEntity telefone = new TelefoneEntity();
                telefone.setNumero(telDto.getNumero());
                telefone.setTipo(telDto.getTipo());
                telefone.setCliente(cliente);
                telefonesAtualizados.add(telefone);
            }
            cliente.getTelefones().addAll(telefonesAtualizados);
        }

        // Salvar cliente atualizado
        ClienteEntity atualizado = clienteRepository.save(cliente);

        // Converter para DTO e formatar
        ClienteDTO retorno = new ClienteDTO(atualizado);
        formatterService.formatarCliente(retorno);

        return retorno;
    }



    // LISTAR
    @Transactional(readOnly = true)
    public List<ClienteDTO> listarTodos() {
        return clienteRepository.findAll().stream()
                .map(ClienteDTO::new)
                .map(formatterService::formatarCliente)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ClienteDTO buscarPorId(Long id) {
        ClienteEntity cliente = buscarEntityPorId(id);
        ClienteDTO dto = new ClienteDTO(cliente);
        formatterService.formatarCliente(dto);
        return dto;
    }

    // DELETAR
    public void deletarCliente(Long id) {
        ClienteEntity cliente = buscarEntityPorId(id);
        clienteRepository.delete(cliente);
    }

    // BUSCAR
    private ClienteEntity buscarEntityPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ClienteNotFoundException("Cliente com id " + id + " não encontrado")
                );
    }
}
