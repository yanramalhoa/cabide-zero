package br.com.hackfest.model.impl.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.hackfest.model.entities.Cargo;
import br.com.hackfest.model.interfaces.repository.CargoRepository;

@Stateless
@LocalBean
public class CargoDao extends GenericoDao<Cargo> implements CargoRepository{

	private static final long serialVersionUID = -1156574661632574429L;

	public CargoDao(){
	}
	
	
	
	
}
