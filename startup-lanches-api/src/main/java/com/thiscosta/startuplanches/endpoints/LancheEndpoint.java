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

import com.thiscosta.startuplanches.model.Lanche;
import com.thiscosta.startuplanches.service.LancheService;

@RestController
@RequestMapping("/lanches")
public class LancheEndpoint {
	
	@Autowired
	LancheService service;
	
	@GetMapping
	public List<Lanche> listar(){
		return service.listarLanches();
	}
	
	@PostMapping
	public Lanche inserir(@RequestBody @Valid Lanche lanche) {
		return service.novoLanche(lanche);
	}
	
	@GetMapping("/:id")
	public Lanche ver(@PathVariable Long id) {
		return service.verLanche(id);
	}
	
	@PutMapping("/:id")
	public Lanche atualizar(@PathVariable Long id, @RequestBody @Valid Lanche lanche) {
		return service.atualizarLanche(id, lanche);
	}

	@DeleteMapping("/:id")
	public void excluir(@PathVariable Long id) {
		service.excluirLanche(id);	
	}
}
