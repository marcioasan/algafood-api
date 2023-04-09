package com.algaworks.algafood.core.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
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
				.apiInfo(apiInfo())//18.6. Descrevendo informações da API na documentação - 2'20"
				.tags(new Tag("Cidades", "Gerência de cidades")); //18.7. Descrevendo tags na documentação e associando com controllers
	}
	
	//18.6. Descrevendo informações da API na documentação
	public ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("AlgaFood API")
				.description("API aberta para clientes e restaurantes")
				.version("1")
				.contact(new Contact("AlgaWorks", "https://www.algaworks.com", "contato@algaworks.com"))
				.build();
	}
	
}
