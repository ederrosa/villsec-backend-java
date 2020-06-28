package br.com.villsec.model.services.dtos;

import br.com.villsec.model.entities.domain.EntidadeDominio;
import br.com.villsec.model.entities.domain.Video;


public class VideoDTO extends EntidadeDominio {

	private static final long serialVersionUID = 1L;

	private String arquivoNome;
	private String arquivoUrl;
	private String descricao;
	private String embed;
	private String tipoVideo;
	private String titulo;
		
	public VideoDTO() {
		
	}

	public VideoDTO(Video theVideo) {
		super(
				theVideo.getId(),
				theVideo.getVerificationCode(), 
				theVideo.getDtCriacao(),
				theVideo.getDtUltimaAlteracao());
		this.descricao = theVideo.getDescricao();
		if(theVideo.getTheArquivo() !=  null) {
			this.arquivoNome = theVideo.getTheArquivo().getNome();
			this.arquivoUrl = theVideo.getTheArquivo().getUrl().toString();
		}		
		this.embed = theVideo.getEmbed();
		this.titulo = theVideo.getTitulo();		
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

	public String getTipoVideo() {
		return tipoVideo;
	}

	public void setTipoVideo(String tipoVideo) {
		this.tipoVideo = tipoVideo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}	
}
