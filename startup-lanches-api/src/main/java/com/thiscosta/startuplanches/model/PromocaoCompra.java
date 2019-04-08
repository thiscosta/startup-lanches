package com.thiscosta.startuplanches.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class PromocaoCompra {

	public PromocaoCompra(){}

	public PromocaoCompra(Promocao promocao, Compra compra, double desconto){
		this.promocao = promocao;
		this.compra = compra;
		this.desconto =  desconto;
	}
	
	public PromocaoCompra(Long id, Promocao promocao, Compra compra, double desconto){
		this.id = id;
		this.promocao = promocao;
		this.compra = compra;
		this.desconto =  desconto;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonIgnoreProperties("promocoesCompra")
	@ManyToOne
	@JoinColumn(name = "promocao_id")
	private Promocao promocao;
	
	@JsonIgnoreProperties("promocoesCompra")
	@ManyToOne
	@JoinColumn(name = "compra_id")
	private Compra compra;
	
	private double desconto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Promocao getPromocao() {
		return promocao;
	}

	public void setPromocao(Promocao promocao) {
		this.promocao = promocao;
	}

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	public double getDesconto() {
		return desconto;
	}

	public void setDesconto(double desconto) {
		this.desconto = desconto;
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
		PromocaoCompra other = (PromocaoCompra) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
