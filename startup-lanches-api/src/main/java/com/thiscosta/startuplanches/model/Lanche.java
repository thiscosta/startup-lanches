package com.thiscosta.startuplanches.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Lanche {

	public Lanche(){}

	public Lanche(Long id, String nome, double preco){
		this.id = id;
		this.nome = nome;
		this.preco = preco;
	}

	public Lanche(String nome, double preco){
		this.nome = nome;
		this.preco = preco;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	@NotBlank(message = "O lanche deve ter um nome.")
	private String nome;
	
	private double preco;
	
	@JsonIgnoreProperties("lanche")
	@OneToMany(mappedBy = "lanche", orphanRemoval = true)
	private List<LancheIngrediente> ingredientes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public List<LancheIngrediente> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(List<LancheIngrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lanche other = (Lanche) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
