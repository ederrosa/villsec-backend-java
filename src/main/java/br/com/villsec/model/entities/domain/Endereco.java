package br.com.villsec.model.entities.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Endereco extends EntidadeDominio {

	private static final long serialVersionUID = 1L;

	@Column(columnDefinition = "VARCHAR(50)")
	private String bairro;
	@Column(columnDefinition = "VARCHAR(10)")
	private String cep;
	@Column(columnDefinition = "VARCHAR(50)")
	private String cidade;
	@Column(columnDefinition = "VARCHAR(50)")
	private String estado;
	@Column(columnDefinition = "VARCHAR(100)")
	private String logradouro;
	@Column(columnDefinition = "VARCHAR(50)")
	private String pais;

	public Endereco() {

	}

	public Endereco(Long id, String bairro, String cep, String cidade, String estado, String logradouro, String pais) {
		super(id);
		this.bairro = bairro;
		this.cep = cep;
		this.cidade = cidade;
		this.estado = estado;
		this.logradouro = logradouro;
		this.pais = pais;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}
}
