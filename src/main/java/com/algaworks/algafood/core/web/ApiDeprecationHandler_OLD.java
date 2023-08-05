package com.algaworks.algafood.core.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

//20.19. Desligando uma versão da API - renomeada e alterada nessa aula
//20.18. Depreciando uma versão da API - 9'20"

@Component
public class ApiDeprecationHandler_OLD implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (request.getRequestURI().startsWith("/v1/")) {
			response.addHeader("X-AlgaFood-Deprecated",
					"Essa versão da API está depreciada e deixará de existir a partir de 01/01/2021."
							+ "Use a versão mais atual da API.");
		}

		return true;
	}
}