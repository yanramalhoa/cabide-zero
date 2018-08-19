package br.com.hackfest.model.interfaces.repository;


import javax.ejb.Local;

import br.com.hackfest.model.entities.Pessoa;

@Local
public interface PessoaRepository extends GenericoRepository<Pessoa>{
	
}
