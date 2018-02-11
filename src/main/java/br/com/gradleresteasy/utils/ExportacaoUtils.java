package br.com.gradleresteasy.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Classe responsável por gerar arquivos xls e exportar para o usuario
 * dinamicamente
 *
 */
public class ExportacaoUtils {

	private static final String NOME_PADRAO_RELATORIO = "RelatorioExportacao";
	private static final String NOME_PADRAO_FOLHA = "Folha Principal";
	private static final String EXTENSAO_EXCEL = ".xlsx";
	private static final short INDICE_LINHA_CABECALHO = 0;
	private static final short INDICE_LINHA_REGISTROS = 1;
	private static final short INDICE_CELULA_CABECALHO = 0;
	private static final short INDICE_CELULA_REGISTROS = 0;

	public static final String ALINHAMENTO_ESQUERDA = "ESQUERDA";
	public static final String ALINHAMENTO_CENTRAL = "CENTRO";

	public static final String PADRAO_FORMAT_DT_EXPORT_FINAN = "dd/MM/yyyy";

	private List<String[]> registros;
	private String[] cabecalhos;
	private int[] tamanhoColunas;
	private String nomeRelatorio;
	private String nomeFolha;
	private List<String[]> totalizadores;
	private String[] cabecalhoTotalizadores;
	private String[] alinhamentosCelulasRegistros;
	private String[] alinhamentosCelulasTotalizadores;
	private String[] coresCelulas;

	/**
	 * Construtor da classe
	 */
	public ExportacaoUtils() {
		super();
		this.registros = new ArrayList<>();
		this.cabecalhos = new String[0];
		this.tamanhoColunas = new int[0];
		this.nomeRelatorio = NOME_PADRAO_RELATORIO + EXTENSAO_EXCEL;
		this.nomeFolha = NOME_PADRAO_FOLHA;
		this.totalizadores = new ArrayList<>();
		this.cabecalhoTotalizadores = new String[0];
		this.alinhamentosCelulasRegistros = new String[0];
		this.alinhamentosCelulasTotalizadores = new String[0];
		this.coresCelulas = new String[0];
	}

	/**
	 * Construtor da classe com todos os atributos
	 *
	 * @param registros
	 *            lista de registros
	 * @param cabecalhos
	 *            cabecalho principal da planilha
	 * @param tamanhoColunas
	 *            tamanho de cada coluna na planilha
	 * @param nomeFolha
	 *            nome folha principal da planilha
	 * @param nomeRelatorio
	 *            nome do relatorio que sera exportado
	 * @param totalizadores
	 *            totalizadores dos registros
	 * @param cabecalhoTotalizadores
	 *            cabecalho dos totalizadores dos registros
	 * @param alinhamentosCelulasRegistros
	 *            o alinhamento da celula dos registros (esquerda, direita ou
	 *            central)
	 * @param alinhamentosCelulasTotatlizadores
	 *            o alinhamento da celula dos totalizadores (esquerda, direita ou
	 *            central)
	 * @param coresCelulas
	 *            a cor das celulas definidas
	 */
	public ExportacaoUtils(List<String[]> registros, String[] cabecalhos, int[] tamanhoColunas, String nomeRelatorio,
			String nomeFolha, List<String[]> totalizadores, String[] cabecalhoTotalizadores,
			String[] alinhamentosCelulasRegistros, String[] alinhamentosCelulasTotalizadores, String[] coresCelulas) {
		super();
		this.registros = registros;
		this.cabecalhos = cabecalhos;
		this.tamanhoColunas = tamanhoColunas;
		this.nomeRelatorio = nomeRelatorio;
		this.nomeFolha = nomeFolha;
		this.totalizadores = totalizadores;
		this.cabecalhoTotalizadores = cabecalhoTotalizadores;
		this.alinhamentosCelulasRegistros = alinhamentosCelulasRegistros;
		this.alinhamentosCelulasTotalizadores = alinhamentosCelulasTotalizadores;
		this.coresCelulas = coresCelulas;
	}

	public void setRegistros(List<String[]> registros) {
		this.registros = registros;
	}

	public void setCabecalhos(String[] cabecalhos) {
		this.cabecalhos = cabecalhos;
	}

	public void setTamanhoColunas(int[] tamanhoColunas) {
		this.tamanhoColunas = tamanhoColunas;
	}

	public void setNomeRelatorio(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio + EXTENSAO_EXCEL;
	}

	public void setNomeFolha(String nomeFolha) {
		this.nomeFolha = nomeFolha;
	}

	public void setTotalizadores(List<String[]> totalizadores) {
		this.totalizadores = totalizadores;
	}

	public void setCabecalhoTotalizadores(String[] cabecalhoTotalizadores) {
		this.cabecalhoTotalizadores = cabecalhoTotalizadores;
	}

