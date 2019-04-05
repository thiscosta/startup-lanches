package com.thiscosta.startuplanches.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiscosta.startuplanches.model.Compra;
import com.thiscosta.startuplanches.repository.CompraRepository;

@Service
public class CompraService {

	@Autowired
	CompraRepository repository;
	
	public List<Compra> listarCompras(){
		return repository.findAll();
	}
}
