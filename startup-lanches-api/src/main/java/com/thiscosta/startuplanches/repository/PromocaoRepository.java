package com.thiscosta.startuplanches.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thiscosta.startuplanches.model.Promocao;

@Repository
public interface PromocaoRepository extends JpaRepository<Promocao, Long> {

}
