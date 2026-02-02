package com.sea.desafio_backend.repository;

import com.sea.desafio_backend.entity.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<EmailEntity, Long> {

    List<EmailEntity> findByClienteId(Long clienteId);

    boolean existsByEmail(String email);

    Optional<EmailEntity> findByEmail(String email);

}
