package br.com.villsec.model.entities.domain;

import java.net.URI;
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
public class Album extends EntidadeDominio{

	private static final long serialVersionUID = 1L;
	private String ano;
	private URI capa;
	private String codigo;
	private String genero;
	private String nome;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "ALBUM_ID")
	private Set<Musica> theMusicas = new LinkedHashSet<Musica>();

	public Album() {
	}

	public Album(Long id, Long verificationCode, Date dtCriacao, Date dtUltimaAlteracao, String ano, URI capa,
			String codigo, String genero, String nome, Set<Musica> theMusicas) {
		super(id, verificationCode, dtCriacao, dtUltimaAlteracao);
		this.ano = ano;
		this.capa = capa;
		this.codigo = codigo;
		this.genero = genero;
		this.nome = nome;
		this.theMusicas = theMusicas;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public URI getCapa() {
		return capa;
	}

	public void setCapa(URI capa) {
		this.capa = capa;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
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

	public Set<Musica> getTheMusicas() {
		return theMusicas;
	}

	public void setTheMusicas(Set<Musica> theMusicas) {
		this.theMusicas = theMusicas;
	}	

}
