package br.com.villsec.model.entities.domain;

import java.net.URI;

import javax.persistence.Entity;

@Entity
public class File extends EntidadeDominio {

	private static final long serialVersionUID = 1L;

	private String nome;
	private URI url;

	public File() {

	}

	public File(Long id, String nome, URI url) {
		super(id);
		this.nome = nome;
		this.url = url;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public URI getUrl() {
		return url;
	}

	public void setUrl(URI url) {
		this.url = url;
	}
}
