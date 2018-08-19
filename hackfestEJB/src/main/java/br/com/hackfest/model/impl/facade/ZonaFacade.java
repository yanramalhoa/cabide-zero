package br.com.hackfest.model.impl.facade;




import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.hackfest.controller.exception.CabideException;
import br.com.hackfest.model.entities.Zona;
import br.com.hackfest.model.interfaces.business.ZonaBusiness;
import br.com.hackfest.model.interfaces.repository.ZonaRepository;

@Stateless
@LocalBean
public class ZonaFacade implements ZonaBusiness{

	@EJB
	private ZonaRepository zonaRepository;
	

	@Override
	public Zona salvar(Zona t) throws CabideException {
		// TODO Auto-generated method stub
		
		
		if(t!=null && t.getId()==null){
			t = zonaRepository.criar(t);
		} else {
			t = zonaRepository.atualizar(t);
		}
		return t;
	}

	@Override
	public void excluir(Zona t) throws CabideException {
		zonaRepository.excluir(t);
	}

	@Override
	public List<Zona> buscarTodos() throws CabideException {
		// TODO Auto-generated method stub
		return zonaRepository.buscarTodos();
	}

	@Override
	public Zona buscarPorId(Long id) throws CabideException {
		// TODO Auto-generated method stub
		return zonaRepository.buscarPorID(id);
	}

	@Override
	public Zona buscarPorEntidade(Zona entidade) throws CabideException {
		// TODO Auto-generated method stub
		return zonaRepository.buscarPorEntidade(entidade);
	}

	@Override
	public Number contaResultados(Zona entidade) throws CabideException {
		// TODO Auto-generated method stub
		return zonaRepository.contaResultados(entidade);
	}
	
	public List<Zona> buscarPorExemplo(Zona Zona) throws CabideException{
		return zonaRepository.buscarPorExemplo(Zona);
	}





	@Override
	public List<Zona> loadPaginated(Zona entidade, int inicio, int tamanho, String campoOrdenacao,
			String tipoOrdenacao, Map<String, Object> parametros, StringBuilder where) throws CabideException {
		// TODO Auto-generated method stub
		return zonaRepository.loadPaginated(entidade, inicio, tamanho, campoOrdenacao, tipoOrdenacao, parametros, where);
	}

}
