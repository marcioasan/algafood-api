package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

//7.12. Desafio: Criando migrações e mapeando as entidades Pedido e ItemPedido

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class ItemPedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	private BigDecimal precoUnitario;
	private BigDecimal precoTotal;
	private Integer quantidade;	
	private String observacao;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Pedido pedido;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Produto produto;

	//12.21. Desafio: Implementando o endpoint de emissão de pedidos
	public void calcularPrecoTotal() {
	    BigDecimal precoUnitario = this.getPrecoUnitario();
	    Integer quantidade = this.getQuantidade();

	    if (precoUnitario == null) {
	        precoUnitario = BigDecimal.ZERO;
	    }

	    if (quantidade == null) {
	        quantidade = 0;
	    }

	    this.setPrecoTotal(precoUnitario.multiply(new BigDecimal(quantidade)));
	}
}
