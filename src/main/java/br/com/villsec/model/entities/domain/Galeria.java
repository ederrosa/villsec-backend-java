package br.com.villsec.model.entities.domain;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.DynamicInsert;

@Entity
@DynamicInsert
public class Galeria extends EntidadeDominio {

	private static final long serialVersionUID = 1L;

	private String descricao;
	@Column(columnDefinition = "TINYINT(1) default '1'")
	private boolean status;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "IMAGEM_ID")
	private Set<Imagem> theImagens = new LinkedHashSet<Imagem>();
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "VIDEO_ID")
	private Set<Video> theVideos = new LinkedHashSet<Video>();
	@Column(columnDefinition = "VARCHAR(100)")
	private String titulo;

	public Galeria() {

	}

	public Galeria(Long id, String descricao, boolean status, Set<Imagem> theImagens, Set<Video> theVideos,
			String titulo) {
		super(id);
		this.descricao = descricao;
		this.status = status;
		this.theImagens = theImagens;
		this.theVideos = theVideos;
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Set<Imagem> getTheImagens() {
		return theImagens;
	}

	public void setTheImagens(Set<Imagem> theImagens) {
		this.theImagens = theImagens;
	}

	public Set<Video> getTheVideos() {
		return theVideos;
	}

	public void setTheVideo(Set<Video> theVideos) {
		this.theVideos = theVideos;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
