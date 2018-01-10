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
 * Classe utilitaria para gerar arquivos xls (Excel)
 *
 */
public class ExportacaoUtils {

	private static final String NOME_PADRAO_FOLHA = "Folha Principal";
	private static final String EXTENSAO_EXCEL = ".xls";
	private static final short INDICE_LINHA_CABECALHO = 0;
	private static final short INDICE_LINHA_REGISTROS = 1;
	private static final short INDICE_CELULA_CABECALHO = 0;
	private static final short INDICE_CELULA_REGISTROS = 0;

	private List<String[]> registros;
	private String[] cabecalhos;
	private String nomeRelatorio;
	private String nomeFolha;

	public ExportacaoUtils() {
		super();
	}

	public ExportacaoUtils(List<String[]> registros, String[] cabecalhos, String nomeRelatorio) {
		super();
		this.registros = registros;
		this.cabecalhos = cabecalhos;
		this.nomeRelatorio = nomeRelatorio + EXTENSAO_EXCEL;
		this.nomeFolha = NOME_PADRAO_FOLHA;
	}

	public ExportacaoUtils(List<String[]> registros, String[] cabecalhos, String nomeRelatorio, String nomeFolha) {
		super();
		this.registros = registros;
		this.cabecalhos = cabecalhos;
		this.nomeRelatorio = nomeRelatorio + EXTENSAO_EXCEL;
		this.nomeFolha = nomeFolha;
	}

	public Response gerarRelatorioXls() throws IOException {

		// Criar a pasta de trabalho e a folha principal
		HSSFWorkbook workbookExcel = new HSSFWorkbook();
		HSSFSheet folha = workbookExcel.createSheet(nomeFolha);
		ByteArrayOutputStream baos = null;

		try {
			// inserir cabecalho planilha
			inserirCabecalhoPlanilha(folha);

			// inserir os registros na planilha
			inserirRegistrosPlanilha(folha);
			
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

	private void inserirCabecalhoPlanilha(HSSFSheet folhaParam) {
		Row linhaCabecalho = folhaParam.createRow(INDICE_LINHA_CABECALHO);

		int contadorCelulaCabecalho = INDICE_CELULA_CABECALHO;

		for (String nomeCabecalho : cabecalhos) {
			Cell celula = linhaCabecalho.createCell(contadorCelulaCabecalho++);
			celula.setCellValue(nomeCabecalho);
		}
	}

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

	public void configurarTamanhoColunas() {

	}

}
