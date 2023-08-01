package com.algaworks.algafood.api.v1.model.mixin;

import java.util.List;

import com.algaworks.algafood.domain.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonIgnore;

//11.4. Desafio: usando @JsonIgnoreProperties e Jackson Mixin
public class CozinhaMixin {

    @JsonIgnore
    private List<Restaurante> restaurantes;
}
