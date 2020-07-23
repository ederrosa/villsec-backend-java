package br.com.villsec.model.services.dtos;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.com.villsec.model.entities.domain.EntidadeDominio;
import br.com.villsec.model.entities.domain.Evento;

public class EventoDTO extends EntidadeDominio {

	private static final long serialVersionUID = 1L;

	private boolean alerta;
	private String classificacao;
	private String descricao;
	private String diaInicio;
	private String diaTermino;
	private String folderName;
	private String folderUrl;
	private String googleMapsUrl;
	private String horaInicio;
	private String horaTermino;
	private String ingressoUrl;
	private String nome;
	private String tipoEvento;
	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 1, max = 100, message = "O tamanho deve ser entre 1 e 100 caracteres")
	private String logradouro;
	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 8, max = 10, message = "Este campo deve ter 8 digitos")
	private String cep;
	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 1, max = 50, message = "O tamanho deve ser entre 1 e 50 caracteres")
	private String bairro;
	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 1, max = 50, message = "O tamanho deve ser entre 1 e 50 caracteres")
	private String cidade;
	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 1, max = 50, message = "O tamanho deve ser entre 1 e 50 caracteres")
	private String estado;
	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 1, max = 50, message = "O tamanho deve ser entre 1 e 50 caracteres")
	private String pais;

	public EventoDTO() {

	}

	public EventoDTO(Evento theEvento) {
		super(theEvento.getId(), theEvento.getVerificationCode(), theEvento.getDtCriacao(),
				theEvento.getDtUltimaAlteracao());
		this.alerta = theEvento.isAlerta();
		this.classificacao = theEvento.getClassificacao();
		this.diaInicio = theEvento.getDiaInicio().toString();
		this.diaTermino = theEvento.getDiaTermino().toString();
		this.descricao = theEvento.getDescricao();
		this.folderName = theEvento.getFolder().getNome();
		this.folderUrl = theEvento.getFolder().getUrl().toString();
		this.horaInicio = theEvento.getHoraInicio();
		this.horaTermino = theEvento.getHoraTermino();
		if (theEvento.getIngressoUrl() != null) {
			this.ingressoUrl = theEvento.getIngressoUrl().toString();
		}
		this.nome = theEvento.getNome();
		this.tipoEvento = theEvento.getTipoEvento().getDescricao();
		this.logradouro = theEvento.getTheEndereco().getLogradouro();
		this.cep = theEvento.getTheEndereco().getCep();
		this.bairro = theEvento.getTheEndereco().getBairro();
		this.cidade = theEvento.getTheEndereco().getCidade();
		this.estado = theEvento.getTheEndereco().getEstado();
		this.pais = theEvento.getTheEndereco().getPais();
		if (theEvento.getTheEndereco().getGoogleMapsUrl() != null) {
			this.googleMapsUrl = theEvento.getTheEndereco().getGoogleMapsUrl().toString();
		}
	}

	public boolean isAlerta() {
		return alerta;
	}

	public void setAlerta(boolean alerta) {
		this.alerta = alerta;
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

	public String getDiaInicio() {
		return diaInicio;
	}

	public void setDiaInicio(String diaInicio) {
		this.diaInicio = diaInicio;
	}

	public String getDiaTermino() {
		return diaTermino;
	}

	public void setDiaTermino(String diaTermino) {
		this.diaTermino = diaTermino;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getFolderUrl() {
		return folderUrl;
	}

	public void setFolderUrl(String folderUrl) {
		this.folderUrl = folderUrl;
	}

	public String getGoogleMapsUrl() {
		return googleMapsUrl;
	}

	public void setGoogleMapsUrl(String googleMapsUrl) {
		this.googleMapsUrl = googleMapsUrl;
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

	public String getIngressoUrl() {
		return ingressoUrl;
	}

	public void setIngressoUrl(String ingressoUrl) {
		this.ingressoUrl = ingressoUrl;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
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

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}
}
