package br.com.villsec.model.entities.domain;

import java.net.URI;
import java.util.Date;

import javax.persistence.Entity;

import org.hibernate.annotations.DynamicInsert;

@Entity
@DynamicInsert
public class Elemento extends EntidadeDominio {

	private static final long serialVersionUID = 1L;

	private String descricao;
	private URI elemento;
	private Integer tipo;
	private String titulo;

	public Elemento() {

	}

	public Elemento(Long id, Long verificationCode, Date dtCriacao, Date dtUltimaAlteracao, String descricao,
			URI elemento, Integer tipo, String titulo) {
		super(id, verificationCode, dtCriacao, dtUltimaAlteracao);
		this.descricao = descricao;
		this.elemento = elemento;
		this.tipo = tipo;
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public URI getElemento() {
		return elemento;
	}

	public void setElemento(URI elemento) {
		this.elemento = elemento;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
