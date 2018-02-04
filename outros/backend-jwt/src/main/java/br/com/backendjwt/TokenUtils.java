package br.com.backendjwt;

import java.security.Key;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.crypto.MacProvider;

public class TokenUtils {

	private static final Logger LOGGER = Logger.getLogger(EndPoint.class.getName());

	private static Key key = MacProvider.generateKey();
	private static final long TEMPO_VIDA_TOKEN = (1000 * 60 * 10);

	public String obterTokenUsuario(Usuario usuario) throws SignatureException {
		Date tempoVida = new Date(System.currentTimeMillis() + TEMPO_VIDA_TOKEN);
		LOGGER.info("Tempo de vida até " + tempoVida);

		String jwtUsuario = "";

		try {

			jwtUsuario = Jwts.builder().setSubject(usuario.getUsuario()).setExpiration(tempoVida)
					.setId(usuario.getUsuario()).signWith(SignatureAlgorithm.HS256, key).setHeaderParam("typ", "JWT")
					.compact();

			LOGGER.info(jwtUsuario);

			return jwtUsuario;
		} catch (SignatureException se) {
			LOGGER.log(Level.SEVERE, "Erro ao gerar token: " + se.getMessage());
			throw new SignatureException(se.getMessage());
		}
	}

	public void validarTokenUsuario(String tokenUsuario) throws SignatureException, Exception {
		try {
			Jwts.parser().setSigningKey(key).parseClaimsJws(tokenUsuario);

			validarTempoVidaToken(tokenUsuario);

		} catch (SignatureException se) {
			LOGGER.log(Level.SEVERE, "Erro ao validar o token: " + se.getMessage());
			throw new SignatureException(se.getMessage());
		}
	}

	public void validarTempoVidaToken(String tokenUsuario) throws Exception {
		Date dataValidacao = new Date(System.currentTimeMillis());
		Date dataToken = new Date();

		if (dataValidacao.compareTo(dataToken) < 0) {
			LOGGER.log(Level.SEVERE, "Token do usuario expirado");
			throw new Exception("Token do usuário expirado!");
		}

	}

}
