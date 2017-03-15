package br.com.gradleresteasy.entidades;

import br.com.gradleresteasy.utils.Modelo;

public class Usuario implements Modelo<Integer> {

	private Integer id;

	private String nome;

	private String email;

	private String senha;

	/**
	 * Construtor da classe
	 */
	public Usuario() {
		super();
	}

	/**
	 * Construtor da classe
	 *
	 * @param id
	 * @param nome
	 * @param email
	 * @param senha
	 */
	public Usuario(Integer id, String nome, String email, String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
	}

	@Override
	public Integer getId() {
		return this.id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Retorna o campo nome
	 *
	 * @return Retorna o campo nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Seta o parametro nome para o campo nome
	 *
	 * @param nome
	 *            Parametro para setar no valor nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Retorna o campo email
	 *
	 * @return Retorna o campo email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Seta o parametro email para o campo email
	 *
	 * @param email
	 *            Parametro para setar no valor email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Retorna o campo senha
	 *
	 * @return Retorna o campo senha
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * Seta o parametro senha para o campo senha
	 *
	 * @param senha
	 *            Parametro para setar no valor senha
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}

}
