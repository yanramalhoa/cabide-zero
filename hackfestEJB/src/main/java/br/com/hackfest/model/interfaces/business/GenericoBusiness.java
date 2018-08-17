package br.com.hackfest.model.interfaces.business;

import java.util.List;
import java.util.Map;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.mail.EmailException;

import br.com.hackfest.controller.exception.CabideException;

public interface GenericoBusiness<T> {
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED) public T salvar(T t) throws CabideException, EmailException;
	@TransactionAttribute(TransactionAttributeType.REQUIRED) public void excluir(T t) throws CabideException;
	public List<T> buscarTodos() throws CabideException;
	public T buscarPorId(Long id) throws CabideException;
	public T buscarPorEntidade(T entidade) throws CabideException;
	public List<T> buscarPorExemplo(T entidade) throws CabideException;
	public Number contaResultados(T entidade) throws CabideException;
	public List<T> loadPaginated(T entidade, int inicio, int tamanho, String campoOrdenacao, String tipoOrdenacao, Map<String, Object> parametros, StringBuilder where) throws CabideException;
	
	
}
