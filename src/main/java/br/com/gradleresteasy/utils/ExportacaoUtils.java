package br.com.gradleresteasy.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * @author eduardoc
 *
 * Classe responsável por gerar arquivos xls e exportar para o usuario 
 */
public class ExportacaoUtils {

	private static final String NOME_PADRAO_PLANILHA = "RelatorioExportacao";
	private static final String NOME_PADRAO_FOLHA = "Folha Principal";
	private static final String EXTENSAO_EXCEL = ".xls";
	private static final short INDICE_LINHA_CABECALHO = 0;
	private static final short INDICE_LINHA_REGISTROS = 1;
	private static final short INDICE_CELULA_CABECALHO = 0;
	private static final short INDICE_CELULA_REGISTROS = 0;

	private List<String[]> registros;
	private String[] cabecalhos;
	private int[] tamanhoColunas;
	private String nomeRelatorio;
	private String nomeFolha;

	/**
	 * Construtor da classe
	 */
	public ExportacaoUtils(List<String[]> registros, String[] cabecalhos, int[] tamanhoColunas) {
		super();
		this.registros = registros;
		this.cabecalhos = cabecalhos;
		this.tamanhoColunas = tamanhoColunas;
		this.nomeRelatorio = NOME_PADRAO_PLANILHA + EXTENSAO_EXCEL;
		this.nomeFolha = NOME_PADRAO_FOLHA;
	}

	public ExportacaoUtils(List<String[]> registros, String[] cabecalhos, int[] tamanhoColunas, String nomeRelatorio) {
		super();
		this.registros = registros;
		this.cabecalhos = cabecalhos;
		this.tamanhoColunas = tamanhoColunas;
		this.nomeRelatorio = nomeRelatorio + EXTENSAO_EXCEL;
		this.nomeFolha = NOME_PADRAO_FOLHA;
	}

	public ExportacaoUtils(List<String[]> registros, String[] cabecalhos, int[] tamanhoColunas, String nomeRelatorio,
			String nomeFolha) {
		super();
		this.registros = registros;
		this.cabecalhos = cabecalhos;
		this.tamanhoColunas = tamanhoColunas;
		this.nomeRelatorio = nomeRelatorio + EXTENSAO_EXCEL;
		this.nomeFolha = nomeFolha;
	}

	/**
	 * Método responsável por construir a estrutura da planilha, inserir os
	 * dados e retornar para o usuario o arquivo excel
	 *
	 * @return arquivo da planilha excel
	 * @throws IOException
	 *             uma excecao eh lancada caso alguma problema aconteca
	 */
	public Response gerarRelatorioXls() throws IOException {

		// Criar a pasta de trabalho e a folha principal
		HSSFWorkbook workbookExcel = new HSSFWorkbook();
		HSSFSheet folhaPrincipal = workbookExcel.createSheet(nomeFolha);
		ByteArrayOutputStream baos = null;

		try {
			// inserir cabecalho planilha
			inserirCabecalhoPlanilha(folhaPrincipal);

			// inserir os registros na planilha
			inserirRegistrosPlanilha(folhaPrincipal);

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
			baos.close();
		}

	}

	/**
	 * Método responsável por inserir os nomes das colunas na planilha
	 *
	 * @param folhaParam
	 *            folha da planilha que ira receber o nome das colunas
	 */
	private void inserirCabecalhoPlanilha(HSSFSheet folhaParam) {
		Row linhaCabecalho = folhaParam.createRow(INDICE_LINHA_CABECALHO);

		int contadorCelulaCabecalho = INDICE_CELULA_CABECALHO;

		for (String nomeCabecalho : cabecalhos) {
			Cell celula = linhaCabecalho.createCell(contadorCelulaCabecalho++);
			celula.setCellValue(nomeCabecalho);
		}
	}

	/**
	 * Método responsável por inserir os registros na planilha
	 *
	 * @param folhaParam
	 *            folha da planilha que ira receber os registros
	 */
	public void inserirRegistrosPlanilha(HSSFSheet folhaParam) {

		int contadorLinhaRegistros = INDICE_LINHA_REGISTROS;

		for (int i = 0; i < registros.size(); i++) {
			Row linhaRegistros = folhaParam.createRow(contadorLinhaRegistros++);
			int contadorCelulaRegistros = INDICE_CELULA_REGISTROS;

			for (String valorCampo : registros.get(i)) {
				Cell celulaLinha = linhaRegistros.createCell(contadorCelulaRegistros++);
				celulaLinha.setCellValue(valorCampo);
			}
		}
	}

	/**
	 * Método responsável por setar o tamanho das colunas baseado em uma
	 * constante de tamanhos no modelo
	 *
	 * @param folhaParam
	 *            a folha da planilha que sera configurada o tamanho das colunas
	 */
	public void setarTamanhoColunas(HSSFSheet folhaParam) {

		int contadorCelulaCabecalho = INDICE_CELULA_CABECALHO;

		for (int i = 0; i < cabecalhos.length; i++) {
			folhaParam.setColumnWidth(contadorCelulaCabecalho++, (tamanhoColunas[i] * 256));
		}
	}

}
