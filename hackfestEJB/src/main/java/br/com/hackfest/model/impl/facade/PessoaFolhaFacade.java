package br.com.hackfest.model.impl.facade;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.commons.mail.EmailException;

import br.com.hackfest.controller.exception.CabideException;
import br.com.hackfest.model.entities.PessoaFolha;
import br.com.hackfest.model.interfaces.business.PessoaFolhaBusiness;
import br.com.hackfest.model.interfaces.repository.PessoaFolhaRepository;

@Stateless
@LocalBean
public class PessoaFolhaFacade implements PessoaFolhaBusiness{

	@EJB
	private PessoaFolhaRepository pessoaFolhaRepository;

	@Override
	public PessoaFolha salvar(PessoaFolha t) throws CabideException, EmailException {
		// TODO Auto-generated method stub
		if(t!=null) {
			if(t.getId()!=null) {
				pessoaFolhaRepository.atualizar(t);
			}else {
				pessoaFolhaRepository.criar(t);
			}
		}
		return t;
	}

	@Override
	public void excluir(PessoaFolha t) throws CabideException {
		// TODO Auto-generated method stub
		pessoaFolhaRepository.excluir(t);
	}

	@Override
	public List<PessoaFolha> buscarTodos() throws CabideException {
		// TODO Auto-generated method stub
		return pessoaFolhaRepository.buscarTodos();
	}

	@Override
	public PessoaFolha buscarPorId(Long id) throws CabideException {
		// TODO Auto-generated method stub
		return pessoaFolhaRepository.buscarPorID(id);
	}

	@Override
	public PessoaFolha buscarPorEntidade(PessoaFolha entidade) throws CabideException {
		// TODO Auto-generated method stub
		return pessoaFolhaRepository.buscarPorEntidade(entidade);
	}

	@Override
	public List<PessoaFolha> buscarPorExemplo(PessoaFolha entidade) throws CabideException {
		// TODO Auto-generated method stub
		return pessoaFolhaRepository.buscarPorExemplo(entidade);
	}

	@Override
	public Number contaResultados(PessoaFolha entidade) throws CabideException {
		// TODO Auto-generated method stub
		return pessoaFolhaRepository.contaResultados(entidade);
	}

	@Override
	public List<PessoaFolha> loadPaginated(PessoaFolha entidade, int inicio, int tamanho, String campoOrdenacao,
			String tipoOrdenacao, Map<String, Object> parametros, StringBuilder where) throws CabideException {
		// TODO Auto-generated method stub
		return pessoaFolhaRepository.loadPaginated(entidade, inicio, tamanho, campoOrdenacao, tipoOrdenacao, parametros, where);
	}
	
}
