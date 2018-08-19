package br.com.hackfest.model.impl.facade;




import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.hackfest.controller.exception.CabideException;
import br.com.hackfest.model.entities.Eleicao;
import br.com.hackfest.model.interfaces.business.EleicaoBusiness;
import br.com.hackfest.model.interfaces.repository.EleicaoRepository;

@Stateless
@LocalBean
public class EleicaoFacade implements EleicaoBusiness{

	@EJB
	private EleicaoRepository eleicaoRepository;
	

	@Override
	public Eleicao salvar(Eleicao t) throws CabideException {
		// TODO Auto-generated method stub
		
		if(t!=null && t.getId()==null){
			t = eleicaoRepository.criar(t);
		} else {
			t = eleicaoRepository.atualizar(t);
		}
		return t;
	}

	@Override
	public void excluir(Eleicao t) throws CabideException {
		eleicaoRepository.excluir(t);
	}

	@Override
	public List<Eleicao> buscarTodos() throws CabideException {
		// TODO Auto-generated method stub
		return eleicaoRepository.buscarTodos();
	}

	@Override
	public Eleicao buscarPorId(Long id) throws CabideException {
		// TODO Auto-generated method stub
		return eleicaoRepository.buscarPorID(id);
	}

	@Override
	public Eleicao buscarPorEntidade(Eleicao entidade) throws CabideException {
		// TODO Auto-generated method stub
		return eleicaoRepository.buscarPorEntidade(entidade);
	}

	@Override
	public Number contaResultados(Eleicao entidade) throws CabideException {
		// TODO Auto-generated method stub
		return eleicaoRepository.contaResultados(entidade);
	}
	
	public List<Eleicao> buscarPorExemplo(Eleicao Eleicao) throws CabideException{
		return eleicaoRepository.buscarPorExemplo(Eleicao);
	}





	@Override
	public List<Eleicao> loadPaginated(Eleicao entidade, int inicio, int tamanho, String campoOrdenacao,
			String tipoOrdenacao, Map<String, Object> parametros, StringBuilder where) throws CabideException {
		// TODO Auto-generated method stub
		return eleicaoRepository.loadPaginated(entidade, inicio, tamanho, campoOrdenacao, tipoOrdenacao, parametros, where);
	}

}
