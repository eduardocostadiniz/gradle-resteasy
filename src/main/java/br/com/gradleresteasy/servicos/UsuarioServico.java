package br.com.gradleresteasy.servicos;

import java.io.IOException;
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
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;

import br.com.gradleresteasy.entidades.FiltroParametros;
import br.com.gradleresteasy.entidades.Usuario;
import br.com.gradleresteasy.utils.ExportacaoUtils;

@Consumes(MediaType.APPLICATION_JSON)
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
	
	@GET
	@Path("/consultar")
	public List<Usuario> listarUser(@BeanParam FiltroParametros filtroParametros) {
		
		LOGGER.log(Level.INFO, "Consultando os usuarios!!!");
		LOGGER.log(Level.INFO, filtroParametros.toString());
		LOGGER.log(Level.INFO, "");
		
		List<Usuario> usuarios = new ArrayList<>();

		Usuario u1 = new Usuario(1, "Usuario 1", "Email 1", "Senha 1");
		Usuario u2 = new Usuario(2, "Usuario 2", "Email 2", "Senha 2");
		Usuario u3 = new Usuario(3, "Usuario 3", "Email 3", "Senha 3");

		usuarios.add(u1);
		usuarios.add(u2);
		usuarios.add(u3);
		
		return usuarios;		
	}
	

	@POST
	@Path("/exportacao")
	public Response exportar(FiltroParametros filtroParametros) throws IOException {

		LOGGER.log(Level.INFO, "Gerando relatório XLS!!!");
		LOGGER.log(Level.INFO, filtroParametros.toString());
		LOGGER.log(Level.INFO, "");
		
		List<Usuario> usuarios = new ArrayList<>();

		Usuario u1 = new Usuario(1, "Usuario 1", "Email 1", "Senha 1");
		Usuario u2 = new Usuario(2, "Usuario 2", "Email 2", "Senha 2");
		Usuario u3 = new Usuario(3, "Usuario 3", "Email 3", "Senha 3");

		usuarios.add(u1);
		usuarios.add(u2);
		usuarios.add(u3);

		final String[] cabecalhos = { "ID", "NOME", "EMAIL", "SENHA" };

		ExportacaoUtils exportar = new ExportacaoUtils(gerarListaDeStrings(usuarios), cabecalhos, "ExportacaoUsuarios");
		
		return exportar.gerarRelatorioXls();
		
	}

	public static List<String[]> gerarListaDeStrings(List<Usuario> lista) {
		List<String[]> listaTemp = new ArrayList<>();

		for (Usuario usuario : lista) {
			String[] string = { usuario.getId().toString(), usuario.getNome(), usuario.getEmail(), usuario.getSenha() };

			listaTemp.add(string);
		}

		return listaTemp;
	}
	
}
