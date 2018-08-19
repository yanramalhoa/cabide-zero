package br.com.hackfest.model.impl.facade;




import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.hackfest.controller.exception.CabideException;
import br.com.hackfest.model.entities.Votacao;
import br.com.hackfest.model.interfaces.business.VotacaoBusiness;
import br.com.hackfest.model.interfaces.repository.VotacaoRepository;

@Stateless
@LocalBean
public class VotacaoFacade implements VotacaoBusiness{

	@EJB
	private VotacaoRepository votacaoRepository;
	

	@Override
	public Votacao salvar(Votacao t) throws CabideException {
		// TODO Auto-generated method stub
		if(t!=null && t.getId()==null){
			t = votacaoRepository.criar(t);
		} else {
			t = votacaoRepository.atualizar(t);
		}
		return t;
	}

	@Override
	public void excluir(Votacao t) throws CabideException {
		votacaoRepository.excluir(t);
	}

	@Override
	public List<Votacao> buscarTodos() throws CabideException {
		// TODO Auto-generated method stub
		return votacaoRepository.buscarTodos();
	}

	@Override
	public Votacao buscarPorId(Long id) throws CabideException {
		// TODO Auto-generated method stub
		return votacaoRepository.buscarPorID(id);
	}

	@Override
	public Votacao buscarPorEntidade(Votacao entidade) throws CabideException {
		// TODO Auto-generated method stub
		return votacaoRepository.buscarPorEntidade(entidade);
	}

	@Override
	public Number contaResultados(Votacao entidade) throws CabideException {
		// TODO Auto-generated method stub
		return votacaoRepository.contaResultados(entidade);
	}
	
	public List<Votacao> buscarPorExemplo(Votacao Votacao) throws CabideException{
		return votacaoRepository.buscarPorExemplo(Votacao);
	}





	@Override
	public List<Votacao> loadPaginated(Votacao entidade, int inicio, int tamanho, String campoOrdenacao,
			String tipoOrdenacao, Map<String, Object> parametros, StringBuilder where) throws CabideException {
		// TODO Auto-generated method stub
		return votacaoRepository.loadPaginated(entidade, inicio, tamanho, campoOrdenacao, tipoOrdenacao, parametros, where);
	}

}
