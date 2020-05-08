package br.com.villsec.model.entities.domain;

import java.net.URI;
import java.util.Date;

import javax.persistence.Entity;

import org.hibernate.annotations.DynamicInsert;

@Entity
@DynamicInsert
public class Proprietario extends Pessoa {

	private static final long serialVersionUID = 1L;

	private String sobreMim;
	private URI facebook;
	private URI instagram;
	private URI spotyfy;
	private URI twitter;
	private URI twitch;

	public Proprietario() {

	}

	public Proprietario(Long id, String nome, String genero, Boolean statusPessoa, AutenticacaoSS theAutenticacaoSS,
			Email theEmail, Endereco theEndereco, Date dataNascimento, String sobreMim, URI facebook,
			URI instagram, URI spotyfy, URI twitter, URI twitch) {
		super(id, nome, genero, statusPessoa, theAutenticacaoSS, theEmail, theEndereco, dataNascimento);
		this.sobreMim = sobreMim;
		this.facebook = facebook;
		this.instagram = instagram;
		this.spotyfy = spotyfy;
		this.twitter = twitter;
		this.twitch = twitch;
	}

	public String getSobreMim() {
		return sobreMim;
	}

	public void setSobreMin(String sobreMim) {
		this.sobreMim = sobreMim;
	}

	public URI getFacebook() {
		return facebook;
	}

	public void setFacebook(URI facebook) {
		this.facebook = facebook;
	}

	public URI getInstagram() {
		return instagram;
	}

	public void setInstagram(URI instagram) {
		this.instagram = instagram;
	}

	public URI getSpotyfy() {
		return spotyfy;
	}

	public void setSpotyfy(URI spotyfy) {
		this.spotyfy = spotyfy;
	}

	public URI getTwitter() {
		return twitter;
	}

	public void setTwitter(URI twitter) {
		this.twitter = twitter;
	}

	public URI getTwitch() {
		return twitch;
	}

	public void setTwitch(URI twitch) {
		this.twitch = twitch;
	}

	public void setSobreMim(String sobreMim) {
		this.sobreMim = sobreMim;
	}
	
}
