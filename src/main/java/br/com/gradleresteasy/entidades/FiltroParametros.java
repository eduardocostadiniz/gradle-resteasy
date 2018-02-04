package br.com.gradleresteasy.entidades;


import java.sql.Timestamp;

import javax.ws.rs.QueryParam;

public class FiltroParametros {

	@QueryParam("dataInicio")
	private Timestamp dataInicio;
	
	@QueryParam("dataFinal")
	private Timestamp dataFim;
	
	@QueryParam("nome")
	private String nome;
	
	@QueryParam("idade")
	private Integer idade;

	public FiltroParametros() {
	}

	public FiltroParametros(Timestamp dataInicio, Timestamp dataFim, String nome, Integer idade) {
		super();
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.nome = nome;
		this.idade = idade;
	}

	public Timestamp getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Timestamp dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Timestamp getDataFim() {
		return dataFim;
	}

	public void setDataFim(Timestamp dataFim) {
		this.dataFim = dataFim;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	@Override
	public String toString() {
		return "FiltroParametros [dataInicio=" + dataInicio + ", dataFim=" + dataFim + ", nome=" + nome + ", idade="
				+ idade + "]";
	}

}
