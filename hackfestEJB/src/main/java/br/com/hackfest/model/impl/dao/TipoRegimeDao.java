package br.com.hackfest.model.impl.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.hackfest.model.entities.TipoRegime;
import br.com.hackfest.model.interfaces.repository.TipoRegimeRepository;

@Stateless
@LocalBean
public class TipoRegimeDao extends GenericoDao<TipoRegime> implements TipoRegimeRepository{

	private static final long serialVersionUID = -7743206788827751105L;

	public TipoRegimeDao(){
	}
	
	
	
	
}
