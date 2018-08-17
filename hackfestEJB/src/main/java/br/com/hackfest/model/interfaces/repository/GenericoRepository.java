package br.com.hackfest.model.interfaces.repository;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.hibernate.criterion.Criterion;

import br.com.hackfest.controller.exception.CabideException;

@Local
public interface GenericoRepository<T> extends Serializable {
	
	public static final String PERSISTENCE_UNIT = "cabideZeroPU";
	
	public T criar(T entidade) throws CabideException;
	public T atualizar(T entidade) throws CabideException;
	public void excluir(T entidade) throws CabideException;
	public void verificaUnicidade(T entidade) throws CabideException;
	public T buscarPorID(Long id) throws CabideException;
	public List<T> buscarTodos() throws CabideException;
	public List<T> buscarPorExemplo(T entidade) throws CabideException;
	public List<T> buscarPorExemploWithCriterion(T entidade, List<Criterion> criterions) throws CabideException;
	public T buscarPorEntidade(T entidade) throws CabideException;
	public Number contaResultados(T entidade) throws CabideException;
	public List<T> loadPaginated(T entidade, int inicio, int tamanho, String campoOrdenacao, String tipoOrdenacao, Map<String, Object> parametros, StringBuilder where) throws CabideException;
	public List<T> loadPaginatedWithCriterion(T entidade, int inicio, int tamanho, String campoOrdenacao, String tipoOrdenacao, Map<String, Object> parametros, StringBuilder where, List<Criterion> criterions) throws CabideException;
	@SuppressWarnings("rawtypes") public String getNamedQuery(String namedQuery, Class c) throws CabideException;
	public void setEntidade(Class<T> entidade) throws CabideException;
	Number contaResultadosWithCriterions(T entidade, List<Criterion> criterions)
			throws CabideException;
	public Number idMaxEntidade() throws CabideException;
	public void criarLista(List<T> lista) throws CabideException;
	T maxEntidade(T entidade) throws CabideException;
	void delete(T entity, Long id) throws CabideException;
	void deletarLista(List<T> lista) throws CabideException, Exception;
	List<T> criarListaComRetorno(List<T> lista) throws CabideException;
	void atualizarLista(List<T> lista) throws Exception;
	List<T> buscarPorExemploWithCriterionAndOrder(T entidade, List<Criterion> criterions, String camposOrdenacao)
			throws CabideException;
	
	
	

}