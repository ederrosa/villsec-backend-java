package br.com.villsec.model.services.dtos;

import br.com.villsec.model.entities.domain.EntidadeDominio;
import br.com.villsec.model.entities.domain.Imagem;

public class ImagemDTO extends EntidadeDominio {

	private static final long serialVersionUID = 1L;

	private String arquivoNome;
	private String arquivoUrl;
	private String descricao;
	private String titulo;

	public ImagemDTO() {

	}

	public ImagemDTO(Imagem theImagem) {
		super(theImagem.getId(), theImagem.getVerificationCode(), theImagem.getDtCriacao(),
				theImagem.getDtUltimaAlteracao());
		this.arquivoNome = theImagem.getTheArquivo().getNome();
		this.arquivoUrl = theImagem.getTheArquivo().getUrl().toString();
		this.descricao = theImagem.getDescricao();
		this.titulo = theImagem.getTitulo();
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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
