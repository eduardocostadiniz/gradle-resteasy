package br.com.backendjwt;

import javax.servlet.http.HttpServletResponse;

public class AutorizacaoException extends AplicacaoException {

    private static final long serialVersionUID = 994983305785155913L;

    private int statusCode;

    private String tipo;

    public AutorizacaoException(String mensagem) {
        super(mensagem);
    }

    public AutorizacaoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

    public AutorizacaoException(String mensagem, String tipo, boolean isForbidden) {
        super(mensagem);
        this.statusCode = selecionarStatusCode(isForbidden);
        this.tipo = tipo;
    }

    public AutorizacaoException(String mensagem, Throwable causa, String tipo, boolean isForbidden) {
        super(mensagem, causa);
        this.statusCode = selecionarStatusCode(isForbidden);
        this.tipo = tipo;
    }

    @Override
    public Integer statusCode() {
        return statusCode;
    }

    @Override
    public String getTipo() {
        return tipo;
    }

    private int selecionarStatusCode(boolean isForbidden) {
        return isForbidden ? HttpServletResponse.SC_FORBIDDEN : HttpServletResponse.SC_UNAUTHORIZED;
    }

}
