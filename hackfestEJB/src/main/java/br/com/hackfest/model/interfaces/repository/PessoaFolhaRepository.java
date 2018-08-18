package br.com.hackfest.model.interfaces.repository;


import javax.ejb.Local;

import br.com.hackfest.model.entities.PessoaFolha;

@Local
public interface PessoaFolhaRepository extends GenericoRepository<PessoaFolha>{
	
}
