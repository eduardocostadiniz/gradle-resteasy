package br.com.gradleresteasy.servicos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;

import br.com.gradleresteasy.entidades.Usuario;

@Produces(MediaType.APPLICATION_JSON)
@Path("/usuario")
public class UsuarioServico implements CrudServico<Usuario> {

	private final static Logger LOGGER = Logger.getLogger(UsuarioServico.class);

	@Override
	public Usuario inserir() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario atualizar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario deletar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@GET
	@Path("/listar")
	public List<Usuario> todos() {
		LOGGER.info("Listando os usuários...");

		List<Usuario> usuarioLista = new ArrayList<>();

		try {
			Context initContext = new InitialContext();
			DataSource datasource = (DataSource) initContext.lookup("java:jboss/apprest");
			Connection conn = datasource.getConnection();

			Statement statement = conn.createStatement();
			String sql = "SELECT * FROM usuario";
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("nome"));
				usuario.setEmail(rs.getString("email"));
				usuario.setSenha(rs.getString("senha"));
				usuarioLista.add(usuario);
			}
		} catch (NamingException ex) {
			System.err.println(ex);
		} catch (SQLException ex) {
			System.err.println(ex);
		}
		// TODO Auto-generated method stub
		return usuarioLista;
	}

	@Override
	@GET
	@Path("/teste")
	public Usuario ativos() {
		return new Usuario(1, "Eduardo Costa Diniz", "teste@gmail.com", "senharoot");
	}

}
