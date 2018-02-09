package br.com.backendjwt;

import javax.servlet.http.HttpServletResponse;

public abstract class AplicacaoException extends RuntimeException {

    private static final long serialVersionUID = 7731095955412395259L;

    public abstract String getTipo();

    public AplicacaoException(String mensagem) {
        super(mensagem);
    }

    public AplicacaoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

    public Integer statusCode() {
        return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }

}