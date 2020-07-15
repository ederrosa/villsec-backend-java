package br.com.villsec.model.entities.domain;

import java.net.URI;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
public class Arquivo extends EntidadeDominio {

	private static final long serialVersionUID = 1L;

	private String nome;
	@Lob
	@Column(columnDefinition = "BLOB")
	private URI url;

	public Arquivo() {

	}

	public Arquivo(Long id, String nome, URI url) {
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
