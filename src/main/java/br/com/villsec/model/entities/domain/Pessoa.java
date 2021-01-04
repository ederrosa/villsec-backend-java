package br.com.villsec.model.entities.domain;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.DynamicInsert;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DynamicInsert
public abstract class Pessoa extends EntidadeDominio {

	private static final long serialVersionUID = 1L;

	private Date dataNascimento;

	@Column(columnDefinition = "VARCHAR(20)")
	private String genero;

	@Column(columnDefinition = "VARCHAR(100)")
	private String nome;

	@Column(columnDefinition = "TINYINT(1) default '1'")
	private Boolean statusPessoa;

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "AUTENTICACAOSS_ID")
	private AutenticacaoSS theAutenticacaoSS;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ENDERECO_ID")
	private Endereco theEndereco;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "EMAIL_ID")
	private Email theEmail;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "PESSOA_ID")
	private Set<Telefone> theTelefones = new LinkedHashSet<Telefone>();

	public Pessoa() {

	}

	public Pessoa(Long id, Date dataNascimento, String genero, String nome, Boolean statusPessoa,
			AutenticacaoSS theAutenticacaoSS, Endereco theEndereco, Email theEmail, Set<Telefone> theTelefones) {
		super(id);
		this.dataNascimento = dataNascimento;
		this.genero = genero;
		this.nome = nome;
		this.statusPessoa = statusPessoa;
		this.theAutenticacaoSS = theAutenticacaoSS;
		this.theEndereco = theEndereco;
		this.theEmail = theEmail;
		this.theTelefones = theTelefones;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getStatusPessoa() {
		return statusPessoa;
	}

	public void setStatusPessoa(Boolean statusPessoa) {
		this.statusPessoa = statusPessoa;
	}

	public AutenticacaoSS getTheAutenticacaoSS() {
		return theAutenticacaoSS;
	}

	public void setTheAutenticacaoSS(AutenticacaoSS theAutenticacaoSS) {
		this.theAutenticacaoSS = theAutenticacaoSS;
	}

	public Endereco getTheEndereco() {
		return theEndereco;
	}

	public void setTheEndereco(Endereco theEndereco) {
		this.theEndereco = theEndereco;
	}

	public Email getTheEmail() {
		return theEmail;
	}

	public void setTheEmail(Email theEmail) {
		this.theEmail = theEmail;
	}

	public Set<Telefone> getTheTelefones() {
		return theTelefones;
	}

	public void setTheTelefones(Set<Telefone> theTelefones) {
		this.theTelefones = theTelefones;
	}
}
