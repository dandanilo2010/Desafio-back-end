package com.sea.desafio_backend.repository;


import com.sea.desafio_backend.entity.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoEntity, Long> {

    EnderecoEntity findByCep(String cep);

}
