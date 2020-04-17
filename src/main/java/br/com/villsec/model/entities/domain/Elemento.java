package br.com.villsec.model.entities.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.DynamicInsert;

import br.com.villsec.model.entities.enums.TipoElemento;

@Entity
@DynamicInsert
public class Elemento extends EntidadeDominio {

	private static final long serialVersionUID = 1L;

	private String descricao;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "File_ID")
	private File elemento;
	private Integer tipoElemento;
	private String titulo;
	private Boolean status;

	public Elemento() {

	}

	public Elemento(Long id, String descricao, File elemento, TipoElemento tipoElemento, String titulo,
			Boolean status) {
		super(id);
		this.descricao = descricao;
		this.elemento = elemento;
		this.tipoElemento = (tipoElemento == null) ? null : tipoElemento.getCodigo();
		this.titulo = titulo;
		this.status = status;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public File getElemento() {
		return elemento;
	}

	public void setElemento(File elemento) {
		this.elemento = elemento;
	}

	public TipoElemento getTipoElemento() {
		return TipoElemento.toEnum(tipoElemento);
	}

	public void setTipoElemento(TipoElemento tipoElemento) {
		this.tipoElemento = tipoElemento.getCodigo();
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
}
