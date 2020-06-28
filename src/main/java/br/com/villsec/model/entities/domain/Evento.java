package br.com.villsec.model.entities.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.DynamicInsert;

import br.com.villsec.model.entities.enums.TipoEvento;

@Entity
@DynamicInsert
public class Evento extends EntidadeDominio {

	private static final long serialVersionUID = 1L;

	private String classificacao;
	private String descricao;
	private Date diaInicio;
	private Date diaTermino;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "File_ID")
	private Arquivo folder;
	private String horaInicio;
	private String horaTermino;
	private String nome;
	private Integer tipoEvento;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ENDERECO_ID")
	private Endereco theEndereco;

	public Evento() {

	}

	public Evento(Long id, String classificacao, Date diaInicio, Date diaTermino, String descricao, Arquivo folder,
			String horaInicio, String horaTermino, String nome, TipoEvento tipoEvento, Endereco theEndereco) {
		super(id);
		this.classificacao = classificacao;
		this.diaInicio = diaInicio;
		this.diaTermino = diaTermino;
		this.descricao = descricao;
		this.folder = folder;
		this.horaInicio = horaInicio;
		this.horaTermino = horaTermino;
		this.nome = nome;
		this.tipoEvento = (tipoEvento == null) ? null : tipoEvento.getCodigo();
		this.theEndereco = theEndereco;
	}

	public String getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDiaInicio() {
		return diaInicio;
	}

	public void setDiaInicio(Date diaInicio) {
		this.diaInicio = diaInicio;
	}

	public Date getDiaTermino() {
		return diaTermino;
	}

	public void setDiaTermino(Date diaTermino) {
		this.diaTermino = diaTermino;
	}

	public Arquivo getFolder() {
		return folder;
	}

	public void setFolder(Arquivo folder) {
		this.folder = folder;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraTermino() {
		return horaTermino;
	}

	public void setHoraTermino(String horaTermino) {
		this.horaTermino = horaTermino;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Endereco getTheEndereco() {
		return theEndereco;
	}

	public void setTheEndereco(Endereco theEndereco) {
		this.theEndereco = theEndereco;
	}

	public TipoEvento getTipoEvento() {
		return TipoEvento.toEnum(tipoEvento);
	}

	public void setTipoEvento(TipoEvento tipoEvento) {
		this.tipoEvento = tipoEvento.getCodigo();
	}

}
