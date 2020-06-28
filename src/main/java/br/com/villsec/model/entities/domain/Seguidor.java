package br.com.villsec.model.entities.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;

import org.hibernate.annotations.DynamicInsert;

@Entity
@DynamicInsert
public class Seguidor extends Pessoa {

	private static final long serialVersionUID = 1L;

	public Seguidor() {
	}

	public Seguidor(Long id, Date dataNascimento, String genero, String nome, Boolean statusPessoa,
			AutenticacaoSS theAutenticacaoSS, Endereco theEndereco, Email theEmail, Set<Telefone> theTelefones) {
		super(id, dataNascimento, genero, nome, statusPessoa, theAutenticacaoSS, theEndereco, theEmail, theTelefones);
		// TODO Auto-generated constructor stub
	}
}
