package br.com.villsec.model.entities.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Email extends EntidadeDominio {

	private static final long serialVersionUID = 1L;

	@Column(unique = true, columnDefinition = "VARCHAR(100)", nullable = false)
	private String email;

	public Email() {
	}

	public Email(Long id, String email) {
		super(id);
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
