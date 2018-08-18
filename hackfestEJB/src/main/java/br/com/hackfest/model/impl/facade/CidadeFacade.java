package br.com.hackfest.model.impl.facade;



import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.hackfest.controller.exception.CabideException;
import br.com.hackfest.model.entities.Municipio;
import br.com.hackfest.model.interfaces.business.MunicipioBusiness;
import br.com.hackfest.model.interfaces.repository.MunicipioRepository;

@Stateless
@LocalBean
public class CidadeFacade implements MunicipioBusiness{

	@EJB
	private MunicipioRepository cidadeRepository;
	

	@Override
	public Municipio salvar(Municipio t) throws CabideException {
		// TODO Auto-generated method stub
		
		
		if(t!=null && t.getId()==null){
			t = cidadeRepository.criar(t);
		} else {
			t = cidadeRepository.atualizar(t);
		}
		return t;
	}

	@Override
	public void excluir(Municipio t) throws CabideException {
		cidadeRepository.excluir(t);
	}

	@Override
	public List<Municipio> buscarTodos() throws CabideException {
		// TODO Auto-generated method stub
		return cidadeRepository.buscarTodos();
	}

	@Override
	public Municipio buscarPorId(Long id) throws CabideException {
		// TODO Auto-generated method stub
		return cidadeRepository.buscarPorID(id);
	}

	@Override
	public Municipio buscarPorEntidade(Municipio entidade) throws CabideException {
		// TODO Auto-generated method stub
		return cidadeRepository.buscarPorEntidade(entidade);
	}

	@Override
	public Number contaResultados(Municipio entidade) throws CabideException {
		// TODO Auto-generated method stub
		return cidadeRepository.contaResultados(entidade);
	}
	
	public List<Municipio> buscarPorExemplo(Municipio Cidade) throws CabideException{
		return cidadeRepository.buscarPorExemplo(Cidade);
	}





	@Override
	public List<Municipio> loadPaginated(Municipio entidade, int inicio, int tamanho, String campoOrdenacao,
			String tipoOrdenacao, Map<String, Object> parametros, StringBuilder where) throws CabideException {
		// TODO Auto-generated method stub
		return cidadeRepository.loadPaginated(entidade, inicio, tamanho, campoOrdenacao, tipoOrdenacao, parametros, where);
	}

}
