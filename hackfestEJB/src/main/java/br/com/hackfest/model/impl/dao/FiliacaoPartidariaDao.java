package br.com.hackfest.model.impl.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.hackfest.controller.exception.CabideException;
import br.com.hackfest.model.entities.FiliacaoPartidaria;
import br.com.hackfest.model.entities.Partido;
import br.com.hackfest.model.entities.Pessoa;
import br.com.hackfest.model.enums.Estado;
import br.com.hackfest.model.enums.SituacaoFiliacao;
import br.com.hackfest.model.interfaces.repository.FiliacaoPartidariaRepository;

@Stateless
@LocalBean
public class FiliacaoPartidariaDao extends GenericoDao<FiliacaoPartidaria> implements FiliacaoPartidariaRepository{

	
	private static final long serialVersionUID = -4455280204439429050L;

	public FiliacaoPartidariaDao(){
	}
	
	@Override
	public List<FiliacaoPartidaria> listaFiliacaoPartidaria() throws CabideException{
		List<FiliacaoPartidaria> list = new ArrayList<>();
		
		String sql =
		"Select fp.id, fp.dataExtracao, fp.dataFiliacao, fp.situacaoFiliacao, fp.unidadeFederativa, p.id, p.nome, p.cpf, pt.id, pt.nome, pt.sigla " + 
		"From FiliacaoPartidaria fp  " + 
		"Inner Join fp.pessoa p  " + 
		"Inner Join fp.partido pt  " + 
		" ";

		List<Object[]> rows =  this.consultarPorQueryRetornaListaObjetos(new FiliacaoPartidaria(), sql);
		
		for(Object[] object : rows){
			FiliacaoPartidaria fp = new FiliacaoPartidaria();
			fp.setId((Long) object[0]);
			fp.setDataExtracao((Date) object[1]);
			fp.setDataFiliacao((Date) object[2]);
			fp.setSituacaoFiliacao((SituacaoFiliacao) object[3]);
			fp.setUnidadeFederativa((Estado) object[4]);
			
			Pessoa p = new Pessoa();
			p.setId((Long) object[5]); 
			p.setNome((String) object[6]); 
			p.setCpf((String) object[7]); 
			
			
			Partido partido = new Partido();
			partido.setId((Long) object[8]);
			partido.setNome((String) object[9]);
			partido.setSigla((String) object[10]);
			
			fp.setPessoa(p);
			fp.setPartido(partido);
			
			list.add(fp);
		}
		return list;
		
		
		
	}
	
	
}
