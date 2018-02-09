package br.com.backendjwt;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.jboss.resteasy.spi.DefaultOptionsMethodException;

/**
 * Tratador de exceções do ivendas. Responsável por converter as exceptions não tratadas em
 * respostas adequadas para a api rest.
 */
@Provider
public class ErroValidacaoResponse implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable throwable) {
        /**
         * O resteasy dispara uma exceção quando uma solicitação OPTIONS é realizada, mas o retorno
         * ao navegador deve ser transparente
         */
        if (throwable instanceof DefaultOptionsMethodException) {
            return Response.ok()
                    .build();
        }

        if (throwable instanceof WebApplicationException) {
            return ((WebApplicationException) throwable).getResponse();
        }

        int statusCode = 0;

        ErroResponseObjeto objetoErro = new ErroResponseObjeto();

        if (throwable instanceof AplicacaoException) {
            AplicacaoException appException = (AplicacaoException) throwable;
            objetoErro.setMensagem(appException.getMessage());
            objetoErro.setTipo(appException.getTipo());
            objetoErro.setStatus("falha");

            statusCode = appException.statusCode();
        } else {
            objetoErro.setMensagem(throwable.getMessage());
            objetoErro.setTipo(Constantes.DESCONHECIDO);
            objetoErro.setStatus("falha");

            statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }

        return Response.status(statusCode)
                .type(MediaType.APPLICATION_JSON)
                .entity(objetoErro)
                .build();
    }

}
