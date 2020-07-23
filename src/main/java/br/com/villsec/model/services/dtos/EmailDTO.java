package br.com.villsec.model.services.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.com.villsec.model.entities.domain.EntidadeDominio;

public class EmailDTO extends EntidadeDominio {

	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(max = 120, message = "O tamanho deve ser no máximo 120 caracteres")
	@Email(message = "E-Mail informado é invalido!")
	private String email;

	public EmailDTO() {

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
