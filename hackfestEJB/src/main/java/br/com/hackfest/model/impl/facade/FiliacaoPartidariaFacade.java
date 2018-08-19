package br.com.hackfest.model.impl.facade;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.commons.mail.EmailException;

import br.com.hackfest.controller.exception.CabideException;
import br.com.hackfest.model.entities.FiliacaoPartidaria;
import br.com.hackfest.model.interfaces.business.FiliacaoPartidariaBusiness;
import br.com.hackfest.model.interfaces.repository.FiliacaoPartidariaRepository;

@Stateless
@LocalBean
public class FiliacaoPartidariaFacade implements FiliacaoPartidariaBusiness{

	@EJB
	private FiliacaoPartidariaRepository filiacaoPartidariaRepository;

	@Override
	public FiliacaoPartidaria salvar(FiliacaoPartidaria t) throws CabideException, EmailException {
		// TODO Auto-generated method stub
		if(t!=null) {
			if(t.getId()!=null) {
				filiacaoPartidariaRepository.atualizar(t);
			}else {
				filiacaoPartidariaRepository.criar(t);
			}
		}
		return t;
	}

	@Override
	public void excluir(FiliacaoPartidaria t) throws CabideException {
		// TODO Auto-generated method stub
		filiacaoPartidariaRepository.excluir(t);
	}

	@Override
	public List<FiliacaoPartidaria> buscarTodos() throws CabideException {
		// TODO Auto-generated method stub
		return filiacaoPartidariaRepository.buscarTodos();
	}

	@Override
	public FiliacaoPartidaria buscarPorId(Long id) throws CabideException {
		// TODO Auto-generated method stub
		return filiacaoPartidariaRepository.buscarPorID(id);
	}

	@Override
	public FiliacaoPartidaria buscarPorEntidade(FiliacaoPartidaria entidade) throws CabideException {
		// TODO Auto-generated method stub
		return filiacaoPartidariaRepository.buscarPorEntidade(entidade);
	}

	@Override
	public List<FiliacaoPartidaria> buscarPorExemplo(FiliacaoPartidaria entidade) throws CabideException {
		// TODO Auto-generated method stub
		return filiacaoPartidariaRepository.buscarPorExemplo(entidade);
	}

	@Override
	public Number contaResultados(FiliacaoPartidaria entidade) throws CabideException {
		// TODO Auto-generated method stub
		return filiacaoPartidariaRepository.contaResultados(entidade);
	}

	@Override
	public List<FiliacaoPartidaria> loadPaginated(FiliacaoPartidaria entidade, int inicio, int tamanho, String campoOrdenacao,
			String tipoOrdenacao, Map<String, Object> parametros, StringBuilder where) throws CabideException {
		// TODO Auto-generated method stub
		return filiacaoPartidariaRepository.loadPaginated(entidade, inicio, tamanho, campoOrdenacao, tipoOrdenacao, parametros, where);
	}
	
}
