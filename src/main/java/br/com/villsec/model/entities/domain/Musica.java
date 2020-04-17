package br.com.villsec.model.entities.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.DynamicInsert;

@Entity
@DynamicInsert
public class Musica extends EntidadeDominio {

	private static final long serialVersionUID = 1L;

	private String nome;
	private Integer bpm;
	private String autor;
	private String coautor;
	private Integer duracao;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "File_ID")
	private File arquivo;
	private Boolean copyright;
	private String idioma;

	public Musica() {

	}

	public Musica(Long id, String nome, Integer bpm, String autor, String coautor, Integer duracao, File arquivo,
			Boolean copyright, String idioma) {
		super(id);
		this.nome = nome;
		this.bpm = bpm;
		this.autor = autor;
		this.coautor = coautor;
		this.duracao = duracao;
		this.arquivo = arquivo;
		this.copyright = copyright;
		this.idioma = idioma;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getBpm() {
		return bpm;
	}

	public void setBpm(Integer bpm) {
		this.bpm = bpm;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getCoautor() {
		return coautor;
	}

	public void setCoautor(String coautor) {
		this.coautor = coautor;
	}

	public Integer getDuracao() {
		return duracao;
	}

	public void setDuracao(Integer duracao) {
		this.duracao = duracao;
	}

	public File getArquivo() {
		return arquivo;
	}

	public void setArquivo(File arquivo) {
		this.arquivo = arquivo;
	}

	public Boolean getCopyright() {
		return copyright;
	}

	public void setCopyright(Boolean copyright) {
		this.copyright = copyright;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
}
