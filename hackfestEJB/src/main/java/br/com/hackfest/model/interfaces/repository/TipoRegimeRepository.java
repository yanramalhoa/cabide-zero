package br.com.hackfest.model.interfaces.repository;


import javax.ejb.Local;

import br.com.hackfest.model.entities.TipoRegime;

@Local
public interface TipoRegimeRepository extends GenericoRepository<TipoRegime>{
	
}
