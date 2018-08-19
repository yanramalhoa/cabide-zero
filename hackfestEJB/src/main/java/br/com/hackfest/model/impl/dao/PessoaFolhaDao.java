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
		"	pf.id, p.id, p.cpf, p.nome as pessoa, ug.id, ug.nome, c.id,c.nome , tr.id, tr.descricao , pf.vantagem " + 
		"From PessoaFolha pf " + 
		"Inner Join pf.pessoa p " + 
		"Inner Join pf.cargo c  " + 
		"Inner Join c.unidadeGestora ug  " + 
		"Inner Join c.tipoRegime tr  " + 
		" " + 
		"Where 1=1 " + 
		"and exists (Select 1 "  +
		"	    From FiliacaoPartidaria fp_2 " +
		"	    Inner Join fp_2.pessoa p_2 " +
		"	    Where p_2.id = p.id) " +
		"and   pf.ano =  " + ano + " " + 
		"and   pf.mes =  " + mes + " " + 
		"and   p.id in (Select p_1.id " + 
		"	    From PessoaFolha pf_1 " + 
		"	    Inner Join pf_1.pessoa p_1  " + 
		"	    Inner Join pf_1.cargo c_1  " + 
		"	    Inner Join c_1.tipoRegime tr_1  " + 
		"	    Where 1=1 " +
		//"       and p_1.id = p.id " +
		"		and exists (Select 1 "  +
		"	    From FiliacaoPartidaria fp_3 " +
		"	    Inner Join fp_3.pessoa p_3 " +
		"	    Where p_3.id = p_1.id) " +
		"       and pf_1.ano = " + ano + " " +
		"       and pf_1.mes = " + mes + " " +
		"	    and tr_1.descricao not in ('Inativos / Pensionistas', 'Benefício previdênciário temporário') " + 
		"	    and c_1.nome not like '%PROF.%' " + 
		"	    and c_1.nome not like '%PROFESSOR%' " + 
		"	    and c_1.nome not like '%MEDICO%' " + 
		"	    and c_1.nome not like '%ENFERM%' " + 
		"	    and c_1.nome not like '%NUTRIC%' " + 
		"	    and c_1.nome not like '%FONO%' " + 
		"	    and c_1.nome not like '%FISIO%' " + 
		"	    and c_1.nome not like '%ODONTO%' " + 
		"	    and c_1.nome not like '%DENTIST%' " + 
		"	    and c_1.nome not like '%FARMAC%' " + 
		"	    Group By p_1.id, p_1.cpf " + 
		"	    Having count(p_1.id)>1 " + 
		")   " + 
		"and not exists(Select 1 " + 
		"		From PessoaFolha pf_1 " + 
		"		Inner Join pf_1.cargo c_1  " + 
		"		Where pf_1.pessoa = pf.pessoa " + 
		"		and c_1.nome like '%PROF.%' " + 
		"		and c_1.nome like '%PROFESSOR%' " + 
		"	        and c_1.nome not like '%MEDICO%' " + 
		"	        and c_1.nome not like '%ENFERM%' " + 
		"	        and c_1.nome not like '%ENFERM%' " + 
		"	        and c_1.nome not like '%NUTRIC%' " + 
		"	        and c_1.nome not like '%FONO%' " + 
		"	        and c_1.nome not like '%FISIO%' " + 
		"	        and c_1.nome not like '%ODONTO%' " + 
		"	        and c_1.nome not like '%DENTIST%' " + 
		"	        and c_1.nome not like '%FARMAC%' " + 
		"	    ) " +
		"Order By pf.id, p.cpf " + 
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
	
	@Override
	public List<Pessoa> listaPessoasComVinculo(Integer mes, Integer ano) throws CabideException {
		List<Pessoa> pessoas = new ArrayList<>();
		String sql =
		"Select  " + 
		"	p.id, p.cpf, p.nome as pessoa, sum(pf.vantagem) as total_vantagem, count(p.id) as total_por_pessoa " + 
		"From PessoaFolha pf " + 
		"Inner Join pf.pessoa p " + 
		"Inner Join pf.cargo c  " + 
		"Inner Join c.unidadeGestora ug  " + 
		"Inner Join c.tipoRegime tr  " + 
		" " + 
		"Where 1=1 " + 
		"and exists (Select 1 "  +
		"	    From FiliacaoPartidaria fp_2 " +
		"	    Inner Join fp_2.pessoa p_2 " +
		"	    Where p_2.id = p.id) " +
		"and   pf.ano =  " + ano + " " + 
		"and   pf.mes =  " + mes + " " + 
		"and   p.id in (Select p_1.id " + 
		"	    From PessoaFolha pf_1 " + 
		"	    Inner Join pf_1.pessoa p_1  " + 
		"	    Inner Join pf_1.cargo c_1  " + 
		"	    Inner Join c_1.tipoRegime tr_1  " + 
		"	    Where 1=1 " +
		//"       and p_1.id = p.id " +
		"		and exists (Select 1 "  +
		"	    From FiliacaoPartidaria fp_3 " +
		"	    Inner Join fp_3.pessoa p_3 " +
		"	    Where p_3.id = p_1.id) " +
		"       and pf_1.ano = " + ano + " " +
		"       and pf_1.mes = " + mes + " " +
		"	    and tr_1.descricao not in ('Inativos / Pensionistas', 'Benefício previdênciário temporário') " + 
		"	    and c_1.nome not like '%PROF.%' " + 
		"	    and c_1.nome not like '%PROFESSOR%' " + 
		"	    and c_1.nome not like '%MEDICO%' " + 
		"	    and c_1.nome not like '%ENFERM%' " + 
		"	    and c_1.nome not like '%NUTRIC%' " + 
		"	    and c_1.nome not like '%FONO%' " + 
		"	    and c_1.nome not like '%FISIO%' " + 
		"	    and c_1.nome not like '%ODONTO%' " + 
		"	    and c_1.nome not like '%DENTIST%' " + 
		"	    and c_1.nome not like '%FARMAC%' " + 
		"	    Group By p_1.id, p_1.cpf " + 
		"	    Having count(p_1.id)>1 " + 
		")   " + 
		"and not exists(Select 1 " + 
		"		From PessoaFolha pf_1 " + 
		"		Inner Join pf_1.cargo c_1  " + 
		"		Where pf_1.pessoa = pf.pessoa " + 
		"		and c_1.nome like '%PROF.%' " + 
		"		and c_1.nome like '%PROFESSOR%' " + 
		"	        and c_1.nome not like '%MEDICO%' " + 
		"	        and c_1.nome not like '%ENFERM%' " + 
		"	        and c_1.nome not like '%ENFERM%' " + 
		"	        and c_1.nome not like '%NUTRIC%' " + 
		"	        and c_1.nome not like '%FONO%' " + 
		"	        and c_1.nome not like '%FISIO%' " + 
		"	        and c_1.nome not like '%ODONTO%' " + 
		"	        and c_1.nome not like '%DENTIST%' " + 
		"	        and c_1.nome not like '%FARMAC%' " + 
		"	    ) " +
		"Group By p.id, p.cpf, p.nome " +
		"Order By p.nome " + 
		" ";

		PessoaFolha pessoafOlha = new PessoaFolha();
		pessoafOlha.setMes(mes);
		pessoafOlha.setAno(ano);
		
		List<Object[]> rows =  this.consultarPorQueryRetornaListaObjetos(pessoafOlha, sql);
		
		List<FiliacaoPartidaria> listaFP = filiacaoPartidariaBusiness.listaFiliacaoPartidaria();

		PessoaFolha pessoaFolha = new PessoaFolha();
		pessoaFolha.setMes(mes);
		pessoaFolha.setAno(ano);
		
		List<PessoaFolha> listaPessoaFolha = this.listaFolha(pessoaFolha);
		
		for(Object[] object : rows){
			Pessoa p = new Pessoa();
			p.setId((Long) object[0]); 
			p.setCpf((String) object[1]); 
			p.setNome((String) object[2]); 
			p.setTotalVantagem((BigDecimal) object[3]); 
			p.setQtdTotalPorPessoa((Long) object[4]); 
			
			if(listaFP!=null && listaFP.size()>0) {
				for(FiliacaoPartidaria fp: listaFP) {
					if(p.getId().equals(fp.getPessoa().getId())) {
						p.getListaFiliacaoPartidaria().add(fp);
					}
				}
			}
			if(listaPessoaFolha!=null && listaPessoaFolha.size()>0) {
				for(PessoaFolha pf: listaPessoaFolha) {
					if(pf.getPessoa()!=null && pf.getPessoa().getId()!=null)
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
