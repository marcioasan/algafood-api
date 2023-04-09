package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

//@ApiModel(value = "Cidade", description = "Representa uma cidade") //18.10. Descrevendo modelos de representações e suas propriedades
@Setter
@Getter
public class CidadeModel {

//	@ApiModelProperty(value = "ID da cidade", example = "1") //18.10. Descrevendo modelos de representações e suas propriedades - 4'
	@ApiModelProperty(example = "1") //18.10. Descrevendo modelos de representações e suas propriedades - 4'
    private Long id;
	
	@ApiModelProperty(example = "Uberlândia")
    private String nome;
    private EstadoModel estado;
}
