package br.com.gradleresteasy.servicos;

import java.util.List;

/**
 * Interface responsável por definir metodos que devem ser implementados
 *
 * @author eduardoc
 *
 */
public interface CrudServico<T> {

	public T inserir();

	public T atualizar();

	public T deletar();

	public List<T> todos();

	public T ativos();

}
