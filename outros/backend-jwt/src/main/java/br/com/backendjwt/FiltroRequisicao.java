package br.com.backendjwt;

import java.io.IOException;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

@WebFilter(urlPatterns = { "/*" })
public class FiltroRequisicao implements Filter {

	private static final Logger LOGGER = Logger.getLogger(FiltroRequisicao.class.getName());

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

		Enumeration<String> headers = httpRequest.getHeaderNames();

		String h1 = httpRequest.getHeader("Access-Control-Request-Method");
		String h2 = httpRequest.getHeader("Access-Control-Request-Headers");

		String h3 = httpRequest.getHeader("Origin");

		String h4 = httpRequest.getHeader("Accept");

		String h5 = httpRequest.getHeader("User-Agent");

		String token = httpRequest.getHeader("Authorization");
		String uriRequisicao = httpRequest.getRequestURI();
		String urlRequisicao = httpRequest.getRequestURL().toString();
		String metodo = httpRequest.getMethod();

		if (metodo.equalsIgnoreCase("OPTIONS") || uriRequisicao.contains("logar")) {
			chain.doFilter(httpRequest, httpResponse);
		} else {

			LOGGER.info("Token Recebido = \n" + token);

			if (token == null || token.equals("")) {
				try {
					throw new Exception("Header de autorizacao não encontrado");
				} catch (Exception ex) {
					LOGGER.log(Level.SEVERE, ex.getMessage());
				}
			}

			try {
				String tokenExtraido = token.replace("Bearer ", "");
				new TokenUtils().validarTokenUsuario(tokenExtraido);
				LOGGER.info("Autenticação retornou SUCESSO");

			} catch (SignatureException se) {
				LOGGER.log(Level.SEVERE, se.getMessage());
			} catch (Exception ex) {
				LOGGER.log(Level.SEVERE, ex.getMessage());
			}

			// verificar se existe o token

			// verificar o tempo de vida do token

			// verificar se eh pagina de login

			chain.doFilter(httpRequest, httpResponse);
		}
	}

	@Override
	public void destroy() {
	}

}
