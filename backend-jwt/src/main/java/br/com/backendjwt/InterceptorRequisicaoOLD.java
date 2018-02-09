package br.com.backendjwt;
//package br.com.backendjwt;
//
//import java.io.IOException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import io.jsonwebtoken.SignatureException;
//
//@WebFilter(urlPatterns = {"/*"})
//public class InterceptorRequisicao implements Filter {
//
//    private static final Logger LOGGER = Logger.getLogger(InterceptorRequisicao.class.getName());
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {}
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
//            throws IOException, ServletException {
//
//        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
//        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
//
//        String token = httpRequest.getHeader("Authorization");
//        String uriRequisicao = httpRequest.getRequestURI();
//        String metodo = httpRequest.getMethod();
//
//        if (metodo.equalsIgnoreCase("OPTIONS") || uriRequisicao.contains("logar")) {
//            chain.doFilter(httpRequest, httpResponse);
//        } else {
//
//            LOGGER.info("Token Recebido = \n" + token);
//
//            try {
//                // verificar se existe o token
//                if (token == null || "".equals(token)) {
//                    throw new AutenticacaoException(
//                            "Header de autorizacao não encontrado",
//                            Constantes.TOKEN_AUSENTE,
//                            false);
//                }
//
//                // extrair o valor do token
//                token = TokenUtils.extrairTokenRequisicao(token);
//
//                // verificar o tempo de vida do token
//                TokenUtils.validarTempoVidaToken(token);
//
//            } catch (SignatureException se) {
//                LOGGER.log(Level.SEVERE, se.getMessage());
//                httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            } catch (AplicacaoException ex) {
//                LOGGER.log(Level.SEVERE, ex.getMessage());
//                httpResponse.getWriter()
//                .write(ex.getMessage());
//                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            }
//
//            chain.doFilter(httpRequest, httpResponse);
//        }
//    }
//
//    @Override
//    public void destroy() {}
//
//}
