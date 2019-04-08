package com.thiscosta.startuplanches.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Compra{
	
	public Compra() {}
	
	public Compra(Long id, double preco, List<CompraLanche> lanchesCompra) {
		this.id = id;
		this.preco = preco;
		this.lanchesCompra = lanchesCompra;
	}
	
	public Compra(Long id, double preco, List<CompraLanche> lanchesCompra, List<PromocaoCompra> promocoesCompra) {
		this.id = id;
		this.preco = preco;
		this.lanchesCompra = lanchesCompra;
		this.promocoesCompra = promocoesCompra;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private double preco;
	
	@NotEmpty
	@JsonIgnoreProperties("compra")
	@OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CompraLanche> lanchesCompra;
	
	@JsonIgnoreProperties("compra")
	@OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PromocaoCompra> promocoesCompra;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public void incrementarPreco(double preco){
		this.preco += preco;
	}

	public List<CompraLanche> getLanchesCompra() {
		return this.lanchesCompra;
	}
	public void setLanchesCompra(List<CompraLanche> lanchesCompra) {
		this.lanchesCompra = lanchesCompra;
	}

	public List<PromocaoCompra> getPromocoesCompra() {
		return promocoesCompra;
	}

	public void setPromocoesCompra(List<PromocaoCompra> promocoesCompra) {
		this.promocoesCompra = promocoesCompra;
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
		Compra other = (Compra) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
