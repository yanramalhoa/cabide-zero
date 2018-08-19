package br.com.hackfest.model.interfaces.repository;


import java.util.List;

import javax.ejb.Local;

import br.com.hackfest.controller.exception.CabideException;
import br.com.hackfest.model.entities.PessoaFolha;

@Local
public interface PessoaFolhaRepository extends GenericoRepository<PessoaFolha>{
	
	public List<PessoaFolha> listaFolha(PessoaFolha pessoaFolha) throws CabideException;
	
}
