package com.algaworks.algafood.api.v1.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.CozinhaController;
import com.algaworks.algafood.api.v1.model.CozinhaModel;
import com.algaworks.algafood.domain.model.Cozinha;

//19.15. Adicionando hypermedia em recursos com paginação - 1'30"

@Component
public class CozinhaModelAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModel>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;  //19.21. Desafio: refatorando construção e inclusão de links
	
    public CozinhaModelAssembler() {
		super(CozinhaController.class, CozinhaModel.class);
	}
    
    @Override
    public CozinhaModel toModel(Cozinha cozinha) {
        CozinhaModel cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaModel);
        
        cozinhaModel.add(algaLinks.linkToCozinhas("cozinhas"));
    	
    	return cozinhaModel;
    }
         
}
