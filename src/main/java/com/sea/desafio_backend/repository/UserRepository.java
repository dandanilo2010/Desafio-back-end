package com.sea.desafio_backend.repository;

import com.sea.desafio_backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    

    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsername(String username);
}
