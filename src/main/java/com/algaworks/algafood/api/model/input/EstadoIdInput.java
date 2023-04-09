package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EstadoIdInput {

	@ApiModelProperty(example = "1", required = true) //18.11. Descrevendo restrições de validação de propriedades do modelo - 7'
    @NotNull
    private Long id;
}
