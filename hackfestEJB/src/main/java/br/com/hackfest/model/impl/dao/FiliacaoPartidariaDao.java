package br.com.hackfest.model.impl.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.hackfest.model.entities.FiliacaoPartidaria;
import br.com.hackfest.model.interfaces.repository.FiliacaoPartidariaRepository;

@Stateless
@LocalBean
public class FiliacaoPartidariaDao extends GenericoDao<FiliacaoPartidaria> implements FiliacaoPartidariaRepository{

	
	private static final long serialVersionUID = -4455280204439429050L;

	public FiliacaoPartidariaDao(){
	}
	
	
	
	
}
