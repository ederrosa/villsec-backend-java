package br.com.villsec.model.entities.domain;

import javax.persistence.Entity;

import org.hibernate.annotations.DynamicInsert;

@Entity
@DynamicInsert
public class Seguidor extends Pessoa {

	private static final long serialVersionUID = 1L;

	public Seguidor() {
	}

	public Seguidor(Long id, String nome, String genero, Boolean statusPessoa, AutenticacaoSS theAutenticacaoSS,
			Email theEmail, Endereco theEndereco) {
		super(id, nome, genero, statusPessoa, theAutenticacaoSS, theEmail, theEndereco);
		// TODO Auto-generated constructor stub
	}

}
