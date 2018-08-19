package br.com.hackfest.model.interfaces.business;

import java.util.List;

import javax.ejb.Local;

import org.apache.commons.mail.EmailException;

import br.com.hackfest.controller.exception.CabideException;
import br.com.hackfest.model.entities.Usuario;

@Local
public interface UsuarioBusiness extends GenericoBusiness<Usuario>{

	public boolean verifyPasswordModify(Usuario usuario) throws CabideException, Exception;
	public Usuario login(Usuario usuario) throws CabideException, Exception;
	public List<Usuario> buscarPorExemplo(Usuario usuario) throws CabideException;
	Usuario atualizar(Usuario t) throws CabideException;
	String buscarEmail(String email) throws CabideException;
	public String generateHash(String str);
	void emailRecuperarSenha(Usuario t) throws EmailException, CabideException;
	List<Usuario> buscarRanking() throws CabideException;
}