	public void setAlinhamentosCelulasRegistros(String[] alinhamentosCelulasRegistros) {
		this.alinhamentosCelulasRegistros = alinhamentosCelulasRegistros;
	}

	public void setAlinhamentosCelulasTotalizadores(String[] alinhamentosCelulasTotalizadores) {
		this.alinhamentosCelulasTotalizadores = alinhamentosCelulasTotalizadores;
	}

	public void setCoresCelulas(String[] coresCelulas) {
		this.coresCelulas = coresCelulas;
	}

	/**
	 * Método responsável por construir a estrutura da planilha, inserir os dados e
	 * retornar para o usuario o arquivo excel
	 *
	 * @return arquivo da planilha excel
	 * @throws IOException
	 *             uma excecao eh lancada caso alguma problema aconteca
	 */
	public Response gerarRelatorioXls() throws IOException {

		// Criar a pasta de trabalho e a folha principal
		XSSFWorkbook workbookExcel = new XSSFWorkbook();
		XSSFSheet folhaPrincipal = workbookExcel.createSheet(nomeFolha);
		ByteArrayOutputStream baos = null;
		XSSFCellStyle estiloCelCentralizar = workbookExcel.createCellStyle();

		try {
			// inserir cabecalho planilha
			inserirCabecalhoPlanilha(folhaPrincipal, estiloCelCentralizar);

			// inserir os registros na planilha
			inserirRegistrosPlanilha(folhaPrincipal, estiloCelCentralizar);

			// inserir cabecalho dos totalizadores, se existir algum
			inserirCabecalhosTotalizadores(folhaPrincipal, estiloCelCentralizar);

			// imprime o totalizador se existir algum
			inserirTotalizadores(folhaPrincipal, estiloCelCentralizar);

			// configurar o tamanho das colunas
			setarTamanhoColunas(folhaPrincipal);

			// copia os dados da planilha para o vetor de bytes
			baos = new ByteArrayOutputStream();
			workbookExcel.write(baos);

			// constroi a resposta, colocando o arquivo na resposta
			ResponseBuilder responseok = Response.ok(baos.toByteArray());
			responseok.type("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			responseok.header("Content-Disposition", "attachment; filename=" + nomeRelatorio);

			return responseok.build();
		} catch (IOException e) {
			String msg = e.getMessage();
			return Response.status(Status.BAD_REQUEST).entity(msg.getBytes("UTF-8")).build();
		} finally {
			workbookExcel.close();
			if (baos != null) {
				baos.close();
			}
		}

	}

	/**
	 * Método responsável por inserir os nomes das colunas na planilha
	 *
	 * @param folhaParam
	 *            folha da planilha que ira receber o nome das colunas
	 */
	private void inserirCabecalhoPlanilha(XSSFSheet folhaParam, XSSFCellStyle estiloCelula) {
		XSSFRow linhaCabecalho = folhaParam.createRow(INDICE_LINHA_CABECALHO);

		int contadorCelulaCabecalho = INDICE_CELULA_CABECALHO;

		for (String valorCampo : cabecalhos) {
			XSSFCell celula = linhaCabecalho.createCell(contadorCelulaCabecalho++);
			celula.setCellValue(valorCampo);
			// o cabecalho principal deve sempre ser alinhado no centro
			ajustarAlinhamentoCelula(celula, ExportacaoUtils.ALINHAMENTO_CENTRAL, estiloCelula);
		}
	}

	/**
	 * Método responsável por ajustar o alinhamento de cada celula da planilha excel
	 *
	 * @param workbookExcel
	 *            a pasta de trabalho que sera criado o estilo
	 * @param celula
	 *            a celula da planilha que recebera o estilo
	 * @param alinhamento
	 *            o alinhamento que a celula deve possuir
	 */
	private void ajustarAlinhamentoCelula(XSSFCell celula, String alinhamento, XSSFCellStyle estiloCelula) {

		if (coresCelulas.length > 0) {
			// em analise
		}

		if (alinhamento.equals(ALINHAMENTO_CENTRAL)) {
			estiloCelula.setAlignment(HorizontalAlignment.CENTER);
			celula.setCellStyle(estiloCelula);
		}

	}

	/**
	 * Método responsável por inserir os registros na planilha
	 *
	 * @param folhaParam
	 *            folha da planilha que ira receber os registros
	 */
	public void inserirRegistrosPlanilha(XSSFSheet folhaParam, XSSFCellStyle estiloCelula) {

		int contadorLinhaRegistros = INDICE_LINHA_REGISTROS;

		for (int i = 0; i < registros.size(); i++) {
			XSSFRow linhaRegistros = folhaParam.createRow(contadorLinhaRegistros++);
			int contadorCelulaRegistros = INDICE_CELULA_REGISTROS;

			for (int j = 0; j < registros.get(i).length; j++) {
				XSSFCell celulaLinha = linhaRegistros.createCell(contadorCelulaRegistros++);
				celulaLinha.setCellValue(registros.get(i)[j]);
				if (alinhamentosCelulasRegistros.length > 0) {
					ajustarAlinhamentoCelula(celulaLinha, alinhamentosCelulasRegistros[j], estiloCelula);
				}
			}
		}
	}

	/**
	 * Método responsável por inserir os nomes dos totalizadores nas colunas na
	 * planilha
	 *
	 * @param folhaParam
	 *            folha da planilha que ira receber o nome das colunas
	 */
	public void inserirCabecalhosTotalizadores(XSSFSheet folhaParam, XSSFCellStyle estiloCelula) {

		if (cabecalhoTotalizadores.length == 0) {
			return;
		}

		XSSFRow linhaCabecalhoTotalizadores = folhaParam.createRow(folhaParam.getLastRowNum() + 1);
		int contadorCelulaCabecalho = INDICE_CELULA_CABECALHO;

		for (String nomeCabecalhoTotalizador : cabecalhoTotalizadores) {
			XSSFCell celula = linhaCabecalhoTotalizadores.createCell(contadorCelulaCabecalho++);
			celula.setCellValue(nomeCabecalhoTotalizador);
			// o cabecalho principal deve sempre ser alinhado no centro
			ajustarAlinhamentoCelula(celula, ExportacaoUtils.ALINHAMENTO_CENTRAL, estiloCelula);
		}
	}

	/**
	 * Método responsável por inserir os registros dos totalizadores na planilha
	 *
	 * @param folhaParam
	 *            folha da planilha que ira receber os registros
	 */
	public void inserirTotalizadores(XSSFSheet folhaParam, XSSFCellStyle estiloCelula) {

		if (totalizadores.size() == 0) {
			return;
		}

		int contadorLinhaRegistros = folhaParam.getLastRowNum() + 1;

		for (int i = 0; i < totalizadores.size(); i++) {
			XSSFRow linhaRegistros = folhaParam.createRow(contadorLinhaRegistros++);
			int contadorCelulaRegistros = INDICE_CELULA_REGISTROS;

			for (int j = 0; j < totalizadores.get(i).length; j++) {
				XSSFCell celulaLinha = linhaRegistros.createCell(contadorCelulaRegistros++);
				celulaLinha.setCellValue(totalizadores.get(i)[j]);
				if (alinhamentosCelulasTotalizadores.length > 0) {
					ajustarAlinhamentoCelula(celulaLinha, alinhamentosCelulasTotalizadores[j], estiloCelula);
				}
			}
		}

	}

	/**
	 * Método responsável por setar o tamanho das colunas baseado em uma constante
	 * de tamanhos no modelo
	 *
	 * @param folhaParam
	 *            a folha da planilha que sera configurada o tamanho das colunas
	 */
	private void setarTamanhoColunas(XSSFSheet folhaParam) {

		int contadorCelulaCabecalho = INDICE_CELULA_CABECALHO;

		for (int i = 0; i < cabecalhos.length; i++) {
			folhaParam.setColumnWidth(contadorCelulaCabecalho++, tamanhoColunas[i] * 256);
		}
	}

	/**
	 * Método responsável por formatar o valor decimal para um formato especifico
	 *
	 * @param padraoFormato
	 *            formato que o valor decimal sera formatado
	 * @param valorBigDecimal
	 *            o valor que sera formatado
	 * @return o valor formatado como string
	 */
	public static String formatarBigDecimal(DecimalFormat padraoFormato, BigDecimal valorBigDecimal) {
		if (valorBigDecimal == null) {
			return "";
		}

		try {
			return padraoFormato.format(valorBigDecimal);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * Método responsável por receber uma data em timestamp e formatar para um
	 * padrao de formatacao de datas
	 *
	 * @param formato
	 *            formato que a data deve ser formatada, caso o formato seja nulo ou
	 *            vazio, o padrao de formatacao de datas do financeiro sera usado
	 * @param data
	 *            a data que deve ser formatada pelo padrao
	 * @return a data formatada no padrao fornecido como String
	 */
	public static String formatarTimestampParaData(String formato, Timestamp data) {
		SimpleDateFormat formatoData = new SimpleDateFormat(PADRAO_FORMAT_DT_EXPORT_FINAN);

		if (formato != null && !formato.equals("")) {
			formatoData.applyPattern(formato);
		}

		try {
			return formatoData.format(data);
		} catch (Exception e) {
			return "";
		}
	}

}
