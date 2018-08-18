package br.com.hackfest.model.impl.facade;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.commons.mail.EmailException;

import br.com.hackfest.controller.exception.CabideException;
import br.com.hackfest.model.entities.TipoRegime;
import br.com.hackfest.model.interfaces.business.TipoRegimeBusiness;
import br.com.hackfest.model.interfaces.repository.TipoRegimeRepository;

@Stateless
@LocalBean
public class TipoRegimeFacade implements TipoRegimeBusiness{

	@EJB
	private TipoRegimeRepository tipoRegimeRepository;

	@Override
	public TipoRegime salvar(TipoRegime t) throws CabideException, EmailException {
		// TODO Auto-generated method stub
		if(t!=null) {
			if(t.getId()!=null) {
				tipoRegimeRepository.atualizar(t);
			}else {
				tipoRegimeRepository.criar(t);
			}
		}
		return t;
	}

	@Override
	public void excluir(TipoRegime t) throws CabideException {
		// TODO Auto-generated method stub
		tipoRegimeRepository.excluir(t);
	}

	@Override
	public List<TipoRegime> buscarTodos() throws CabideException {
		// TODO Auto-generated method stub
		return tipoRegimeRepository.buscarTodos();
	}

	@Override
	public TipoRegime buscarPorId(Long id) throws CabideException {
		// TODO Auto-generated method stub
		return tipoRegimeRepository.buscarPorID(id);
	}

	@Override
	public TipoRegime buscarPorEntidade(TipoRegime entidade) throws CabideException {
		// TODO Auto-generated method stub
		return tipoRegimeRepository.buscarPorEntidade(entidade);
	}

	@Override
	public List<TipoRegime> buscarPorExemplo(TipoRegime entidade) throws CabideException {
		// TODO Auto-generated method stub
		return tipoRegimeRepository.buscarPorExemplo(entidade);
	}

	@Override
	public Number contaResultados(TipoRegime entidade) throws CabideException {
		// TODO Auto-generated method stub
		return tipoRegimeRepository.contaResultados(entidade);
	}

	@Override
	public List<TipoRegime> loadPaginated(TipoRegime entidade, int inicio, int tamanho, String campoOrdenacao,
			String tipoOrdenacao, Map<String, Object> parametros, StringBuilder where) throws CabideException {
		// TODO Auto-generated method stub
		return tipoRegimeRepository.loadPaginated(entidade, inicio, tamanho, campoOrdenacao, tipoOrdenacao, parametros, where);
	}
	
}
