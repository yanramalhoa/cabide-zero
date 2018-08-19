package br.com.hackfest.model.interfaces.repository;


import javax.ejb.Local;

import br.com.hackfest.model.entities.Cargo;

@Local
public interface CargoRepository extends GenericoRepository<Cargo>{
	
}
