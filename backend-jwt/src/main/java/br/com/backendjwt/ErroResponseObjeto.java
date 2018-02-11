package br.com.backendjwt;

public class ErroResponseObjeto {

    private String mensagem;
    private String tipo;
    private String status;

    public ErroResponseObjeto() {
        super();
    }

    public ErroResponseObjeto(String mensagem, String tipo, String status) {
        super();
        this.mensagem = mensagem;
        this.tipo = tipo;
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ErroResponseObjeto [mensagem=" + mensagem + ", tipo=" + tipo + ", status=" + status + "]";
    }

}
