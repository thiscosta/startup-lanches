package com.thiscosta.startuplanches.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thiscosta.startuplanches.model.Lanche;

@Repository
public interface LancheRepository extends JpaRepository<Lanche, Long> {

    Lanche findByNome(String nome);

}
