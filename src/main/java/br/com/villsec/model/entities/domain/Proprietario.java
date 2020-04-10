package br.com.villsec.model.entities.domain;

import javax.persistence.Entity;

import org.hibernate.annotations.DynamicInsert;

@Entity
@DynamicInsert
public class Proprietario extends Pessoa{

	private static final long serialVersionUID = 1L;

	private String sobreMin;

	public Proprietario() {
		
	}

	public Proprietario(Long id, String nome, String genero, Boolean statusPessoa, AutenticacaoSS theAutenticacaoSS,
			Email theEmail, Endereco theEndereco, String sobreMim) {
		super(id, nome, genero, statusPessoa, theAutenticacaoSS, theEmail, theEndereco);
		this.sobreMin = sobreMim;
	}

	public String getSobreMin() {
		return sobreMin;
	}

	public void setSobreMin(String sobreMin) {
		this.sobreMin = sobreMin;
	}	
	
}
