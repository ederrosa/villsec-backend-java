package br.com.villsec.model.entities.domain;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.DynamicInsert;

@Entity
@DynamicInsert
public class Album extends EntidadeDominio {

	private static final long serialVersionUID = 1L;
	private String ano;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "File_ID")
	private Arquivo capa;
	private String codigo;
	private String descricao;
	private String genero;
	private String nome;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "MUSICA_ID")
	private Set<Musica> theMusicas = new LinkedHashSet<Musica>();

	public Album() {
	}

	public Album(Long id, String ano, Arquivo capa, String codigo, String descricao, String genero, String nome, Set<Musica> theMusicas) {
		super(id);
		this.ano = ano;
		this.capa = capa;
		this.codigo = codigo;
		this.descricao = descricao;
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

	public Arquivo getCapa() {
		return capa;
	}

	public void setCapa(Arquivo capa) {
		this.capa = capa;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
