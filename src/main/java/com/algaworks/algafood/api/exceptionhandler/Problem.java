package com.algaworks.algafood.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

//8.12. Tratando exceções em nível de controlador com @ExceptionHandler - 7'50", 9'
@JsonInclude(Include.NON_NULL) //8.18. Padronizando o formato de problemas no corpo de respostas com a RFC 7807 - 5'30", só incluirá as propriedades que não forem nulas
@ApiModel("Problema") //18.14. Descrevendo o modelo de representação de problema - 4'
@Getter
@Builder
public class Problem {

	@ApiModelProperty(example = "400", position = 1) //18.14. Descrevendo o modelo de representação de problema - 5', 9'30" - OBS: O parâmetro position não está funcionando no OpenApi 3
	private Integer status; //8.18. Padronizando o formato de problemas no corpo de respostas com a RFC 7807 - 1'
	
	@ApiModelProperty(example = "https://algafood.com.br/dados-invalidos", position = 5)
	private String type;
	
	@ApiModelProperty(example = "Dados inválidos", position = 10)
	private String title;
	
	@ApiModelProperty(example = "Um ou mais dados estão inválidos", position = 15)
	private String detail;
	
	private String userMessage; //8.28. Estendendo o formato do problema para adicionar novas propriedades
	
	//private LocalDateTime timestamp; //8.29. Desafio: estendendo o formato do problema
	@ApiModelProperty(example = "2023-04-10T23:27:03.3433502Z", position = 20) //18.14. Descrevendo o modelo de representação de problema - 6'
	private OffsetDateTime timestamp; //11.8. Desafio: refatorando o código para usar OffsetDateTime
		
	//private List<Field> fields; //9.4. Estendendo o Problem Details para adicionar as propriedades com constraints violadas - 1'
	@ApiModelProperty(example = "Lista de objetos ou campos que geraram o erro (opcional)", position = 25)
	private List<Object> objects; //9.18. Ajustando Exception Handler para adicionar mensagens de validação em nível de classe - 6'30"
	
	@ApiModel("ObjetoProblema") //18.14. Descrevendo o modelo de representação de problema - 4'
	@Getter
	@Builder
	public static class Object {
		
		@ApiModelProperty(example = "preço")
		private String name;
		
		@ApiModelProperty(example = "O preço é obrigatório")
		private String userMessage;
	}
}
