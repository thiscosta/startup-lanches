package com.thiscosta.startuplanches.endpoints;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thiscosta.startuplanches.model.Ingrediente;
import com.thiscosta.startuplanches.service.IngredienteService;

@RestController
@RequestMapping("/ingredientes")
public class IngredienteEndpoint {
	
	@Autowired
	IngredienteService service;
	
	@GetMapping
	public List<Ingrediente> listar(){
		return service.listarIngredientes();
	}
	
	@PostMapping
	public Ingrediente inserir(@RequestBody @Valid Ingrediente ingrediente) {
		return service.novoIngrediente(ingrediente);
	}
	
	@GetMapping("/:id")
	public Ingrediente ver(@PathVariable Long id) {
		return service.verIngrediente(id);
	}
	
	@PutMapping("/:id")
	public Ingrediente atualizar(@PathVariable Long id, @RequestBody @Valid Ingrediente ingrediente) {
		return service.atualizarIngrediente(id, ingrediente);
	}

	@DeleteMapping("/:id")
	public void excluir(@PathVariable Long id) {
		service.excluirIngrediente(id);	
	}
}
