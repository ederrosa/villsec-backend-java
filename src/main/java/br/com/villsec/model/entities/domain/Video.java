package br.com.villsec.model.entities.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.DynamicInsert;

@Entity
@DynamicInsert
public class Video extends EntidadeDominio {

	private static final long serialVersionUID = 1L;

	private String descricao;
	private String embed;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "File_ID")
	private Arquivo theArquivo;
	private String titulo;

	public Video() {
	}

	public Video(Long id, String descricao, String embed, Arquivo theArquivo, String titulo) {
		super(id);
		this.descricao = descricao;
		this.embed = embed;
		this.theArquivo = theArquivo;
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getEmbed() {
		return embed;
	}

	public void setEmbed(String embed) {
		this.embed = embed;
	}

	public Arquivo getTheArquivo() {
		return theArquivo;
	}

	public void setTheArquivo(Arquivo theArquivo) {
		this.theArquivo = theArquivo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
