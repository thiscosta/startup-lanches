package com.thiscosta.startuplanches.endpoints;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thiscosta.startuplanches.model.Compra;
import com.thiscosta.startuplanches.service.CompraService;

@RestController
@RequestMapping("/compras")
public class CompraEndpoint {
	
	@Autowired
	CompraService service;
	
	@GetMapping
	public List<Compra> listar(){
		return service.listarCompras();
	}
	
	@PostMapping
	public ResponseEntity inserir(@RequestBody @Valid Compra compra) {
		return service.novaCompra(compra);
	}
	
	@GetMapping("/:id")
	public Compra ver(@PathVariable Long id) {
		return service.verCompra(id);
	}
	
	@PutMapping("/:id")
	public Compra atualizar(@PathVariable Long id, @RequestBody @Valid Compra compra) {
		return service.atualizarCompra(id, compra);
	}

	@DeleteMapping("/:id")
	public void excluir(@PathVariable Long id) {
		service.excluirCompra(id);	
	}
}
