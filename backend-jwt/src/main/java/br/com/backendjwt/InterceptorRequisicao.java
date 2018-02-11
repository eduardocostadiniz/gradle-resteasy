package br.com.backendjwt;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.logging.Logger;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import org.jboss.resteasy.core.ResourceMethodInvoker;

@Provider
public class InterceptorRequisicao implements ContainerRequestFilter, ContainerResponseFilter {

    private static final Logger LOGGER = Logger.getLogger(InterceptorRequisicao.class.getName());

    @Context
    private HttpServletRequest servletRequest;

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
            throws IOException {

        LOGGER.info("Filtro Container Request");

    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        LOGGER.info("Filtro Container Request e Response");

        ResourceMethodInvoker methodInvoker =
                (ResourceMethodInvoker) requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
        Method method = methodInvoker.getMethod();

        if (method.isAnnotationPresent(PermitAll.class)) {
            return;
        }

        if (method.isAnnotationPresent(DenyAll.class)) {
            Boolean forbidden = true;
            LOGGER.warning("Tentativa de acesso ao metodo " + method.getName() + " sem permissão de acesso!");
            throw new AutorizacaoException(
                    "Você não tem permissão de acesso à esse recurso.",
                    Constantes.PERMISSAO_ACESSO,
                    forbidden);
        }

        String tokenAutorizacao = requestContext.getHeaderString("Authorization");

        if (tokenAutorizacao == null || "".equals(tokenAutorizacao)) {
            throw new AutorizacaoException("Header de autorizacao não encontrado", Constantes.TOKEN_AUSENTE, false);
        }

    }

}
