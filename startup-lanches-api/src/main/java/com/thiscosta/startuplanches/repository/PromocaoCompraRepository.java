package com.thiscosta.startuplanches.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thiscosta.startuplanches.model.PromocaoCompra;

@Repository
public interface PromocaoCompraRepository extends JpaRepository<PromocaoCompra, Long> {

}
