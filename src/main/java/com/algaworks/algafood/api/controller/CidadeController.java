package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.assembler.CidadeModelAssembler;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cidades") //18.7. Descrevendo tags na documentação e associando com controllers - 1'40" - Referencia o atributo tags configurado na classe SpringFoxConfig no método apiDocket() .tags
@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cidadeService;
	
	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;

	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;
	
	@ApiOperation("Lista as cidades") //18.8. Descrevendo as operações de endpoints na documentação
	@GetMapping
	public List<CidadeModel> listar() {
	    List<Cidade> todasCidades = cidadeRepository.findAll();
	    
	    return cidadeModelAssembler.toCollectionModel(todasCidades);
	}
	
	
	
	
	@ApiOperation("Busca uma cidade por ID") //18.8. Descrevendo as operações de endpoints na documentação
	@ApiResponses({ //18.16. Descrevendo códigos de status de respostas em endpoints específicos - 3'
		@ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	@GetMapping("/{cidadeId}") //8.6. Desafio: refatorando os serviços REST
	public CidadeModel buscar(
			@ApiParam(value = "ID de uma cidade", example = "1") //18.9. Descrevendo parâmetros de entrada na documentação - 1', 2'10", 3'45" não exibiu no html o exemplo. Olhando os posts da aula, foi informaod que o SpringFox foi descontinuado e irão usar o SpringDoc mais a frente.
			@PathVariable Long cidadeId) {
	    Cidade cidade = cidadeService.buscarOuFalhar(cidadeId);
	    
	    return cidadeModelAssembler.toModel(cidade);
	}
	
	/*
    @GetMapping("/{cidadeId}")
	public ResponseEntity<Cidade> buscar(@PathVariable Long cidadeId) {
		Optional<Cidade> cidade = cidadeRepository.findById(cidadeId);
		
		if (cidade.isEmpty()) {
			return ResponseEntity.ok(cidade.get());
		}
		
		return ResponseEntity.notFound().build();
	}
    */
	
	
	
	
	@ApiOperation("Cadastra uma cidade")
	@ApiResponses({ //18.16. Descrevendo códigos de status de respostas em endpoints específicos - 5'30"
		@ApiResponse(code = 201, message = "Cidade cadastrada")
	})
	@PostMapping //11.20. Desafio: usando DTOs como representation model
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModel adicionar(
			@ApiParam(name = "corpo", value = "Representação de uma nova cidade") //18.9. Descrevendo parâmetros de entrada na documentação - 4'50"
			@RequestBody @Valid CidadeInput cidadeInput) {
	    try {
	        Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
	        
	        cidade = cidadeService.salvar(cidade);
	        
	        return cidadeModelAssembler.toModel(cidade);
	    } catch (EstadoNaoEncontradoException e) {
	        throw new NegocioException(e.getMessage(), e);
	    }
	}
	
	//8.6. Desafio: refatorando os serviços REST
	/*
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
	public Cidade adicionar(@RequestBody @Valid Cidade cidade) { //9.9. Desafio: adicionando constraints de validação no modelo - @Valid ver anotações na classe RestauranteController.java
    	try {
    		Cidade cidadeSalva = cidadeService.salvar(cidade);
    		return cidadeSalva;
		} catch (EstadoNaoEncontradoException e) { //8.8. Criando a exception NegocioException - 10'
			throw new NegocioException(e.getMessage(), e);//8.10. Afinando a granularidade e definindo a hierarquia das exceptions de negócios - 16', 17'10"
		}
    }
	*/
	
	
//	@PostMapping
//	@ResponseStatus(HttpStatus.CREATED)
//	public Cidade adicionar(@RequestBody Cidade cidade) {		
//		cidade = cidadeService.salvar(cidade);
//		return cidade;
//	}
	
    /**
     Duas formas de retornar HttpStatus.CREATED
     1 - anotado no método
     2 - no retorno do método
     */
    
	/*
    @PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Cidade cidade) {
		try {
			cidade = cidadeService.salvar(cidade);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
    */
	
    
    
	@ApiOperation("Atualiza uma cidade por ID")
	@ApiResponses({ //18.16. Descrevendo códigos de status de respostas em endpoints específicos - 6'30"
		@ApiResponse(code = 200, message = "Cidade atualizada"),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	@PutMapping("/{cidadeId}")
	public CidadeModel atualizar(
			@ApiParam(value = "ID de uma cidade", example = "1")
			@PathVariable Long cidadeId,
			
			@ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados")
	        @RequestBody @Valid CidadeInput cidadeInput) {
	    try {
	        Cidade cidadeAtual = cidadeService.buscarOuFalhar(cidadeId);
	        
	        cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);
	        
	        cidadeAtual = cidadeService.salvar(cidadeAtual);
	        
	        return cidadeModelAssembler.toModel(cidadeAtual);
	    } catch (EstadoNaoEncontradoException e) {
	        throw new NegocioException(e.getMessage(), e);
	    }
	}
    
    
    //8.6. Desafio: refatorando os serviços REST
	/*
    @PutMapping("/{cidadeId}")
    public Cidade atualizar(@PathVariable Long cidadeId, @RequestBody @Valid Cidade cidade) {
    	
    	try {
    		Cidade cidadeAtual = cidadeService.buscarOuFalhar(cidadeId);
    		BeanUtils.copyProperties(cidade, cidadeAtual, "id");//4.25. Modelando e implementando a atualização de recursos com PUT - 9' - O parâmetro "id" será ignorado no copyProperties
    		
    		return cidadeService.salvar(cidadeAtual);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
    }
    */
        
    /*
	@PutMapping("/{cidadeId}")
	public ResponseEntity<Cidade> atualizar(@PathVariable Long cidadeId, @RequestBody Cidade cidade) {		
		Optional<Cidade> cidadeAtual = cidadeRepository.findById(cidadeId);
		
		if (cidadeAtual.isPresent()) {
			BeanUtils.copyProperties(cidade, cidadeAtual.get(), "id");//4.25. Modelando e implementando a atualização de recursos com PUT - 9' - O parâmetro "id" será ignorado no copyProperties
			Cidade cidadeSalva = cidadeService.salvar(cidadeAtual.get());
			return ResponseEntity.ok(cidadeSalva);
		}
		return ResponseEntity.notFound().build();
	}
	*/
    
    
    
    
    
	@ApiOperation("Exclui uma cidade por ID")
	@ApiResponses({ //18.16. Descrevendo códigos de status de respostas em endpoints específicos - 6'30"
		@ApiResponse(code = 204, message = "Cidade excluída"),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
    @DeleteMapping("/{cidadeId}") //8.6. Desafio: refatorando os serviços REST
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(
    		@ApiParam(value = "ID de uma cidade", example = "1")
    		@PathVariable Long cidadeId) {
    	cidadeService.excluir(cidadeId);
    }    
    
    /*
	@DeleteMapping("/{cidadeId}")
	public ResponseEntity<?> remover(@PathVariable Long cidadeId) {		
		try {
			cidadeService.excluir(cidadeId);
			return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
    */    
}
