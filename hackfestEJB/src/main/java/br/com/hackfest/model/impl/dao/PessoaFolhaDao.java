package br.com.hackfest.model.impl.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.hackfest.controller.exception.CabideException;
import br.com.hackfest.model.entities.Cargo;
import br.com.hackfest.model.entities.FiliacaoPartidaria;
import br.com.hackfest.model.entities.Pessoa;
import br.com.hackfest.model.entities.PessoaFolha;
import br.com.hackfest.model.entities.TipoRegime;
import br.com.hackfest.model.entities.UnidadeGestora;
import br.com.hackfest.model.interfaces.business.FiliacaoPartidariaBusiness;
import br.com.hackfest.model.interfaces.repository.PessoaFolhaRepository;

@Stateless
@LocalBean
public class PessoaFolhaDao extends GenericoDao<PessoaFolha> implements PessoaFolhaRepository{

	private static final long serialVersionUID = -2495968413392434373L;
	
	@EJB
	private FiliacaoPartidariaBusiness filiacaoPartidariaBusiness;

	public PessoaFolhaDao(){
	}

	@Override
	public List<PessoaFolha> listaFolha(PessoaFolha pessoaFolha) throws CabideException {
		// TODO Auto-generated method stub
		List<PessoaFolha> listaFolhas = new ArrayList<>();
		
		Integer mes = pessoaFolha.getMes();
		Integer ano = pessoaFolha.getAno();
		
		String sql =
		"Select  " + 
		"	pf.id_pessoa_folha, p.id_pessoa, p.cpf, p.nome as pessoa, ug.id_unidade_gestora, ug.nome unidade_gestora, c.id_cargo,c.nome cargo, tr.id_tipo_regime, tr.descricao tipo_regime, pf.vantagem " + 
		"From pessoa_folha pf " + 
		"Inner Join pessoa p on p.id_pessoa = pf.id_pessoa " + 
		"Inner Join cargo c on c.id_cargo = pf.id_cargo " + 
		"Inner Join unidade_gestora ug on ug.id_unidade_gestora = c.id_unidade_gestora " + 
		"Inner Join tipo_regime tr on tr.id_tipo_regime = c.id_tipo_regime " + 
		"Inner Join (Select p.id_pessoa, p.cpf " + 
		"	    From pessoa_folha pf " + 
		"	    Inner Join pessoa p on p.id_pessoa = pf.id_pessoa " + 
		"	    Inner Join cargo c on c.id_cargo = pf.id_cargo " + 
		"	    Inner Join tipo_regime tr on tr.id_tipo_regime = c.id_tipo_regime " + 
		"	    Where pf.mes = 1 " + 
		"	    and tr.descricao not in ('Inativos / Pensionistas', 'Benefício previdênciário temporário') " + 
		"	    and c.nome not like '%PROF.%' " + 
		"	    and c.nome not like '%PROFESSOR%' " + 
		"	    and c.nome not like '%MEDICO%' " + 
		"	    and c.nome not like '%ENFERM%' " + 
		"	    and c.nome not like '%NUTRIC%' " + 
		"	    and c.nome not like '%FONO%' " + 
		"	    and c.nome not like '%FISIO%' " + 
		"	    and c.nome not like '%ODONTO%' " + 
		"	    and c.nome not like '%DENTIST%' " + 
		"	    and c.nome not like '%FARMAC%' " + 
		"	    Group By p.id_pessoa, p.cpf " + 
		"	    Having count(p.id_pessoa)>1 " + 
		") pn on pn.id_pessoa = pf.id_pessoa " + 
		" " + 
		"Where pf.mes =  " + mes + " " + 
		"and   pf.ano =  " + ano + " " + 
		"and exists (Select 1 "  +
		"	    From filiacao_partidaria fp " +
		"	    Where fp.id_pessoa = pf.id_pessoa) " +
		"and not exists(Select 1 " + 
		"		From pessoa_folha pf_1 " + 
		"		Inner Join cargo c_1 on c_1.id_cargo = pf_1.id_cargo " + 
		"		Where pf_1.id_pessoa = pf.id_pessoa " + 
		"		and c_1.nome like '%PROF.%' " + 
		"		and c_1.nome like '%PROFESSOR%' " + 
		"	        and c.nome not like '%MEDICO%' " + 
		"	        and c.nome not like '%ENFERM%' " + 
		"	        and c.nome not like '%ENFERM%' " + 
		"	        and c.nome not like '%NUTRIC%' " + 
		"	        and c.nome not like '%FONO%' " + 
		"	        and c.nome not like '%FISIO%' " + 
		"	        and c.nome not like '%ODONTO%' " + 
		"	        and c.nome not like '%DENTIST%' " + 
		"	        and c.nome not like '%FARMAC%' " + 
		"	    ) " + 
		"Order By pf.id_pessoa_folha, p.cpf " + 
		" ";

		List<Object[]> rows =  this.consultarPorQueryRetornaListaObjetos(pessoaFolha, sql);

		for(Object[] object : rows){
			PessoaFolha pf = new PessoaFolha();
			pf.setId((Long) object[0]); 
			
			Pessoa p = new Pessoa();
			p.setId((Long) object[1]); 
			p.setCpf((String) object[2]); 
			p.setNome((String) object[3]); 
			
			UnidadeGestora ug = new UnidadeGestora();
			ug.setId((Long) object[4]); 
			ug.setNome((String) object[5]); 
			
			Cargo c = new Cargo();
			c.setId((Long) object[6]);
			c.setNome((String) object[7]);
			c.setUnidadeGestora(ug);
			
			TipoRegime tr = new TipoRegime();
			tr.setId((Long) object[8]);
			tr.setDescricao((String) object[9]);
			
			c.setTipoRegime(tr);
			
			pf.setCargo(c);
			pf.setMes(mes);
			pf.setAno(ano);
			pf.setPessoa(p);
			
			pf.setVantagem((BigDecimal) object[10]); 
			
			listaFolhas.add(pf);
		}
		
		return listaFolhas;


	}
	
