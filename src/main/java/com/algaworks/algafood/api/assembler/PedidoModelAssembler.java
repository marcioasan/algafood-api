package com.algaworks.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.controller.FormaPagamentoController;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.domain.model.Pedido;

//19.20. Refatorando construção e inclusão de links em representation model

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;  //19.21. Desafio: refatorando construção e inclusão de links

	public PedidoModelAssembler() {
		super(PedidoController.class, PedidoModel.class);
	}
	
	@Override
	public PedidoModel toModel(Pedido pedido) {
	    PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
	    modelMapper.map(pedido, pedidoModel);
	    
	    pedidoModel.add(algaLinks.linkToPedidos());
	    
	    pedidoModel.getRestaurante().add(
	            algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));
	    
	    pedidoModel.getCliente().add(
	            algaLinks.linkToUsuario(pedido.getCliente().getId()));
	    
	    pedidoModel.getFormaPagamento().add(
	            algaLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));
	    
	    pedidoModel.getEnderecoEntrega().getCidade().add(
	            algaLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));
	    
	    pedidoModel.getItens().forEach(item -> {
	        item.add(algaLinks.linkToProduto(
	                pedidoModel.getRestaurante().getId(), item.getProdutoId(), "produto"));
	    });
		
		return pedidoModel;
	}
}
