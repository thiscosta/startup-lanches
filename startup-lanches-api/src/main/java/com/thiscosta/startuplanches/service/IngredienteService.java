package com.thiscosta.startuplanches.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.thiscosta.startuplanches.model.Ingrediente;
import com.thiscosta.startuplanches.repository.IngredienteRepository;

@Service
public class IngredienteService {

	public IngredienteService(){}

	public IngredienteService(IngredienteRepository ingredienteRepository ){
		this.repository = ingredienteRepository;
	}

	@Autowired
	IngredienteRepository repository;

	public List<Ingrediente> listarIngredientes(){
		return repository.findAll();
	}
	
	public Ingrediente novoIngrediente(Ingrediente ingrediente) {
		return new Ingrediente();
	}
	
	public Ingrediente verIngrediente(Long id) {
		return repository.getOne(id);
	}

	public Ingrediente verIngredientePorNome(String nome){
		return repository.findByNome(nome);
	}
	
	public Ingrediente atualizarIngrediente(Long id, Ingrediente ingrediente) {
		Ingrediente novoIngrediente = repository.getOne(id);
		
		if(novoIngrediente == null) {
			return new Ingrediente();
		}
		
		BeanUtils.copyProperties(ingrediente, novoIngrediente, "id");
		
		return novoIngrediente;
	}
	
	public void excluirIngrediente(Long id) {
		repository.deleteById(id);
	}
}
