package br.com.villsec.model.entities.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	@ManyToOne
	@JoinColumn(name = "GALERIA_ID")
	private Galeria theGaleria;
	private String titulo;

	public Video() {
	}

	public Video(Long id, String descricao, String embed, Arquivo theArquivo, Galeria theGaleria, String titulo) {
		super(id);
		this.descricao = descricao;
		this.embed = embed;
		this.theArquivo = theArquivo;
		this.theGaleria = theGaleria;
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

	public Galeria getTheGaleria() {
		return theGaleria;
	}

	public void setTheGaleria(Galeria theGaleria) {
		this.theGaleria = theGaleria;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
