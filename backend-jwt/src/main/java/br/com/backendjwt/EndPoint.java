package br.com.backendjwt;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/endpoint")
public class EndPoint {

    private static final Logger LOGGER = Logger.getLogger(EndPoint.class.getName());

    private static void validarUsuario(Usuario usuario) {
        if (usuario.getUsuario() == null || usuario.getSenha() == null) {
            LOGGER.log(Level.SEVERE, "Credenciais do usuário não informadas");
            throw new ValidacaoException("Credenciais do usuário não informadas", Constantes.CREDENCIAIS_AUSENTE);
        }

        if (!usuario.getUsuario()
                .equals("eduardoc")
                || !usuario.getSenha()
                .equals("123")) {
            LOGGER.log(Level.SEVERE, "Credenciais do usuário inválidas");
            throw new ValidacaoException("Credenciais do usuário inválidas", Constantes.CREDENCIAIS_INVALIDAS);
        }
        LOGGER.info("Validação OK");
    }

    @POST
    @Path("/logar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public String testar(Usuario usuario) {
        LOGGER.info("Logando usuário...");
        validarUsuario(usuario);

        return TokenUtils.obterTokenUsuario(usuario);
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
