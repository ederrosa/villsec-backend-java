package br.com.villsec.model.entities.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.DynamicInsert;

@Entity
@DynamicInsert
public class Musica extends EntidadeDominio {

	private static final long serialVersionUID = 1L;

	private String autor;
	private String nome;
	private Integer bpm;
	private String coautor;
	private Boolean copyright;
	private String duracao;
	private Integer faixa;
	private String idioma;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "File_ID")
	private Arquivo theArquivo;
	@ManyToOne
	@JoinColumn(name = "ALBUM_ID")
	private Album theAlbum;

	public Musica() {

	}

	public Musica(Long id, String autor, Integer bpm, String coautor, Boolean copyright, String duracao, Integer faixa,
			String idioma, String nome, Arquivo theArquivo, Album theAlbum) {
		super(id);
		this.autor = autor;
		this.nome = nome;
		this.bpm = bpm;
		this.coautor = coautor;
		this.copyright = copyright;
		this.duracao = duracao;
		this.faixa = faixa;
		this.idioma = idioma;
		this.theArquivo = theArquivo;
		this.theAlbum = theAlbum;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
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

	public String getCoautor() {
		return coautor;
	}

	public void setCoautor(String coautor) {
		this.coautor = coautor;
	}

	public Boolean getCopyright() {
		return copyright;
	}

	public void setCopyright(Boolean copyright) {
		this.copyright = copyright;
	}

	public String getDuracao() {
		return duracao;
	}

	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}

	public Integer getFaixa() {
		return faixa;
	}

	public void setFaixa(Integer faixa) {
		this.faixa = faixa;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public Arquivo getTheArquivo() {
		return theArquivo;
	}

	public void setTheArquivo(Arquivo theArquivo) {
		this.theArquivo = theArquivo;
	}

	public Album getTheAlbum() {
		return theAlbum;
	}

	public void setTheAlbum(Album theAlbum) {
		this.theAlbum = theAlbum;
	}
}
