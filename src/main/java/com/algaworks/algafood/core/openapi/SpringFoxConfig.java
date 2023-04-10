package com.algaworks.algafood.core.openapi;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

//18.3. Gerando a definição OpenAPI em JSON com SpringFox - 4'50"
//***Novas configurações no conteúdo de apoio da aula

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)  //18.11. Descrevendo restrições de validação de propriedades do modelo - 3'20"
public class SpringFoxConfig {

	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.OAS_30)
				.select()
//					.apis(RequestHandlerSelectors.any())
				.apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api")) //18.5. Selecionando os endpoints da API para gerar a documentação
				.paths(PathSelectors.any())
//				.paths(PathSelectors.ant("/restaurantes/*"))
				.build()
				.useDefaultResponseMessages(false) //18.12. Descrevendo códigos de status de respostas de forma global - 2'20"
				.globalResponses(HttpMethod.GET, globalGetResponseMessages()) //18.12. Descrevendo códigos de status de respostas de forma global - 3'30", OBS: ver o conteúdo de apoio
	            .globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
	            .globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
	            .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())				
				.apiInfo(apiInfo())//18.6. Descrevendo informações da API na documentação - 2'20"
				.tags(new Tag("Cidades", "Gerência de cidades")); //18.7. Descrevendo tags na documentação e associando com controllers
	}
	
	//18.12. Descrevendo códigos de status de respostas de forma global - 4'50", OBS: ver o conteúdo de apoio
	private List<Response> globalGetResponseMessages() {
		  return Arrays.asList(	  
		      new ResponseBuilder()
		          .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
		          .description("Erro interno do Servidor")
		          .build(),
		      new ResponseBuilder()
		          .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
		          .description("Recurso não possui representação que pode ser aceita pelo consumidor")
		          .build()
		  );
	}
	
	  private List<Response> globalPostPutResponseMessages() {
		    return Arrays.asList(
		        new ResponseBuilder()
		            .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
		            .description("Requisição inválida (erro do cliente)")
		            .build(),
		        new ResponseBuilder()
		            .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
		            .description("Erro interno no servidor")
		            .build(),
		        new ResponseBuilder()
		            .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
		            .description("Recurso não possui representação que poderia ser aceita pelo consumidor")
		            .build(),
		        new ResponseBuilder()
		            .code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
		            .description("Requisição recusada porque o corpo está em um formato não suportado")
		            .build()
		    );
	  }
	  
	  //18.13. Desafio: descrevendo códigos de status de respostas de forma global
	  private List<Response> globalDeleteResponseMessages() {
		    return Arrays.asList(
		        new ResponseBuilder()
		            .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
		            .description("Requisição inválida (erro do cliente)")
		            .build(),
		        new ResponseBuilder()
		            .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
		            .description("Erro interno no servidor")
		            .build()
		    );
	  }
	
	//18.6. Descrevendo informações da API na documentação
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("AlgaFood API")
				.description("API aberta para clientes e restaurantes")
				.version("1")
				.contact(new Contact("AlgaWorks", "https://www.algaworks.com", "contato@algaworks.com"))
				.build();
	}
	
}
