package com.thiscosta.startuplanches.endpoints;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thiscosta.startuplanches.model.Promocao;
import com.thiscosta.startuplanches.service.PromocaoService;

@RestController
@RequestMapping("/promocoes")
public class PromocaoEndpoint {

	@Autowired
	PromocaoService service;
	
	@GetMapping
	public List<Promocao> listarPromocoes(){
		return service.listarPromocoes();
	}
}
