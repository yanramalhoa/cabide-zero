package br.com.hackfest.model.interfaces.repository;


import java.util.List;

import javax.ejb.Local; 

import br.com.hackfest.controller.exception.CabideException;
import br.com.hackfest.model.entities.Usuario;

@Local
public interface UsuarioRepository extends GenericoRepository<Usuario>{

	Usuario login(Usuario usuario) throws CabideException, Exception;

	Usuario getUsuario(String username) throws Exception;

	List<Usuario> buscarRanking(Usuario t) throws CabideException;

	
}
