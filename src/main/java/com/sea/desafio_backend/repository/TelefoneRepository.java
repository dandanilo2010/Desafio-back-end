package com.sea.desafio_backend.repository;

import com.sea.desafio_backend.entity.TelefoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for managing telephone entities.
 */
@Repository
public interface TelefoneRepository extends JpaRepository<TelefoneEntity, Long> {

    List<TelefoneEntity> findByClienteId(Long clienteId);

}
