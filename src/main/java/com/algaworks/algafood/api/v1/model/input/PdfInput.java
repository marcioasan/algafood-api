package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafood.core.validation.FileContentType;
import com.algaworks.algafood.core.validation.FileSize;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PdfInput {
	@NotNull
	@FileSize(max = "500KB")
	@FileContentType(allowed = {MediaType.APPLICATION_PDF_VALUE})
	private MultipartFile arquivo;
	
	@NotBlank
	private String descricao;
}