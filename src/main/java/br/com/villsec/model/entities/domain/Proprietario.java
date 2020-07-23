package br.com.villsec.model.entities.domain;

import java.net.URI;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

import org.hibernate.annotations.DynamicInsert;

@Entity
@DynamicInsert
public class Proprietario extends Pessoa {

	private static final long serialVersionUID = 1L;

	@Lob
	@Column(columnDefinition = "BLOB")
	private URI facebook;
	@Lob
	@Column(columnDefinition = "BLOB")
	private URI instagram;
	private String sobreMim;
	@Lob
	@Column(columnDefinition = "BLOB")
	private URI spotify;
	@Lob
	@Column(columnDefinition = "BLOB")
	private URI twitch;
	@Lob
	@Column(columnDefinition = "BLOB")
	private URI twitter;
	@Lob
	@Column(columnDefinition = "BLOB")
	private URI youtube;

	public Proprietario() {

	}

	public Proprietario(Long id, Date dataNascimento, String genero, String nome, Boolean statusPessoa,
			AutenticacaoSS theAutenticacaoSS, Endereco theEndereco, Email theEmail, Set<Telefone> theTelefones,
			URI facebook, URI instagram, String sobreMim, URI spotify, URI twitch, URI twitter, URI youtube) {
		super(id, dataNascimento, genero, nome, statusPessoa, theAutenticacaoSS, theEndereco, theEmail, theTelefones);
		this.facebook = facebook;
		this.instagram = instagram;
		this.sobreMim = sobreMim;
		this.spotify = spotify;
		this.twitch = twitch;
		this.twitter = twitter;
		this.youtube = youtube;
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

	public String getSobreMim() {
		return sobreMim;
	}

	public void setSobreMim(String sobreMim) {
		this.sobreMim = sobreMim;
	}

	public URI getSpotify() {
		return spotify;
	}

	public void setSpotify(URI spotify) {
		this.spotify = spotify;
	}

	public URI getTwitch() {
		return twitch;
	}

	public void setTwitch(URI twitch) {
		this.twitch = twitch;
	}

	public URI getTwitter() {
		return twitter;
	}

	public void setTwitter(URI twitter) {
		this.twitter = twitter;
	}

	public URI getYoutube() {
		return youtube;
	}

	public void setYoutube(URI youtube) {
		this.youtube = youtube;
	}
}
