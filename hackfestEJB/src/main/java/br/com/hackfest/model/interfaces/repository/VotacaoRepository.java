package br.com.hackfest.model.interfaces.repository;


import java.util.List;

import javax.ejb.Local;

import br.com.hackfest.model.entities.Candidato;
import br.com.hackfest.model.entities.Votacao;

@Local
public interface VotacaoRepository extends GenericoRepository<Votacao>{



	
}
