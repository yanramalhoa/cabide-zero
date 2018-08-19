package br.com.hackfest.model.impl.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.hackfest.controller.exception.CabideException;
import br.com.hackfest.model.entities.Usuario;
import br.com.hackfest.model.interfaces.repository.UsuarioRepository;

@Stateless
@LocalBean
public class UsuarioDao extends GenericoDao<Usuario> implements UsuarioRepository{

	private static final long serialVersionUID = 2155017995455344150L;
	

	@EJB
	private UsuarioRepository usuarioRepository;
	
	public UsuarioDao(){
	}
	
	
	
	
	@Override
	public Usuario getUsuario(String username) throws Exception {
		Usuario usuario = new Usuario();
		usuario.setLogin(username);
		try {
			return this.buscarPorExemplo(usuario).iterator().next();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("Usuário não cadastrado");
		}
	}
	
	
	@Override
	public Usuario login(Usuario usuario) throws Exception {
		this.requiredField(usuario);
		/*String senha = this.generateHash(usuario.getSenha());
		usuario.setSenha(senha);*/
		//verifica se o nome do usuário existe
		this.getUsuario(usuario.getLogin());
//		usuario.setSenhaAnterior(null);
		Usuario usr = null;
		try {
			List<Usuario> usuarios = this.buscarPorExemplo(usuario);
			if(usuarios != null && usuarios.size() > 0)
				usr = usuarios.iterator().next(); 
			if(usr==null || usr.getId()==null){
				throw new Exception("Senha incorreta.");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("Senha incorreta.");
		} 
		 
		return usr;
	}
	
	private void requiredField(Usuario usuario) throws Exception{
		if(usuario==null){
			throw new Exception("O objeto está nulo.");
		}else if(usuario.getLogin()==null || usuario.getLogin().isEmpty()){
			throw new Exception("Informe o Nome do Usuário");
		}else if(usuario.getSenha()==null || usuario.getSenha().isEmpty()){
			throw new Exception("Informe a Senha do Usuário");
		}
	}

	@Override
	public List<Usuario> buscarRanking(Usuario t) throws CabideException{
		List<Usuario> listaUsuario = new ArrayList<>();
		String sql =
				"select  u.nome, u.numeroVitorias, u.cotacaoTotalVitorias, u.id" + 
				" from Usuario u where u.ativo = true " + 
				"order by u.cotacaoTotalVitorias desc, u.numeroVitorias desc";

			List<Object[]> rows =  this.consultarPorQueryRetornaListaObjetos(t, sql);
			
			for(Object[] object : rows){
				Usuario usuario = new Usuario();
				usuario.setNome((String) object[0]); 
				usuario.setNumeroVitorias((Integer) object[1]);
				usuario.setCotacaoTotalVitorias((BigDecimal) object[2]);
				usuario.setId((Long) object[3]);
				listaUsuario.add(usuario);
		
			}
		
		return listaUsuario;
		
	}







	


	
	
	
	
}
