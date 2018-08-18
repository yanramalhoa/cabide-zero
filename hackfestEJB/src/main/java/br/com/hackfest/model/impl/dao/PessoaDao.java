package br.com.hackfest.model.impl.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.hackfest.model.entities.Pessoa;
import br.com.hackfest.model.interfaces.repository.PessoaRepository;

@Stateless
@LocalBean
public class PessoaDao extends GenericoDao<Pessoa> implements PessoaRepository{

	private static final long serialVersionUID = -1714515883178857373L;

	public PessoaDao(){
	}
	
	
	
	
}
