package br.com.hackfest.model.interfaces.business;

import javax.ejb.Local;

import br.com.hackfest.controller.exception.CabideException;
import br.com.hackfest.model.entities.Partido;

@Local
public interface PartidoBusiness extends GenericoBusiness<Partido>{

	Partido atualizar(Partido t) throws CabideException;

}
