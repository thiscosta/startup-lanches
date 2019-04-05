package com.thiscosta.startuplanches.endpoints;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

}
