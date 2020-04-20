package br.com.villsec.model.services.dtos;

import br.com.villsec.model.entities.domain.EntidadeDominio;
import br.com.villsec.model.entities.domain.Musica;

public class MusicaDTO extends EntidadeDominio {

	private static final long serialVersionUID = 1L;

	private String nome;
	private String bpm;
	private String autor;
	private String coautor;
	private String duracao;
	private String arquivoNome;
	private String arquivoUrl;
	private Boolean copyright;
	private String idioma;
	
	public MusicaDTO() {
		
	}
	
	public MusicaDTO(Musica theMusica) {
		super(
				theMusica.getId(),
				theMusica.getVerificationCode(), 
				theMusica.getDtCriacao(),
				theMusica.getDtUltimaAlteracao());
		this.nome = theMusica.getNome();
		this.bpm = theMusica.getBpm().toString();
		this.autor = theMusica.getAutor();
		this.coautor = theMusica.getCoautor();
		this.duracao = theMusica.getDuracao().toString();
		this.arquivoNome = theMusica.getArquivo().getNome();
		this.arquivoUrl = theMusica.getArquivo().getUrl().toString();
		this.copyright = theMusica.getCopyright();
		this.idioma = theMusica.getIdioma();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getBpm() {
		return bpm;
	}

	public void setBpm(String bpm) {
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

	public String getDuracao() {
		return duracao;
	}

	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}

	public String getArquivoNome() {
		return arquivoNome;
	}

	public void setArquivoNome(String arquivoNome) {
		this.arquivoNome = arquivoNome;
	}

	public String getArquivoUrl() {
		return arquivoUrl;
	}

	public void setArquivoUrl(String arquivoUrl) {
		this.arquivoUrl = arquivoUrl;
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
