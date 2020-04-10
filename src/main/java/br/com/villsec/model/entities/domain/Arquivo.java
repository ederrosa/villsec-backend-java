package br.com.villsec.model.entities.domain;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.DynamicInsert;

@Entity
@DynamicInsert
public class Arquivo extends EntidadeDominio {

	private static final long serialVersionUID = 1L;

	private String nome;
	private Boolean status;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "ARQUIVO_ID")
	private Set<Elemento> theElementos = new LinkedHashSet<Elemento>();

	public Arquivo() {

	}

	public Arquivo(Long id, Long verificationCode, Date dtCriacao, Date dtUltimaAlteracao, String nome, Boolean status,
			Set<Elemento> theElementos) {
		super(id, verificationCode, dtCriacao, dtUltimaAlteracao);
		this.nome = nome;
		this.status = status;
		this.theElementos = theElementos;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Set<Elemento> getTheElementos() {
		return theElementos;
	}

	public void setTheElementos(Set<Elemento> theElementos) {
		this.theElementos = theElementos;
	}	
}
