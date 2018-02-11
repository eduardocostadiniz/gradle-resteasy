package br.com.gradleresteasy.utils;

import java.math.BigDecimal;
import java.util.List;

/**
 * Classe responsável por manipular a paginacao dos dados do menu financeiro.
 * Foi criado essa classe devido a solucao que o projeto tem nao eh a adequada
 * para o cenario de listagem de informacao que vem de uma API externa e a mesma
 * nao faz o controle de paginacao
 *
 * @param <T>
 *            classe que a listagem pertence
 * @param <FT>
 *            classe que o totalizador de registros pertence
 */
public class PaginacaoDadosFinanceiro<T, FT> {

	private List<T> registros;
	private int paginaAtual;
	private int tamanhoPagina;
	private boolean temProximo;
	private FT totalizador;

	public PaginacaoDadosFinanceiro() {
		super();
	}

	/**
	 * Construtor da classe com todos os atributos
	 *
	 * @param registros
	 *            lista de registros
	 * @param paginaAtual
	 *            pagina atual da requisicao
	 * @param tamanhoPagina
	 *            quantidade de registros por pagina
	 * @param temProximo
	 *            verificador de existencia de proxima pagina
	 * @param totalizador
	 *            totalizador dos registros
	 */
	public PaginacaoDadosFinanceiro(List<T> registros, int paginaAtual, int tamanhoPagina, boolean temProximo,
			FT totalizador) {
		super();
		this.registros = registros;
		this.paginaAtual = paginaAtual;
		this.tamanhoPagina = tamanhoPagina;
		this.temProximo = temProximo;
		this.totalizador = totalizador;
	}

	public List<T> getRegistros() {
		return registros;
	}

	public void setRegistros(List<T> registros) {
		this.registros = registros;
	}

	public int getPaginaAtual() {
		return paginaAtual;
	}

	public void setPaginaAtual(int paginaAtual) {
		this.paginaAtual = paginaAtual;
	}

	public int getTamanhoPagina() {
		return tamanhoPagina;
	}

	public void setTamanhoPagina(int tamanhoPagina) {
		this.tamanhoPagina = tamanhoPagina;
	}

	public boolean isTemProximo() {
		return temProximo;
	}

	public void setTemProximo(boolean temProximo) {
		this.temProximo = temProximo;
	}

	public FT getTotalizador() {
		return totalizador;
	}

	public void setTotalizador(FT totalizador) {
		this.totalizador = totalizador;
	}

	@Override
	public String toString() {
		return "Paginacao [registros=" + registros + ", paginaAtual=" + paginaAtual + ", tamanhoPagina=" + tamanhoPagina
				+ ", temProximo=" + temProximo + ", totalizador=" + totalizador + "]";
	}

	/**
	 * Método responsável por gerenciar a paginacao de dados dinamicamente,
	 * controlando o numero da pagina e o tamanho de cada pagina, verificando se
	 * existe mais registros na proxima pagina
	 *
	 * @param lista
	 *            lista de registros que sera paginada
	 * @param paginaAtual
	 *            a pagina atual que foi passada para o backend
	 * @param tamanhoPagina
	 *            a quantidade de registros que cada pagina deve conter
	 */
	public void configurarPaginacao(List<T> lista, int paginaAtual, int tamanhoPagina) {

		BigDecimal calcularPaginas = new BigDecimal(lista.size());

		int totalPaginas = calcularPaginas.divide(new BigDecimal(tamanhoPagina), 0, BigDecimal.ROUND_UP).intValue();

		int indiceInicial = paginaAtual * tamanhoPagina;
		int indiceFinal = lista.size();
		boolean temProximo = false;

		if (totalPaginas > paginaAtual + 1) {
			temProximo = true;
			indiceFinal = indiceInicial + tamanhoPagina;
		}

		setRegistros(lista.subList(indiceInicial, indiceFinal));
		setPaginaAtual(paginaAtual);
		setTamanhoPagina(tamanhoPagina);
		setTemProximo(temProximo);

	}

}
