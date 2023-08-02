package com.algaworks.algafood.api.v2.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

//20.11. Implementando o versionamento da API por Media Type - 8'55"

@Relation(collectionRelation = "cidades")
@Setter
@Getter
public class CidadeModelV2 extends RepresentationModel<CidadeModelV2> {

	private Long idCidade;
		
	private String nomeCidade;
	
	private Long idEstado;
	private String nomeEstado;
	
}
