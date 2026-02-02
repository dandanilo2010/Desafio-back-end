package com.sea.desafio_backend.config;

import com.sea.desafio_backend.dto.ClienteDTO;
import com.sea.desafio_backend.dto.EmailDTO;
import com.sea.desafio_backend.dto.EnderecoDTO;
import com.sea.desafio_backend.dto.TelefoneDTO;
import com.sea.desafio_backend.entity.ClienteEntity;
import com.sea.desafio_backend.enums.TipoTelefone;
import com.sea.desafio_backend.service.ClienteService;
import com.sea.desafio_backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class DatabaseInitializer {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);

    private final String adminUsername = "admin";

    private final String adminPassword =  "123qwe!@#";

    private final String userUsername = "user";

    private final String userPassword = "123qwe123";

    @Bean
    public CommandLineRunner initDatabase(UserService userService, ClienteService clienteService) {
        return args -> {
            logger.info("Initializing database with default roles, users, and clients");

            // Create roles
            userService.createRole("ADMIN");
            userService.createRole("USER");

            // Create admin client
            ClienteDTO adminCliente = null;
            try {
                ClienteDTO adminClienteDTO = createAdminClienteDTO();
                adminCliente = clienteService.salvarCliente(adminClienteDTO);
                logger.info("Admin client created with ID: {}", adminCliente.getId());
            } catch (Exception e) {
                logger.warn("Could not create admin client: {}", e.getMessage());
            }

            // Create user client
            ClienteDTO userCliente = null;
            try {
                ClienteDTO userClienteDTO = createUserClienteDTO();
                userCliente = clienteService.salvarCliente(userClienteDTO);
                logger.info("User client created with ID: {}", userCliente.getId());
            } catch (Exception e) {
                logger.warn("Could not create user client: {}", e.getMessage());
            }

            // Create admin user with ADMIN and USER roles
            try {
                userService.createUser(adminUsername, adminPassword, Arrays.asList("ADMIN", "USER"));
                logger.info("Admin user created: {}", adminUsername);

                // Assign client to admin user
                if (adminCliente != null) {
                    userService.assignClienteToUser(adminUsername, adminCliente.getId());
                    logger.info("Admin client assigned to admin user");
                }
            } catch (Exception e) {
                logger.warn("Could not create admin user: {}", e.getMessage());
            }

            // Create regular user with USER role
            try {
                userService.createUser(userUsername, userPassword, Collections.singletonList("USER"));
                logger.info("Regular user created: {}", userUsername);

                // Assign client to regular user
                if (userCliente != null) {
                    userService.assignClienteToUser(userUsername, userCliente.getId());
                    logger.info("User client assigned to regular user");
                }
            } catch (Exception e) {
                logger.warn("Could not create regular user: {}", e.getMessage());
            }

            logger.info("Database initialization completed");
        };
    }

    private ClienteDTO createAdminClienteDTO() {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNome("Admin Cliente");
        clienteDTO.setCpf("12345678901");

        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setCep("12345-678");
        enderecoDTO.setLogradouro("Rua Admin");
        enderecoDTO.setBairro("Bairro Admin");
        enderecoDTO.setCidade("Cidade Admin");
        enderecoDTO.setUf("SP");
        clienteDTO.setEndereco(enderecoDTO);

        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setEmail("admin@example.com");
        clienteDTO.setEmails(Collections.singletonList(emailDTO));

        TelefoneDTO telefoneDTO = new TelefoneDTO();
        telefoneDTO.setNumero("(11) 98765-4321");
        telefoneDTO.setTipo(TipoTelefone.CELULAR);
        clienteDTO.setTelefones(Collections.singletonList(telefoneDTO));

        return clienteDTO;
    }

    private ClienteDTO createUserClienteDTO() {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNome("User Cliente");
        clienteDTO.setCpf("98765432109");

        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setCep("98765-432");
        enderecoDTO.setLogradouro("Rua User");
        enderecoDTO.setBairro("Bairro User");
        enderecoDTO.setCidade("Cidade User");
        enderecoDTO.setUf("RJ");
        clienteDTO.setEndereco(enderecoDTO);

        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setEmail("user@example.com");
        clienteDTO.setEmails(Collections.singletonList(emailDTO));

        TelefoneDTO telefoneDTO = new TelefoneDTO();
        telefoneDTO.setNumero("(21) 98765-4321");
        telefoneDTO.setTipo(TipoTelefone.CELULAR);
        clienteDTO.setTelefones(Collections.singletonList(telefoneDTO));

        return clienteDTO;
    }
}
