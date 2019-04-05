package com.thiscosta.startuplanches.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiscosta.startuplanches.repository.LancheIngredienteRepository;

@Service
public class LancheIngredienteService {

	@Autowired
	LancheIngredienteRepository repository;
}
