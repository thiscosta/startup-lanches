package com.thiscosta.startuplanches.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiscosta.startuplanches.model.Promocao;
import com.thiscosta.startuplanches.repository.PromocaoRepository;

@Service
public class PromocaoService {

	@Autowired
	PromocaoRepository repository;
	
	public List<Promocao> listarPromocoes(){
		return repository.findAll();
	}
	
	public Promocao verPromocaoPorNome(String nome) {
		return repository.findByNome(nome);
	}
}
