package com.sea.desafio_backend.service;

import com.sea.desafio_backend.entity.ClienteEntity;
import com.sea.desafio_backend.entity.RoleEntity;
import com.sea.desafio_backend.entity.UserEntity;
import com.sea.desafio_backend.repository.ClienteRepository;
import com.sea.desafio_backend.repository.RoleRepository;
import com.sea.desafio_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, 
                      RoleRepository roleRepository,
                      ClienteRepository clienteRepository,
                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserEntity createUser(String username, String password, List<String> roleNames) {
        return createUser(username, password, roleNames, null);
    }

    @Transactional
    public UserEntity createUser(String username, String password, List<String> roleNames, ClienteEntity cliente) {
        if (userRepository.existsByUsername(username)) {
            logger.warn("Username {} already exists", username);
            throw new RuntimeException("Username already exists");
        }

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);

        List<RoleEntity> roles = new ArrayList<>();
        for (String roleName : roleNames) {
            RoleEntity role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
            roles.add(role);
        }
        user.setRoles(roles);

        if (cliente != null) {
            user.setCliente(cliente);
        }

        return userRepository.save(user);
    }

    @Transactional
    public RoleEntity createRole(String name) {
        if (roleRepository.existsByName(name)) {
            logger.info("Role {} already exists", name);
            return roleRepository.findByName(name)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + name));
        }

        RoleEntity role = new RoleEntity();
        role.setName(name);
        return roleRepository.save(role);
    }

    @Transactional(readOnly = true)
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void assignRoleToUser(String username, String roleName) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        RoleEntity role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));

        if (!user.getRoles().contains(role)) {
            user.getRoles().add(role);
            userRepository.save(user);
        }
    }

    @Transactional
    public void assignClienteToUser(String username, Long clienteId) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        ClienteEntity cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente not found: " + clienteId));

        user.setCliente(cliente);
        userRepository.save(user);
        logger.info("Cliente {} assigned to user {}", clienteId, username);
    }
}
