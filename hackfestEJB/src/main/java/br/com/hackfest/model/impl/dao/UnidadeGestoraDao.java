package br.com.hackfest.model.impl.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.hackfest.model.entities.UnidadeGestora;
import br.com.hackfest.model.interfaces.repository.UnidadeGestoraRepository;

@Stateless
@LocalBean
public class UnidadeGestoraDao extends GenericoDao<UnidadeGestora> implements UnidadeGestoraRepository{

	private static final long serialVersionUID = 3036611474185877564L;

	public UnidadeGestoraDao(){
	}
	
	
	
	
}
