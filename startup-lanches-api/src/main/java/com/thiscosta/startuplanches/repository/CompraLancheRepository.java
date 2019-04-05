package com.thiscosta.startuplanches.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thiscosta.startuplanches.model.CompraLanche;

@Repository
public interface CompraLancheRepository extends JpaRepository<CompraLanche, Long> {

}
