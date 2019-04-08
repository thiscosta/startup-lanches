package com.thiscosta.startuplanches.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiscosta.startuplanches.model.PromocaoCompra;
import com.thiscosta.startuplanches.repository.PromocaoCompraRepository;

@Service
public class PromocaoCompraService {

	@Autowired
	PromocaoCompraRepository repository;
	
	public List<PromocaoCompra> listarPromocoesCompra(){
		return repository.findAll();
	}

	public PromocaoCompra novaPromocaoCompra(PromocaoCompra promocaoCompra) {
		return repository.save(promocaoCompra);
	}
	
}
