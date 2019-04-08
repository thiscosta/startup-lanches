package com.thiscosta.startuplanches.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.thiscosta.startuplanches.model.Lanche;
import com.thiscosta.startuplanches.repository.LancheRepository;

@Service
public class LancheService {
	
	@Autowired
	LancheRepository repository;

	public List<Lanche> listarLanches(){
		return repository.findAll();
	}
	
	public Lanche novoLanche(Lanche lanche) {
		Lanche lancheExistente = verLanchePorNome(lanche.getNome());
		if(lancheExistente == null){
			return repository.save(lanche);
		}
		return lancheExistente;
		
	}
	
	public Lanche verLanche(Long id) {
		return repository.getOne(id);
	}

	public Lanche verLanchePorNome(String nome) {
		return repository.findByNome(nome);
	}
	
	public Lanche atualizarLanche(Long id, Lanche lanche) {
		Lanche novoLanche = repository.getOne(id);
		
		if(novoLanche == null) {
			return new Lanche();
		}
		
		BeanUtils.copyProperties(lanche, novoLanche, "id");
		
		return novoLanche;
	}
	
	public void excluirLanche(Long id) {
		repository.deleteById(id);
	}

}
