package com.thiscosta.startuplanches.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class LancheIngrediente {

	public LancheIngrediente(){}

	public LancheIngrediente(Lanche lanche, Ingrediente ingrediente, Integer quantidade){
		this.lanche = lanche;
		this.ingrediente = ingrediente;
		this.quantidade =  quantidade;
	}
	
	public LancheIngrediente(Long id, Lanche lanche, Ingrediente ingrediente, Integer quantidade){
		this.id = id;
		this.lanche = lanche;
		this.ingrediente = ingrediente;
		this.quantidade =  quantidade;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "lanche_id")
	private Lanche lanche;
	
	@ManyToOne
	@JoinColumn(name = "ingrediente_id")
	private Ingrediente ingrediente;
	
	private Integer quantidade;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Lanche getLanche() {
		return lanche;
	}

	public void setLanche(Lanche lanche) {
		this.lanche = lanche;
	}

	public Ingrediente getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	

}
