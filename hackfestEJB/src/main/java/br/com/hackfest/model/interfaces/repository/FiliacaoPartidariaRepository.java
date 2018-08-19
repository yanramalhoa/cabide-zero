package br.com.hackfest.model.interfaces.repository;


import java.util.List;

import javax.ejb.Local;

import br.com.hackfest.controller.exception.CabideException;
import br.com.hackfest.model.entities.FiliacaoPartidaria;

@Local
public interface FiliacaoPartidariaRepository extends GenericoRepository<FiliacaoPartidaria>{
	public List<FiliacaoPartidaria> listaFiliacaoPartidaria() throws CabideException;
}
