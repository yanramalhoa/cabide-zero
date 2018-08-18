package br.com.hackfest.model.impl.facade;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.commons.mail.EmailException;

import br.com.hackfest.controller.exception.CabideException;
import br.com.hackfest.model.entities.Pessoa;
import br.com.hackfest.model.interfaces.business.PessoaBusiness;
import br.com.hackfest.model.interfaces.repository.PessoaRepository;

@Stateless
@LocalBean
public class PessoaFacade implements PessoaBusiness{

	@EJB
	private PessoaRepository pessoaRepository;

	@Override
	public Pessoa salvar(Pessoa t) throws CabideException, EmailException {
		// TODO Auto-generated method stub
		if(t!=null) {
			if(t.getId()!=null) {
				pessoaRepository.atualizar(t);
			}else {
				pessoaRepository.criar(t);
			}
		}
		return t;
	}

	@Override
	public void excluir(Pessoa t) throws CabideException {
		// TODO Auto-generated method stub
		pessoaRepository.excluir(t);
	}

	@Override
	public List<Pessoa> buscarTodos() throws CabideException {
		// TODO Auto-generated method stub
		return pessoaRepository.buscarTodos();
	}

	@Override
	public Pessoa buscarPorId(Long id) throws CabideException {
		// TODO Auto-generated method stub
		return pessoaRepository.buscarPorID(id);
	}

	@Override
	public Pessoa buscarPorEntidade(Pessoa entidade) throws CabideException {
		// TODO Auto-generated method stub
		return pessoaRepository.buscarPorEntidade(entidade);
	}

	@Override
	public List<Pessoa> buscarPorExemplo(Pessoa entidade) throws CabideException {
		// TODO Auto-generated method stub
		return pessoaRepository.buscarPorExemplo(entidade);
	}

	@Override
	public Number contaResultados(Pessoa entidade) throws CabideException {
		// TODO Auto-generated method stub
		return pessoaRepository.contaResultados(entidade);
	}

	@Override
	public List<Pessoa> loadPaginated(Pessoa entidade, int inicio, int tamanho, String campoOrdenacao,
			String tipoOrdenacao, Map<String, Object> parametros, StringBuilder where) throws CabideException {
		// TODO Auto-generated method stub
		return pessoaRepository.loadPaginated(entidade, inicio, tamanho, campoOrdenacao, tipoOrdenacao, parametros, where);
	}
	
}
