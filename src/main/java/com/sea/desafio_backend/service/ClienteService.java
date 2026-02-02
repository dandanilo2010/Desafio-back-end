package com.sea.desafio_backend.service;

import com.sea.desafio_backend.dto.*;
import com.sea.desafio_backend.entity.*;
import com.sea.desafio_backend.exception.ClienteNotFoundException;
import com.sea.desafio_backend.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // ================= CREATE =================
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

      //  clienteDTO = new ClienteDTO(salvo);

       // formatterService.formatarCliente(clienteDTO);

        return  clienteDTO;
    }

    // ================= UPDATE =================
    public ClienteDTO atualizarCliente(Long id, ClienteDTO clienteDTO) {

        validationService.validarClienteDTOParaAtualizacao(clienteDTO, id);

        ClienteEntity cliente = buscarEntityPorId(id);
        cliente.setNome(clienteDTO.getNome());

        String cpfSemMascara = clienteDTO.getCpf().replaceAll("\\D", "");
        cliente.setCpf(cpfSemMascara);

        EnderecoEntity endereco = enderecoService.criarEndereco(clienteDTO.getEndereco());
        cliente.setEndereco(endereco);

        cliente.getEmails().clear();
        cliente.getEmails().addAll(
                emailService.criarEmails(clienteDTO.getEmails(), cliente)
        );

        cliente.getTelefones().clear();
        cliente.getTelefones().addAll(
                telefoneService.criarTelefones(clienteDTO.getTelefones(), cliente)
        );

        ClienteEntity atualizado = clienteRepository.save(cliente);
        //formatterService.formatarCliente(atualizado)
        return clienteDTO;
    }

    // ================= READ =================
    @Transactional(readOnly = true)
    public List<ClienteDTO> listarTodos() {
        List<ClienteEntity> clientes = clienteRepository.findAll();
        List<ClienteDTO> clienteDTOList = clientes.stream().map(ClienteDTO::new).collect(Collectors.toList());
        return clienteDTOList.stream()
                .map(formatterService::formatarCliente)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ClienteDTO buscarPorId(Long id) {
        ClienteEntity cliente = buscarEntityPorId(id);
        ClienteDTO clienteDTO = new ClienteDTO(cliente);
        formatterService.formatarCliente(clienteDTO);
        return clienteDTO;
    }

    // ================= DELETE =================
    public void deletarCliente(Long id) {
        ClienteEntity cliente = buscarEntityPorId(id);
        clienteRepository.delete(cliente);
    }

    // ================= INTERNAL =================
    private ClienteEntity buscarEntityPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ClienteNotFoundException("Cliente com id " + id + " não encontrado")
                );
    }
}
