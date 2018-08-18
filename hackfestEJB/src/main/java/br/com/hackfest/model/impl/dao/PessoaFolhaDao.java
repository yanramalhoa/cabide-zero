package br.com.hackfest.model.impl.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.hackfest.model.entities.PessoaFolha;
import br.com.hackfest.model.interfaces.repository.PessoaFolhaRepository;

@Stateless
@LocalBean
public class PessoaFolhaDao extends GenericoDao<PessoaFolha> implements PessoaFolhaRepository{

	private static final long serialVersionUID = -2495968413392434373L;

	public PessoaFolhaDao(){
	}
	
	
	
	
}
