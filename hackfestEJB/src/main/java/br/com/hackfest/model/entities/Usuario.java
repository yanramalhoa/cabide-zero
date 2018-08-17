package br.com.hackfest.model.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.hackfest.controller.annotation.StringKey;

@Entity
@Table(name = "usuario")
@SequenceGenerator(name = "seqUsuario", sequenceName = "seq_Usuario", allocationSize = 1)
@NamedQueries({
	@NamedQuery(name = "Usuario.unicidade", query = "SELECT COUNT(u) FROM Usuario u WHERE (u.email = :email or u.login = :login or u.cpf = :cpf) ")
})
public class Usuario extends EntidadeGenerica {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4355186167596728625L;

	@Id
	@Column(name = "ID_USUARIO")
	@GeneratedValue(generator = "seqUsuario", strategy = GenerationType.AUTO)
	private Long id;
	
	
	
	@StringKey
	@Column(name = "LOGIN", nullable = false, length = 255)
	private String login;
	
	@Column(name = "NOME_USUARIO", nullable = false, length = 255)
	private String nome;
	
	@Column(name = "SENHA", nullable = false, length = 255)
	private String senha;
	
	@Column(name = "EMAIL", nullable = false)
	private String email;
	
	@Column(name = "CELULAR", nullable = false)
	private String celular;
	
	@Column(name = "FORCAR_TROCA_SENHA")
	private Boolean forcarTrocaSenha;
    
    @Column(name="USUARIO_ADMINISTRADOR")
    private Boolean usuarioAdministrador;
	
	@Column(name = "ULTIMO_ACESSO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date ultimoAcesso;
	
	@Column(name="DATA_NASCIMENTO")
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	
	@Transient
	private String dataNascimentoCadastro;
	
	@Column(name="CPF" , nullable = false)
	private String cpf;
	
	
	@Column(name="ID_ULTIMO_ACESSO")
	private String ipUltimoAcesso;
	
	@Transient
	private String senhaAnterior;
	
    @Column(name="NUMERO_VITORIAS")
	private Integer numeroVitorias;
    
	@Column(name="COTACAO_TOTAL_VITORIAS")
	private BigDecimal cotacaoTotalVitorias;
	
    @Transient
    private Integer posicao;
    
    @Column(name="EMAIL_VERIFICADO")
	private Boolean emailVerificado;
    
    @Column(name="APELIDO")
	private String apelido;
    
    @Column(name="DATA_CADASTRO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;
    
    @Transient
    private String senhaAtual;
    
    //atributo para autenticacao do cadastro do usuario
    @Column(name="HASH_CADASTRO")
    private String hashCadastro;
   
    //recuperar senha
	@Column(name="ESQUECEU_SENHA" )
	private Boolean esqueceuSenha;
    
	public Usuario() {
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
		if(obj instanceof Usuario)
			if(((Usuario)obj).getId().equals(id))
				return true;
		return false;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Boolean getForcarTrocaSenha() {
		return forcarTrocaSenha;
	}
	public void setForcarTrocaSenha(Boolean forcarTrocaSenha) {
		this.forcarTrocaSenha = forcarTrocaSenha;
	}
	public String getSenhaAnterior() {
		return senhaAnterior;
	}
	public void setSenhaAnterior(String senhaAnterior) {
		this.senhaAnterior = senhaAnterior;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getIpUltimoAcesso() {
		return ipUltimoAcesso;
	}

	public void setIpUltimoAcesso(String ipUltimoAcesso) {
		this.ipUltimoAcesso = ipUltimoAcesso;
	}

	public Date getUltimoAcesso() {
		return ultimoAcesso;
	}

	public void setUltimoAcesso(Date ultimoAcesso) {
		this.ultimoAcesso = ultimoAcesso;
	}

	public Boolean getUsuarioAdministrador() {
		return usuarioAdministrador;
	}

	public void setUsuarioAdministrador(Boolean usuarioAdministrador) {
		this.usuarioAdministrador = usuarioAdministrador;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Boolean getEmailVerificado() {
		return emailVerificado;
	}

	public void setEmailVerificado(Boolean emailVerificado) {
		this.emailVerificado = emailVerificado;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	
	}	
		public String getDataNascimentoCadastro() {
		return dataNascimentoCadastro;
	}

	public void setDataNascimentoCadastro(String dataNascimentoCadastro) {
		this.dataNascimentoCadastro = dataNascimentoCadastro;
	}


	public String getHashCadastro() {
		return hashCadastro;
	}

	public void setHashCadastro(String hashCadastro) {
		this.hashCadastro = hashCadastro;
	}
	
	public String getNomeTopBar() {
		String nomeEditado = nome;
		if(nomeEditado != null && nomeEditado.length() > 10) {
			
			nomeEditado = nomeEditado.substring(0, 10);
			nomeEditado = nomeEditado.concat("...");
		}
		return nomeEditado;
	}
	
	public String getNomeRanking() {
		String nomeEditado = nome;
		if(nomeEditado != null && nomeEditado.length() > 10) {
			String[] slitNome = nomeEditado.split(" ");
			nomeEditado = slitNome[0];
			if(slitNome[1] != null) {
				nomeEditado = nomeEditado.concat(" ").concat(slitNome[1]);
			}
		}
		return nomeEditado;
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public Boolean getEsqueceuSenha() {
		return esqueceuSenha;
	}

	public void setEsqueceuSenha(Boolean esqueceuSenha) {
		this.esqueceuSenha = esqueceuSenha;
	}

	public BigDecimal getCotacaoTotalVitorias() {
		return cotacaoTotalVitorias;
	}

	public void setCotacaoTotalVitorias(BigDecimal cotacaoTotalVitorias) {
		this.cotacaoTotalVitorias = cotacaoTotalVitorias;
	}

	public Integer getNumeroVitorias() {
		return numeroVitorias;
	}

	public void setNumeroVitorias(Integer numeroVitorias) {
		this.numeroVitorias = numeroVitorias;
	}

	public Integer getPosicao() {
		return posicao;
	}

	public void setPosicao(Integer posicao) {
		this.posicao = posicao;
	}
	
	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}
	
}
