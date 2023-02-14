package com.algaworks.algafood.infrastructure.service.report;

//13.20. Preenchendo um relatório JasperReports com JavaBeans e gerando bytes do PDF - 12'10"

public class ReportException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ReportException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReportException(String message) {
		super(message);
	}
}
