package br.com.villsec.model.services.dtos;

import br.com.villsec.model.entities.domain.EntidadeDominio;
import br.com.villsec.model.entities.domain.Galeria;

public class GaleriaDTO extends EntidadeDominio {

	private static final long serialVersionUID = 1L;

	private String descricao;
	private Boolean status;
	private String titulo;

	public GaleriaDTO() {

	}

	public GaleriaDTO(Galeria theGaleria) {
		super(theGaleria.getId(), theGaleria.getVerificationCode(), theGaleria.getDtCriacao(),
				theGaleria.getDtUltimaAlteracao());
		this.descricao = theGaleria.getDescricao();
		this.status = theGaleria.isStatus();
		this.titulo = theGaleria.getTitulo();
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
