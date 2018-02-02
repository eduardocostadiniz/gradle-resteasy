package br.com.backendjwt;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.crypto.MacProvider;

@Path("/endpoint")
public class EndPoint {

	private static final Logger LOGGER = Logger.getLogger(EndPoint.class.getName());

	private void validarUsuario(Usuario usuario) throws Exception {
		if (usuario == null) {
			LOGGER.log(Level.SEVERE, "Credenciais do usuário não informadas");
			throw new Exception("Credenciais do usuário não informadas");
		}

		if (!usuario.getUsuario().equals("eduardoc") || !usuario.getSenha().equals("123")) {
			LOGGER.log(Level.SEVERE, "Credenciais do usuário inválidas");
			throw new Exception("Credenciais do usuário inválidas");
		}
		LOGGER.info("Validação OK");
	}

	@POST
	@Path("/logar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String testar(Usuario usuario) throws Exception {
		LOGGER.info("Logando usuário...");

		try {
			validarUsuario(usuario);

			return new TokenUtils().obterTokenUsuario(usuario);

		} catch (SignatureException se) {
			LOGGER.log(Level.SEVERE, "Autenticação retornou FALHA - " + se.getMessage());
		}

		return null;
	}

	@GET
	@Path("/dashboard")
	public List<Dashboard> listarDashboard() {
		List<Dashboard> listaDashboard = new ArrayList<>();

		for (int i = 1; i <= 12; i++) {
			listaDashboard.add(new Dashboard(i));
		}

		return listaDashboard;
	}

}
