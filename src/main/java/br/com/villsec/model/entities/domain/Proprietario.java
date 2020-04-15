package br.com.villsec.model.entities.domain;

import java.util.Date;

import javax.persistence.Entity;

import org.hibernate.annotations.DynamicInsert;

@Entity
@DynamicInsert
public class Proprietario extends Pessoa{

	private static final long serialVersionUID = 1L;

	private String sobreMim;

	public Proprietario() {
		
	}
	
	public Proprietario(Long id, String nome, String genero, Boolean statusPessoa, AutenticacaoSS theAutenticacaoSS,
			Email theEmail, Endereco theEndereco, Date dataNascimento, String sobreMim) {
		super(id, nome, genero, statusPessoa, theAutenticacaoSS, theEmail, theEndereco, dataNascimento);
		this.sobreMim = sobreMim;
	}

	public String getSobreMim() {
		return sobreMim;
	}

	public void setSobreMin(String sobreMim) {
		this.sobreMim = sobreMim;
	}	
	
}
