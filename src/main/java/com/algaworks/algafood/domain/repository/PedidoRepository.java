package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.api.controller.dto.VendaDiaria;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, String>, 
			JpaSpecificationExecutor<Pedido>{ //13.6. Implementando pesquisas complexas na API - 9'30"

	//12.25. Usando IDs vs UUIDs nas URIs de recursos - 13'50"	
	Optional<Pedido> findByCodigo(String codigo);
	
	//12.20. Otimizando a query de pedidos e retornando model resumido na listagem - 8'20", 11'40"
	@Query("from Pedido p join fetch p.cliente join fetch p.restaurante r join fetch r.cozinha")
	List<Pedido> findAll();
}
