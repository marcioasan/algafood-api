package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService.Mensagem;


@Service
public class FluxoPedidoService {

	//12.24. Refatorando o código de regras para transição de status de pedidos
	@Autowired
	private EmissaoPedidoService emissaoPedido;
	
	@Autowired
	private EnvioEmailService envioEmail;
	
	@Transactional
	public void confirmar(String codigoPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
		pedido.confirmar();
		
		//15.4. Usando o serviço de envio de e-mails na confirmação de pedidos - 3' //OBS: aqui, o builder pede um "Set<String> destinatarios",então, para não precisar criar um SET, foi anotado na classe Mensagem no atributo destinatarios com @Singular do lombok que singulariza e passa só um objeto ao invés de um SET
		var mensagem = Mensagem.builder()
				.assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
				.corpo("O pedido de código <strong>" 
						+ pedido.getCodigo() + "</strong> foi confirmado!")
				.destinatario(pedido.getCliente().getEmail())
				.build();
		
		envioEmail.enviar(mensagem);
	}
	
	@Transactional
	public void cancelar(String codigoPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
		pedido.cancelar();
	}
	
	@Transactional
	public void entregar(String codigoPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
		pedido.entregar();
	}
	
	
	
	/* //12.22. Implementando endpoint de transição de status de pedidos - 8'30", 17'20"
	@Transactional
	public void confirmar(Long pedidoId) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);
		
		if (!pedido.getStatus().equals(StatusPedido.CRIADO)) {
			throw new NegocioException(
					String.format("Status do pedido %d não pode ser alterado de %s para %s",
							pedido.getId(), pedido.getStatus().getDescricao(), 
							StatusPedido.CONFIRMADO.getDescricao()));
		}
		
		pedido.setStatus(StatusPedido.CONFIRMADO);
		pedido.setDataConfirmacao(OffsetDateTime.now());
	}
	
	//12.23. Desafio: implementando endpoints de transição de status de pedidos
	@Transactional
	public void cancelar(Long pedidoId) {
	    Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);
	    
	    if (!pedido.getStatus().equals(StatusPedido.CRIADO)) {
	        throw new NegocioException(
	                String.format("Status do pedido %d não pode ser alterado de %s para %s",
	                        pedido.getId(), pedido.getStatus().getDescricao(), 
	                        StatusPedido.CANCELADO.getDescricao()));
	    }
	    
	    pedido.setStatus(StatusPedido.CANCELADO);
	    pedido.setDataCancelamento(OffsetDateTime.now());
	}

	@Transactional
	public void entregar(Long pedidoId) {
	    Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);
	    
	    if (!pedido.getStatus().equals(StatusPedido.CONFIRMADO)) {
	        throw new NegocioException(
	                String.format("Status do pedido %d não pode ser alterado de %s para %s",
	                        pedido.getId(), pedido.getStatus().getDescricao(), 
	                        StatusPedido.ENTREGUE.getDescricao()));
	    }
	    
	    pedido.setStatus(StatusPedido.ENTREGUE);
	    pedido.setDataEntrega(OffsetDateTime.now());
	}
	*/
}