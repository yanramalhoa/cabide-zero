package br.com.hackfest.model.impl.facade;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.commons.mail.EmailException;

import br.com.hackfest.controller.exception.CabideException;
import br.com.hackfest.model.entities.Partido;
import br.com.hackfest.model.interfaces.business.PartidoBusiness;
import br.com.hackfest.model.interfaces.repository.PartidoRepository;
import br.com.hackfest.model.util.SHA;

@Stateless
@LocalBean
public class PartidoFacade implements PartidoBusiness{

	@EJB
	private PartidoRepository partidoRepository;
	
	@Override
	public Partido salvar(Partido t) throws CabideException, EmailException {
		// TODO Auto-generated method stub
			if(t != null && t.getId()==null){
				t = partidoRepository.criar(t);
			} else {
				t = partidoRepository.atualizar(t);
			}
		
		return t;
	}
	
	@Override
	public Partido atualizar(Partido t) throws CabideException{
		if(t != null && t.getId() != null){
			partidoRepository.atualizar(t);
		}
		return t;
	}
	
	
	public String getHash(Partido t) {
		String hash = null;
		hash = String.valueOf(t.getId()).concat("777");
	     MessageDigest m;
		try {
			m = MessageDigest.getInstance("MD5");
			m.update(hash.getBytes(),0,hash.length());
			hash = ""+new BigInteger(1,m.digest()).toString(16);
		

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		
		return hash;
	}

	@Override
	public void excluir(Partido t) throws CabideException {
		partidoRepository.excluir(t);
	}

	@Override
	public List<Partido> buscarTodos() throws CabideException {
		// TODO Auto-generated method stub
		return partidoRepository.buscarTodos();
	}

	@Override
	public Partido buscarPorId(Long id) throws CabideException {
		// TODO Auto-generated method stub
		return partidoRepository.buscarPorID(id);
	}


	@Override
	public Partido buscarPorEntidade(Partido entidade) throws CabideException {
		// TODO Auto-generated method stub
		return partidoRepository.buscarPorEntidade(entidade);
	}

	@Override
	public Number contaResultados(Partido entidade) throws CabideException {
		// TODO Auto-generated method stub
		return partidoRepository.contaResultados(entidade);
	}
	
	public List<Partido> buscarPorExemplo(Partido Partido) throws CabideException{
		return partidoRepository.buscarPorExemplo(Partido);
	}

	
	public String generateHash(String str){
		return SHA.bytesToHex(SHA.hash256(str));
	}


	@Override
	public List<Partido> loadPaginated(Partido entidade, int inicio, int tamanho, String campoOrdenacao,
			String tipoOrdenacao, Map<String, Object> parametros, StringBuilder where) throws CabideException {
		// TODO Auto-generated method stub
		return partidoRepository.loadPaginated(entidade, inicio, tamanho, campoOrdenacao, tipoOrdenacao, parametros, where);
	}

}
