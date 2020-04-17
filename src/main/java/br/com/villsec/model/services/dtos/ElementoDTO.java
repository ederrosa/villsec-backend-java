package br.com.villsec.model.services.dtos;

import br.com.villsec.model.entities.domain.EntidadeDominio;
import br.com.villsec.model.entities.domain.Elemento;


public class ElementoDTO extends EntidadeDominio {

	private static final long serialVersionUID = 1L;

	private String descricao;
	private String elementoNome;
	private String elementoUrl;
	private String tipoElemento;
	private String titulo;
	private Boolean status;
	
	public ElementoDTO() {
		
	}

	public ElementoDTO(Elemento theElemento) {
		super(
				theElemento.getId(),
				theElemento.getVerificationCode(), 
				theElemento.getDtCriacao(),
				theElemento.getDtUltimaAlteracao());
		this.descricao = theElemento.getDescricao();
		this.elementoNome = theElemento.getElemento().getNome();
		this.elementoUrl = theElemento.getElemento().getUrl().toString();
		this.tipoElemento = theElemento.getTipoElemento().getDescricao();
		this.titulo = theElemento.getTitulo();
		this.status = theElemento.getStatus();
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getElementoNome() {
		return elementoNome;
	}

	public void setElementoNome(String elementoNome) {
		this.elementoNome = elementoNome;
	}

	public String getElementoUrl() {
		return elementoUrl;
	}

	public void setElementoUrl(String elementoUrl) {
		this.elementoUrl = elementoUrl;
	}

	public String getTipoElemento() {
		return tipoElemento;
	}

	public void setTipoElemento(String tipoElemento) {
		this.tipoElemento = tipoElemento;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}	
}
