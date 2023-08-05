package com.algaworks.algafood.api.v1.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

//12.13. Desafio: implementando os endpoints de produtos

@Relation(collectionRelation = "produtos")
@Setter
@Getter
public class ProdutoModel extends RepresentationModel<ProdutoModel>{

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Boolean ativo; 
}