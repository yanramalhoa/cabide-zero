package br.com.hackfest.model.interfaces.repository;


import javax.ejb.Local;

import br.com.hackfest.model.entities.Partido;

@Local
public interface PartidoRepository extends GenericoRepository<Partido>{

	
}
