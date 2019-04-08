package com.thiscosta.startuplanches.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.thiscosta.startuplanches.model.CompraLanche;
import com.thiscosta.startuplanches.repository.CompraLancheRepository;

@Service
public class CompraLancheService {

	@Autowired
	CompraLancheRepository repository;

	public List<CompraLanche> listarComprasLanche(){
		return repository.findAll();
	}

	public CompraLanche novaCompraLanche(CompraLanche compraLanche){
		return repository.save(compraLanche);
	}
}
