package br.com.hackfest.model.impl.facade;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.commons.mail.EmailException;

import br.com.hackfest.controller.exception.CabideException;
import br.com.hackfest.model.entities.UnidadeGestora;
import br.com.hackfest.model.interfaces.business.UnidadeGestoraBusiness;
import br.com.hackfest.model.interfaces.repository.UnidadeGestoraRepository;

@Stateless
@LocalBean
public class UnidadeGestoraFacade implements UnidadeGestoraBusiness{

	@EJB
	private UnidadeGestoraRepository unidadeGestoraRepository;

	@Override
	public UnidadeGestora salvar(UnidadeGestora t) throws CabideException, EmailException {
		// TODO Auto-generated method stub
		if(t!=null) {
			if(t.getId()!=null) {
				unidadeGestoraRepository.atualizar(t);
			}else {
				unidadeGestoraRepository.criar(t);
			}
		}
		return t;
	}

	@Override
	public void excluir(UnidadeGestora t) throws CabideException {
		// TODO Auto-generated method stub
		unidadeGestoraRepository.excluir(t);
	}

	@Override
	public List<UnidadeGestora> buscarTodos() throws CabideException {
		// TODO Auto-generated method stub
		return unidadeGestoraRepository.buscarTodos();
	}

	@Override
	public UnidadeGestora buscarPorId(Long id) throws CabideException {
		// TODO Auto-generated method stub
		return unidadeGestoraRepository.buscarPorID(id);
	}

	@Override
	public UnidadeGestora buscarPorEntidade(UnidadeGestora entidade) throws CabideException {
		// TODO Auto-generated method stub
		return unidadeGestoraRepository.buscarPorEntidade(entidade);
	}

	@Override
	public List<UnidadeGestora> buscarPorExemplo(UnidadeGestora entidade) throws CabideException {
		// TODO Auto-generated method stub
		return unidadeGestoraRepository.buscarPorExemplo(entidade);
	}

	@Override
	public Number contaResultados(UnidadeGestora entidade) throws CabideException {
		// TODO Auto-generated method stub
		return unidadeGestoraRepository.contaResultados(entidade);
	}

	@Override
	public List<UnidadeGestora> loadPaginated(UnidadeGestora entidade, int inicio, int tamanho, String campoOrdenacao,
			String tipoOrdenacao, Map<String, Object> parametros, StringBuilder where) throws CabideException {
		// TODO Auto-generated method stub
		return unidadeGestoraRepository.loadPaginated(entidade, inicio, tamanho, campoOrdenacao, tipoOrdenacao, parametros, where);
	}
	
}
