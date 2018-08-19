package br.com.hackfest.model.impl.facade;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.commons.mail.EmailException;

import br.com.hackfest.controller.exception.CabideException;
import br.com.hackfest.model.entities.Cargo;
import br.com.hackfest.model.interfaces.business.CargoBusiness;
import br.com.hackfest.model.interfaces.repository.CargoRepository;

@Stateless
@LocalBean
public class CargoFacade implements CargoBusiness{

	@EJB
	private CargoRepository cargoRepository;

	@Override
	public Cargo salvar(Cargo t) throws CabideException, EmailException {
		// TODO Auto-generated method stub
		if(t!=null) {
			if(t.getId()!=null) {
				cargoRepository.atualizar(t);
			}else {
				cargoRepository.criar(t);
			}
		}
		return t;
	}

	@Override
	public void excluir(Cargo t) throws CabideException {
		// TODO Auto-generated method stub
		cargoRepository.excluir(t);
	}

	@Override
	public List<Cargo> buscarTodos() throws CabideException {
		// TODO Auto-generated method stub
		return cargoRepository.buscarTodos();
	}

	@Override
	public Cargo buscarPorId(Long id) throws CabideException {
		// TODO Auto-generated method stub
		return cargoRepository.buscarPorID(id);
	}

	@Override
	public Cargo buscarPorEntidade(Cargo entidade) throws CabideException {
		// TODO Auto-generated method stub
		return cargoRepository.buscarPorEntidade(entidade);
	}

	@Override
	public List<Cargo> buscarPorExemplo(Cargo entidade) throws CabideException {
		// TODO Auto-generated method stub
		return cargoRepository.buscarPorExemplo(entidade);
	}

	@Override
	public Number contaResultados(Cargo entidade) throws CabideException {
		// TODO Auto-generated method stub
		return cargoRepository.contaResultados(entidade);
	}

	@Override
	public List<Cargo> loadPaginated(Cargo entidade, int inicio, int tamanho, String campoOrdenacao,
			String tipoOrdenacao, Map<String, Object> parametros, StringBuilder where) throws CabideException {
		// TODO Auto-generated method stub
		return cargoRepository.loadPaginated(entidade, inicio, tamanho, campoOrdenacao, tipoOrdenacao, parametros, where);
	}
	
}
