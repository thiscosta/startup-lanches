package com.thiscosta.startuplanches.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thiscosta.startuplanches.model.Compra;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {

}
