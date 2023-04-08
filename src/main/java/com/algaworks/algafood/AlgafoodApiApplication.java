package com.algaworks.algafood;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.algaworks.algafood.domain.repository.CustomJpaRepository;
import com.algaworks.algafood.infrastructure.repository.CustomJpaRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class) //5.20. Estendendo o JpaRepository para customizar o repositório base - 13'
//@EnableWebMvc //18.3. Gerando a definição OpenAPI em JSON com SpringFox - Conteúdo de apoio - para funcionar o OpenApi 3.0 (Swagger) ou colocar no application.properties a propriedade spring.mvc.pathmatch.matching-strategy=ANT_PATH_MATCHER 
public class AlgafoodApiApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC")); //11.7. Configurando e refatorando o projeto para usar UTC - 17'20"
		SpringApplication.run(AlgafoodApiApplication.class, args);
	}
	
	//*** Para saber quantos beans gerenciados tem na aplicação
	//https://app.algaworks.com/forum/topicos/85840/specification-classe-restaurantespecs-uso-metodos-static
//	public static void main(String[] args) {
//		ApplicationContext applicationContext = SpringApplication.run(AlgafoodApiApplication.class, args);
//		String[] allBeanNames = applicationContext.getBeanDefinitionNames();
//		Arrays.stream(allBeanNames).forEach(System.out::println); //nomes
//
//                System.out.println(allBeanNames.length); //quantidade
//                System.out.println(applicationContext.getBeanDefinitionCount()); // quantidade
//    
//	}	

}
