package com.algaworks.algafood.infrastructure.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepositoryQueries;

//14.6. Implementando serviço de cadastro de foto de produto - 5'20"
@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;

	@Transactional
	@Override
	public FotoProduto save(FotoProduto foto) {
		return manager.merge(foto);
	}
	
	//14.7. Excluindo e substituindo cadastro de foto de produto - 6'50"
	@Transactional
	@Override
	public void delete(FotoProduto foto) {
		manager.remove(foto);
	}
}
