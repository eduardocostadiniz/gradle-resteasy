package br.com.gradleresteasy.utils;

import java.io.Serializable;

/**
 * Classe responsável por definir um modelo para as entidades
 *
 * @author eduardoc
 *
 * @param <T>
 *            tipo da classe
 */
public interface Modelo<T extends Serializable> {

	/**
	 * Método responsável por retornar o id da entidade
	 *
	 * @return
	 */
	public T getId();

	/**
	 * Método responsável por setar um valor para o id da entidade
	 *
	 * @param id
	 */
	public void setId(T id);

}
