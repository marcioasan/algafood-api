package com.algaworks.algafood.api.v1.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.v1.model.ProdutoModel;
import com.algaworks.algafood.domain.model.Produto;

//12.13. Desafio: implementando os endpoints de produtos

@Component
public class ProdutoModelAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoModel> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private AlgaLinks algaLinks;
    
    public ProdutoModelAssembler() {
        super(RestauranteProdutoController.class, ProdutoModel.class);
    }
    
    //19.32. Desafio: adicionando links para recurso de foto de produto
    public ProdutoModel toModel(Produto produto) {
        ProdutoModel produtoModel = createModelWithId(
                produto.getId(), produto, produto.getRestaurante().getId());
        
        modelMapper.map(produto, produtoModel);
        
        produtoModel.add(algaLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));

        produtoModel.add(algaLinks.linkToFotoProduto(
                produto.getRestaurante().getId(), produto.getId(), "foto"));
        
        return produtoModel;
    }
    
    /*
    public ProdutoModel toModel(Produto produto) {
        ProdutoModel produtoModel = createModelWithId(
                produto.getId(), produto, produto.getRestaurante().getId());
        
        modelMapper.map(produto, produtoModel);
        
        produtoModel.add(algaLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));
        
        return produtoModel;
    }
    */
    
//    public List<ProdutoModel> toCollectionModel(List<Produto> produtos) {
//        return produtos.stream()
//                .map(produto -> toModel(produto))
//                .collect(Collectors.toList());
//    }  
}