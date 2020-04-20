package br.com.villsec.model.services.dtos;

import br.com.villsec.model.entities.domain.Album;
import br.com.villsec.model.entities.domain.EntidadeDominio;

public class AlbumDTO extends EntidadeDominio{

	private static final long serialVersionUID = 1L;

	private String ano;
	private String capaNome;
	private String capaUrl;
	private String codigo;
	private String genero;
	private String nome;

	public AlbumDTO() {
		
	}
	
	public AlbumDTO(Album theAlbum) {
		super(
				theAlbum.getId(),
				theAlbum.getVerificationCode(), 
				theAlbum.getDtCriacao(),
				theAlbum.getDtUltimaAlteracao());
		this.ano = theAlbum.getAno();
		this.capaNome = theAlbum.getCapa().getNome();
		this.capaUrl = theAlbum.getCapa().getUrl().toString();
		this.codigo = theAlbum.getCodigo();
		this.genero = theAlbum.getGenero();
		this.nome = theAlbum.getNome();
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getCapaNome() {
		return capaNome;
	}

	public void setCapaNome(String capaNome) {
		this.capaNome = capaNome;
	}

	public String getCapaUrl() {
		return capaUrl;
	}

	public void setCapaUrl(String capaUrl) {
		this.capaUrl = capaUrl;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}	
}
