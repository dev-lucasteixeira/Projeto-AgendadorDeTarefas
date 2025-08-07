package com.lucasteixeira.com.lucasteixeira.infrastructure.repository.repository;


import com.lucasteixeira.com.lucasteixeira.infrastructure.entity.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone,Long> {
}