	public List<Pessoa> listaPessoasComVinculo(Integer mes, Integer ano) throws CabideException {
		List<Pessoa> pessoas = new ArrayList<>();
		String sql =
		"Select  " + 
		"	p.id_pessoa, p.cpf, p.nome as pessoa, sum(pf.vantagem) as total_vantagem, count(p.id_pessoa) total_por_pessoa " + 
		"From pessoa_folha pf " + 
		"Inner Join pessoa p on p.id_pessoa = pf.id_pessoa " + 
		"Inner Join cargo c on c.id_cargo = pf.id_cargo " + 
		"Inner Join unidade_gestora ug on ug.id_unidade_gestora = c.id_unidade_gestora " + 
		"Inner Join tipo_regime tr on tr.id_tipo_regime = c.id_tipo_regime " + 
		"Inner Join (Select p.id_pessoa, p.cpf " + 
		"	    From pessoa_folha pf " + 
		"	    Inner Join pessoa p on p.id_pessoa = pf.id_pessoa " + 
		"	    Inner Join cargo c on c.id_cargo = pf.id_cargo " + 
		"	    Inner Join tipo_regime tr on tr.id_tipo_regime = c.id_tipo_regime " + 
		"	    Where pf.mes = 1 " + 
		"	    and tr.descricao not in ('Inativos / Pensionistas', 'Benefício previdênciário temporário') " + 
		"	    and c.nome not like '%PROF.%' " + 
		"	    and c.nome not like '%PROFESSOR%' " + 
		"	    and c.nome not like '%MEDICO%' " + 
		"	    and c.nome not like '%ENFERM%' " + 
		"	    and c.nome not like '%NUTRIC%' " + 
		"	    and c.nome not like '%FONO%' " + 
		"	    and c.nome not like '%FISIO%' " + 
		"	    and c.nome not like '%ODONTO%' " + 
		"	    and c.nome not like '%DENTIST%' " + 
		"	    and c.nome not like '%FARMAC%' " + 
		"	    Group By p.id_pessoa, p.cpf " + 
		"	    Having count(p.id_pessoa)>1 " + 
		") pn on pn.id_pessoa = pf.id_pessoa " + 
		" " + 
		"Where pf.mes =  " + mes + " " + 
		"and   pf.ano =  " + ano + " " + 
		"and exists (Select 1 "  +
		"	    From filiacao_partidaria fp " +
		"	    Where fp.id_pessoa = pf.id_pessoa) " +
		"and not exists(Select 1 " + 
		"		From pessoa_folha pf_1 " + 
		"		Inner Join cargo c_1 on c_1.id_cargo = pf_1.id_cargo " + 
		"		Where pf_1.id_pessoa = pf.id_pessoa " + 
		"		and c_1.nome like '%PROF.%' " + 
		"		and c_1.nome like '%PROFESSOR%' " + 
		"	        and c.nome not like '%MEDICO%' " + 
		"	        and c.nome not like '%ENFERM%' " + 
		"	        and c.nome not like '%ENFERM%' " + 
		"	        and c.nome not like '%NUTRIC%' " + 
		"	        and c.nome not like '%FONO%' " + 
		"	        and c.nome not like '%FISIO%' " + 
		"	        and c.nome not like '%ODONTO%' " + 
		"	        and c.nome not like '%DENTIST%' " + 
		"	        and c.nome not like '%FARMAC%' " + 
		"	    ) " +
		"Group By p.id_pessoa, p.cpf, p.nome as pessoa" +
		"Order By p.nome " + 
		" ";

		List<Object[]> rows =  this.consultarPorQueryRetornaListaObjetos(new PessoaFolha(), sql);
		
		List<FiliacaoPartidaria> listaFP = filiacaoPartidariaBusiness.buscarTodos();

		PessoaFolha pessoaFolha = new PessoaFolha();
		pessoaFolha.setMes(mes);
		pessoaFolha.setAno(ano);
		
		List<PessoaFolha> listaPessoaFolha = this.listaFolha(pessoaFolha);
		
		for(Object[] object : rows){
			Pessoa p = new Pessoa();
			p.setId((Long) object[1]); 
			p.setCpf((String) object[2]); 
			p.setNome((String) object[3]); 
			p.setTotalVantagem((BigDecimal) object[4]); 
			p.setQtdTotalPorPessoa((Integer) object[5]); 
			
			p.setListaFiliacaoPartidaria(new ArrayList<>());
			
			if(listaFP!=null && listaFP.size()>0) {
				for(FiliacaoPartidaria fp: listaFP) {
					if(p.getId().equals(fp.getPessoa().getId())) {
						p.getListaFiliacaoPartidaria().add(fp);
					}
				}
			}
			if(listaPessoaFolha!=null && listaPessoaFolha.size()>0) {
				for(PessoaFolha pf: listaPessoaFolha) {
					if(p.getId().equals(pf.getPessoa().getId())) {
						p.getListaPessoaFolha().add(pf);
					}
				}
			}
			
			pessoas.add(p);
		}
		return pessoas;
	}
	
}
