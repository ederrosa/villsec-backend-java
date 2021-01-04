package br.com.villsec.model.entities.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.DynamicInsert;

@Entity
@DynamicInsert
public class Imagem extends EntidadeDominio {

	private static final long serialVersionUID = 1L;

	private String descricao;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "File_ID")
	private Arquivo theArquivo;
	@Column(columnDefinition = "VARCHAR(100)")
	private String titulo;

	public Imagem() {
	}

	public Imagem(Long id, String descricao, Arquivo theArquivo, String titulo) {
		super(id);
		this.descricao = descricao;
		this.theArquivo = theArquivo;
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
