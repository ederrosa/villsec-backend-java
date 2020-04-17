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
	private String duracao;
	private Date data;
	private String descricao;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "File_ID")
	private File folder;
	private String nome;
	private Integer tipoEvento;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ENDERECO_ID")
	private Endereco theEndereco;

	public Evento() {

	}

	public Evento(Long id, String classificacao, String duracao, Date data, String descricao, File folder, String nome,
			TipoEvento tipoEvento, Endereco theEndereco) {
		super(id);
		this.classificacao = classificacao;
		this.duracao = duracao;
		this.data = data;
		this.descricao = descricao;
		this.folder = folder;
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

	public String getDuracao() {
		return duracao;
	}

	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public File getFolder() {
		return folder;
	}

	public void setFolder(File folder) {
		this.folder = folder;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoEvento getTipoEvento() {
		return TipoEvento.toEnum(tipoEvento);
	}

	public void setTipoEvento(TipoEvento tipoEvento) {
		this.tipoEvento = tipoEvento.getCodigo();
	}

	public Endereco getTheEndereco() {
		return theEndereco;
	}

	public void setTheEndereco(Endereco theEndereco) {
		this.theEndereco = theEndereco;
	}

}
