package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.algaworks.algafood.core.validation.Groups;
import com.algaworks.algafood.core.validation.Multiplo;
import com.algaworks.algafood.core.validation.ValorZeroIncluiDescricao;

import lombok.Data;
import lombok.EqualsAndHashCode;

@ValorZeroIncluiDescricao(valorField = "taxaFrete", descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")  //9.17. Criando constraints de validação customizadas em nível de classe - 3'50"
@Data //3.15. Conhecendo e usando o Lombok - 9'
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//@NotBlank //9.5. Conhecendo e adicionando mais constraints de validação no modelo - 5'
	//@NotEmpty //9.5. Conhecendo e adicionando mais constraints de validação no modelo - 3'40"
	//@NotNull //9.2. Adicionando constraints e validando no controller com @Valid - 2'30"
	//@NotBlank(groups = Groups.CadastroRestaurante.class)  //9.7. Agrupando e restringindo constraints que devem ser usadas na validação - 5'
	//@NotBlank //está sendo validado no RestauranteInput
	@Column(nullable = false)
	private String nome;
	
	//@PositiveOrZero(groups = Groups.CadastroRestaurante.class) //9.5. Conhecendo e adicionando mais constraints de validação no modelo - 2' | 9.7. Agrupando e restringindo constraints que devem ser usadas na validação - 5'
	//@DecimalMin("1") //9.5. Conhecendo e adicionando mais constraints de validação no modelo - 30"
	//@NotNull //está sendo validado no RestauranteInput
	//@PositiveOrZero(message = "{TaxaFrete.invalida}") //9.14. Usando o Resource Bundle do Spring como Resource Bundle do Bean Validation - 6'40"
	//@PositiveOrZero //está sendo validado no RestauranteInput
	//@Multiplo(numero = 5) *** precisei comentar essa anotação pq dava erro na aula 12.18. Implementando ativação e inativação em massa de restaurantes aos 6'30", depois estudar porque deu o erro de constraint
	//@TaxaFrete
	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;

	//@JsonIgnoreProperties(value = "nome", allowGetters = true) //11.2. Refinando o payload de cadastro com @JsonIgnoreProperties - 4'10", 5'10", 6'30" - para passar mais de 1 propriedade --> @JsonIgnoreProperties({ "propriedade1", "propriedade2" }) | anotação movida para a classe RestauranteMixin.java - 11.3. Criando classes de mixin para usar as anotações do Jackson - 3'40"
	@ConvertGroup(from = Default.class, to = Groups.CozinhaId.class) //9.8. Convertendo grupos de constraints para validação em cascata com @ConvertGroup - 3'40"
	@Valid //***OBS: O Bean Validation não valida o @NotNull em cascata, ou seja, não vai validar o id nulo da entidade cozinha, para validar em cascata, tem que usar o @Valid - 9.6. Validando as associações de uma entidade em cascata - 3'20"
	//@NotNull(groups = Groups.CadastroRestaurante.class)  //9.7. Agrupando e restringindo constraints que devem ser usadas na validação - 5'
	//@NotNull //está sendo validado no RestauranteInput
	//@JsonIgnore
	//@JsonIgnoreProperties("hibernateLazyInitializer") //6.12. Alterando a estratégia de fetching para Lazy Loading 13'10" - ignora a propriedade do proxy da cozinha que foi criada em tempo de execução
	@ManyToOne
	@JoinColumn(name = "cozinha_id", nullable = false) //6.10. Entendendo o Eager Loading - 13' - Explicação sobre a consulta gerar um inner join com cozinha
	private Cozinha cozinha;
	
	//@JsonIgnore //6.5. Testando e analisando o impacto da incorporação de classe na REST API - 5' | anotação movida para a classe RestauranteMixin.java - 11.3. Criando classes de mixin para usar as anotações do Jackson - 3'40"
	@Embedded //6.4. Mapeando classes incorporáveis com @Embedded e @Embeddable - 6'50"
	private Endereco endereco;
	
	private Boolean ativo = Boolean.TRUE; //12.4. Implementando os endpoints de ativação e inativação de restaurantes - 5'30"
	
	private Boolean aberto = Boolean.FALSE; //12.14. Desafio: Implementando os endpoints de abertura e fechamento de restaurantes
	
	//@JsonIgnore anotação movida para a classe RestauranteMixin.java - 11.3. Criando classes de mixin para usar as anotações do Jackson - 3'40"
	@CreationTimestamp //6.6. Mapeando propriedades com @CreationTimestamp e @UpdateTimestamp - 3', 8'40", 11'50" - essa anotação atribui uma data/hora para a propriedade dataCadastro quando ela for salva pela primeira vez 
	@Column(nullable = false, columnDefinition = "datetime")
	//private LocalDateTime dataCadastro; //6.6. Mapeando propriedades com @CreationTimestamp e @UpdateTimestamp - 1'
	private OffsetDateTime dataCadastro; //11.7. Configurando e refatorando o projeto para usar UTC - 6'20"
		
	//@JsonIgnore anotação movida para a classe RestauranteMixin.java - 11.3. Criando classes de mixin para usar as anotações do Jackson - 3'40"
	@UpdateTimestamp //6.6. Mapeando propriedades com @CreationTimestamp e @UpdateTimestamp - 4'20"
	@Column(nullable = false, columnDefinition = "datetime")
	//private LocalDateTime dataAtualizacao;
	private OffsetDateTime dataAtualizacao; //11.7. Configurando e refatorando o projeto para usar UTC - 6'20"
	
	//@JsonIgnore //6.3. Analisando o impacto do relacionamento muitos-para-muitos na REST API - 4'50" | anotação movida para a classe RestauranteMixin.java - 11.3. Criando classes de mixin para usar as anotações do Jackson - 3'40"	
	@ManyToMany //6.2. Mapeando relacionamento muitos-para-muitos com @ManyToMany - 4'50", 6'20", 6'50"
	//@ManyToMany(fetch = FetchType.EAGER) aula 6.14
	@JoinTable(name = "restaurante_forma_pagamento", 
		joinColumns = @JoinColumn(name = "restaurante_id"),
		inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
	//private List<FormaPagamento> formasPagamento = new ArrayList<>();
	private Set<FormaPagamento> formasPagamento = new HashSet<>();//12.12. Implementando os endpoints de associação de formas de pagamento em restaurantes - 19'
	
	//@JsonIgnore anotação movida para a classe RestauranteMixin.java - 11.3. Criando classes de mixin para usar as anotações do Jackson - 3'40"
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos =  new ArrayList<>(); //6.8. Desafio: mapeando relacionamento um-para-muitos
	
	//12.17. Desafio: implementando endpoints de associação de usuários responsáveis com restaurantes
	@ManyToMany
	@JoinTable(name = "restaurante_usuario_responsavel",
	        joinColumns = @JoinColumn(name = "restaurante_id"),
	        inverseJoinColumns = @JoinColumn(name = "usuario_id"))
	private Set<Usuario> responsaveis = new HashSet<>();
	
	//12.4. Implementando os endpoints de ativação e inativação de restaurantes - 10'25"
	public void ativar() {
		setAtivo(true);
	}
	
	public void inativar() {
		setAtivo(false);
	}
	
	public void abrir() {
	    setAberto(true);
	}

	public void fechar() {
	    setAberto(false);
	} 
	
	//12.12. Implementando os endpoints de associação de formas de pagamento em restaurantes - 11', 16'20"
	public boolean removerFormaPagamento(FormaPagamento formaPagamento) {
		return getFormasPagamento().remove(formaPagamento);
	}
	
	public boolean adicionarFormaPagamento(FormaPagamento formaPagamento) {
		return getFormasPagamento().add(formaPagamento);
	}
	
	//12.17. Desafio: implementando endpoints de associação de usuários responsáveis com restaurantes
	public boolean removerResponsavel(Usuario usuario) {
	    return getResponsaveis().remove(usuario);
	}

	public boolean adicionarResponsavel(Usuario usuario) {
	    return getResponsaveis().add(usuario);
	}
	
	//12.19. Desafio: Implementando os endpoints de consulta de pedidos
	public boolean aceitaFormaPagamento(FormaPagamento formaPagamento) {
	    return getFormasPagamento().contains(formaPagamento);
	}

	public boolean naoAceitaFormaPagamento(FormaPagamento formaPagamento) {
	    return !aceitaFormaPagamento(formaPagamento);
	}
}


//*** por padrão, tudo que é toMany é lazy e toOne é Eager