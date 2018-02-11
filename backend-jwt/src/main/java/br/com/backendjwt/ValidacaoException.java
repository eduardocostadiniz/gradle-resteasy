package br.com.backendjwt;

import javax.servlet.http.HttpServletResponse;

public class ValidacaoException extends AplicacaoException {

    private static final long serialVersionUID = 4588853602809749457L;

    private String tipo;

    public ValidacaoException(String mensagem) {
        super(mensagem);
    }

    public ValidacaoException(String mensagem, String tipo) {
        super(mensagem);
        this.tipo = tipo;
    }

    public ValidacaoException(String mensagem, Throwable causa, String tipo) {
        super(mensagem, causa);
        this.tipo = tipo;
    }

    @Override
    public Integer statusCode() {
        return HttpServletResponse.SC_BAD_REQUEST;
    }

    @Override
    public String getTipo() {
        return this.tipo;
    }

}
