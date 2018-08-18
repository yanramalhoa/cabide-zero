package br.com.hackfest.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
@Table(name = "filiado")
@SequenceGenerator(name = "seqFiliado", sequenceName = "seq_Filiado", allocationSize = 1)
//@NamedQueries({
//	@NamedQuery(name = "Filiado.unicidade", query = "SELECT COUNT(u) FROM Filiado u WHERE (u.email = :email or u.login = :login or u.cpf = :cpf) ")
//})
public class Filiado extends EntidadeGenerica {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4355186167596728625L;

	@Id
	@Column(name = "ID_FILIADO")
	@GeneratedValue(generator = "seqFiliado", strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "DATA_EXTRACAO")
	@Temporal(TemporalType.DATE)
	private Date dataExtracao;
	
	@Column(name = "HORA_EXTRACAO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date horaExtracao;
	
	@Column(name = "NUMERO_INSCRICAO")
	private String numeroInscricao;

	@Column(name = "NOME")
	private String nome;
	

	@Column(name = "SIGLA_PARTIDO")
	private String siglaPartido;
	

	@Column(name = "NOME_PARTIDO")
	private String nomePartido;
	

	@Column(name = "UNIDADE_FEDERACAO")
	private String unidadeFederacao;

	@Column(name = "CODIGO_MUNICIPIO")
	private String codigoMunicipio;
	
	@Column(name = "NOME_MUNICIPIO")
	private String nomeMunicipio;
	
	@Column(name = "ZONA_ELEITORAL")
	private String zonaEleitoral;
	
	@Column(name = "SESSAO_ELEITORAL")
	private String sessaoEleitoral;
	
	@Column(name = "DATA_FILIACAO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFiliacao;
	
	@Column(name = "SITUACAO_REGISTRO_FILIACAO")
	private String situacaoRegistroFiliacao;
	
	@Column(name = "TIPO_REGISTRO_FILIACAO")
	private String tipoRegistroFiliacao;
	
	@Column(name = "DATA_PROCES_REGISTRO_FILIACAO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataProcesRegistroFiliacao;
	
	@Column(name = "DATA_DESFILIACAO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataDesfiliacao;
	
	@Column(name = "DATA_CANCELAR_REGISTRO_DESFILIACAO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCancelarRegistroDesfiliacao;
	
	@Column(name = "DATA_REGULARIZACAO_FILIACAO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataRegularizacaoDfiliacao;
	
	@Column(name = "MOTIVO_CANCELAMENTO")
	private String motivoCancelamento;
	
	
	
	public Filiado() {

	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(obj instanceof Filiado)
			if(((Filiado)obj).getId().equals(id))
				return true;
		return false;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataExtracao() {
		return dataExtracao;
	}

	public void setDataExtracao(Date dataExtracao) {
		this.dataExtracao = dataExtracao;
	}

	public Date getHoraExtracao() {
		return horaExtracao;
	}

	public void setHoraExtracao(Date horaExtracao) {
		this.horaExtracao = horaExtracao;
	}

	public String getNumeroInscricao() {
		return numeroInscricao;
	}

	public void setNumeroInscricao(String numeroInscricao) {
		this.numeroInscricao = numeroInscricao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSiglaPartido() {
		return siglaPartido;
	}

	public void setSiglaPartido(String siglaPartido) {
		this.siglaPartido = siglaPartido;
	}

	public String getNomePartido() {
		return nomePartido;
	}

	public void setNomePartido(String nomePartido) {
		this.nomePartido = nomePartido;
	}

	public String getUnidadeFederacao() {
		return unidadeFederacao;
	}

	public void setUnidadeFederacao(String unidadeFederacao) {
		this.unidadeFederacao = unidadeFederacao;
	}

	public String getCodigoMunicipio() {
		return codigoMunicipio;
	}

	public void setCodigoMunicipio(String codigoMunicipio) {
		this.codigoMunicipio = codigoMunicipio;
	}

	public String getNomeMunicipio() {
		return nomeMunicipio;
	}

	public void setNomeMunicipio(String nomeMunicipio) {
		this.nomeMunicipio = nomeMunicipio;
	}

	public String getZonaEleitoral() {
		return zonaEleitoral;
	}

	public void setZonaEleitoral(String zonaEleitoral) {
		this.zonaEleitoral = zonaEleitoral;
	}

	public String getSessaoEleitoral() {
		return sessaoEleitoral;
	}

	public void setSessaoEleitoral(String sessaoEleitoral) {
		this.sessaoEleitoral = sessaoEleitoral;
	}

	public Date getDataFiliacao() {
		return dataFiliacao;
	}

	public void setDataFiliacao(Date dataFiliacao) {
		this.dataFiliacao = dataFiliacao;
	}

	public String getSituacaoRegistroFiliacao() {
		return situacaoRegistroFiliacao;
	}

	public void setSituacaoRegistroFiliacao(String situacaoRegistroFiliacao) {
		this.situacaoRegistroFiliacao = situacaoRegistroFiliacao;
	}

	public String getTipoRegistroFiliacao() {
		return tipoRegistroFiliacao;
	}

	public void setTipoRegistroFiliacao(String tipoRegistroFiliacao) {
		this.tipoRegistroFiliacao = tipoRegistroFiliacao;
	}

	public Date getDataProcesRegistroFiliacao() {
		return dataProcesRegistroFiliacao;
	}

	public void setDataProcesRegistroFiliacao(Date dataProcesRegistroFiliacao) {
		this.dataProcesRegistroFiliacao = dataProcesRegistroFiliacao;
	}

	public Date getDataDesfiliacao() {
		return dataDesfiliacao;
	}

	public void setDataDesfiliacao(Date dataDesfiliacao) {
		this.dataDesfiliacao = dataDesfiliacao;
	}

	public Date getDataCancelarRegistroDesfiliacao() {
		return dataCancelarRegistroDesfiliacao;
	}

	public void setDataCancelarRegistroDesfiliacao(Date dataCancelarRegistroDesfiliacao) {
		this.dataCancelarRegistroDesfiliacao = dataCancelarRegistroDesfiliacao;
	}

	public Date getDataRegularizacaoDfiliacao() {
		return dataRegularizacaoDfiliacao;
	}

	public void setDataRegularizacaoDfiliacao(Date dataRegularizacaoDfiliacao) {
		this.dataRegularizacaoDfiliacao = dataRegularizacaoDfiliacao;
	}

	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}
	
	
	
}
