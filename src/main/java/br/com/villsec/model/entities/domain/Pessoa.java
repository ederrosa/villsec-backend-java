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

import br.com.villsec.model.entities.domain.AutenticacaoSS;
import br.com.villsec.model.entities.domain.Email;
import br.com.villsec.model.entities.domain.Endereco;
import br.com.villsec.model.entities.domain.EntidadeDominio;
import br.com.villsec.model.entities.domain.Telefone;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DynamicInsert
public abstract class Pessoa extends EntidadeDominio {

	private static final long serialVersionUID = 1L;

	@Column(columnDefinition = "VARCHAR(100)")
	private String nome;

	@Column(columnDefinition = "VARCHAR(20)")
	private String genero;

	@Column(columnDefinition = "TINYINT(1) default '1'")
	private Boolean statusPessoa;
	
	private Date dataNascimento;

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "AUTENTICACAOSS_ID")
	private AutenticacaoSS theAutenticacaoSS;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "EMAIL_ID")
	private Email theEmail;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ENDERECO_ID")
	private Endereco theEndereco;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "PESSOA_ID")
	private Set<Telefone> theTelefones = new LinkedHashSet<Telefone>();

	public Pessoa() {

	}

	public Pessoa(Long id, String nome, String genero, Boolean statusPessoa, AutenticacaoSS theAutenticacaoSS,
			Email theEmail, Endereco theEndereco, Date dataNascimento) {
		super(id);
		this.nome = nome;
		this.genero = genero;
		this.statusPessoa = statusPessoa;
		this.theAutenticacaoSS = theAutenticacaoSS;
		this.theEmail = theEmail;
		this.theEndereco = theEndereco;
		this.dataNascimento = dataNascimento;

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Boolean getStatusPessoa() {
		return statusPessoa;
	}

	public void setStatusPessoa(Boolean statusPessoa) {
		this.statusPessoa = statusPessoa;
	}
	
	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public AutenticacaoSS getTheAutenticacaoSS() {
		return theAutenticacaoSS;
	}

	public void setTheAutenticacaoSS(AutenticacaoSS theAutenticacaoSS) {
		this.theAutenticacaoSS = theAutenticacaoSS;
	}

	public Email getTheEmail() {
		return theEmail;
	}

	public void setTheEmail(Email theEmail) {
		this.theEmail = theEmail;
	}

	public Endereco getTheEndereco() {
		return theEndereco;
	}

	public void setTheEndereco(Endereco theEndereco) {
		this.theEndereco = theEndereco;
	}

	public Set<Telefone> getTheTelefones() {
		return theTelefones;
	}

	public void setTheTelefones(Set<Telefone> theTelefones) {
		this.theTelefones = theTelefones;
	}

}
