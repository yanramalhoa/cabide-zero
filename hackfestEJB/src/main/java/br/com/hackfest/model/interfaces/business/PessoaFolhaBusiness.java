package br.com.hackfest.model.interfaces.business;

import java.util.List;

import javax.ejb.Local;

import br.com.hackfest.controller.exception.CabideException;
import br.com.hackfest.model.entities.Pessoa;
import br.com.hackfest.model.entities.PessoaFolha;

@Local
public interface PessoaFolhaBusiness extends GenericoBusiness<PessoaFolha>{

	public List<Pessoa> listaPessoasComVinculo(Integer mes, Integer ano) throws CabideException;
	
}
