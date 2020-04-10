package br.com.villsec.model.entities.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

import br.com.villsec.model.entities.enums.TipoTelefone;

@Entity
public class Telefone extends EntidadeDominio {

	private static final long serialVersionUID = 1L;

	@Column(columnDefinition = "VARCHAR(20)")
	private String numeroTelefone;
	private Integer tipoTelefone;

	public Telefone() {

	}

	public Telefone(Long id, String numeroTelefone, TipoTelefone tipoTelefone) {
		super(id);
		this.numeroTelefone = numeroTelefone;
		this.tipoTelefone = (tipoTelefone == null) ? null : tipoTelefone.getCodigo();
	}

	public String getNumeroTelefone() {
		return numeroTelefone;
	}

	public void setNumeroTelefone(String numeroTelefone) {
		this.numeroTelefone = numeroTelefone;
	}

	public TipoTelefone getTipoTelefone() {
		return TipoTelefone.toEnum(tipoTelefone);
	}

	public void setTipoTelefone(TipoTelefone tipoTelefone) {
		this.tipoTelefone = tipoTelefone.getCodigo();
	}

}
