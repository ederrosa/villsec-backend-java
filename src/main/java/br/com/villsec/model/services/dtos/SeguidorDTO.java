package br.com.villsec.model.services.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.villsec.model.entities.domain.Telefone;
import br.com.villsec.model.entities.domain.EntidadeDominio;
import br.com.villsec.model.entities.domain.Seguidor;

public class SeguidorDTO extends EntidadeDominio implements Serializable {

	private static final long serialVersionUID = 1L;

	@Length(max = 20, message = "O tamanho deve ser no máximo 20 caracteres")
	private String senha;
	private String login;
	private String matricula;
	private String urlImgPerfil;
	private String tipoUsuario;
	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(max = 120, message = "O tamanho deve ser no máximo 120 caracteres")
	@Email(message = "E-Mail informado é invalido!")
	private String email;
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
	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 4, max = 20, message = "O tamanho deve ser entre 4 e 20 caracteres")
	private String numeroTelefone1;
	private String tipoTelefone1;
	@Length(max = 20, message = "O tamanho deve ser entre 4 e 20 caracteres")
	private String numeroTelefone2;
	private String tipoTelefone2;
	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 4, max = 100, message = "O tamanho deve ser entre 4 e 100 caracteres")
	private String nome;
	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 4, max = 20, message = "O tamanho deve ser entre 4 e 50 caracteres")
	private String genero;
	private Boolean statusPessoa;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private String dataNascimento;

	public SeguidorDTO() {

	}

	public SeguidorDTO(Seguidor theSeguidor) {
		super(theSeguidor.getId(), theSeguidor.getVerificationCode(), theSeguidor.getDtCriacao(),
				theSeguidor.getDtUltimaAlteracao());
		this.senha = theSeguidor.getTheAutenticacaoSS().getSenha();
		this.login = theSeguidor.getTheAutenticacaoSS().getLogin();
		this.matricula = theSeguidor.getTheAutenticacaoSS().getMatricula();
		this.urlImgPerfil = theSeguidor.getTheAutenticacaoSS().getUriImgPerfil().toString();
		this.tipoUsuario = theSeguidor.getTheAutenticacaoSS().getTipoUsuario().getDescricao();
		this.email = theSeguidor.getTheEmail().getEmail();
		this.logradouro = theSeguidor.getTheEndereco().getLogradouro();
		this.cep = theSeguidor.getTheEndereco().getCep();
		this.bairro = theSeguidor.getTheEndereco().getBairro();
		this.cidade = theSeguidor.getTheEndereco().getCidade();
		this.estado = theSeguidor.getTheEndereco().getEstado();
		this.pais = theSeguidor.getTheEndereco().getPais();
		this.nome = theSeguidor.getNome();
		this.genero = theSeguidor.getGenero();
		this.statusPessoa = theSeguidor.getStatusPessoa();
		this.dataNascimento = theSeguidor.getDataNascimento().toString();
		List<Telefone> list = new ArrayList<Telefone>();
		list.addAll(theSeguidor.getTheTelefones());
		this.numeroTelefone1 = list.get(0).getNumeroTelefone();
		this.tipoTelefone1 = list.get(0).getTipoTelefone().getDescricao();
		if (list.size() > 1) {
			this.numeroTelefone2 = list.get(1).getNumeroTelefone();
			this.tipoTelefone2 = list.get(1).getTipoTelefone().getDescricao();
		}
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getUrlImgPerfil() {
		return urlImgPerfil;
	}

	public void setUriImgPerfil(String urlImgPerfil) {
		this.urlImgPerfil = urlImgPerfil;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getNumeroTelefone1() {
		return numeroTelefone1;
	}

	public void setNumeroTelefone1(String numeroTelefone1) {
		this.numeroTelefone1 = numeroTelefone1;
	}

	public String getTipoTelefone1() {
		return tipoTelefone1;
	}

	public void setTipoTelefone1(String tipoTelefone1) {
		this.tipoTelefone1 = tipoTelefone1;
	}

	public String getNumeroTelefone2() {
		return numeroTelefone2;
	}

	public void setNumeroTelefone2(String numeroTelefone2) {
		this.numeroTelefone2 = numeroTelefone2;
	}

	public String getTipoTelefone2() {
		return tipoTelefone2;
	}

	public void setTipoTelefone2(String tipoTelefone2) {
		this.tipoTelefone2 = tipoTelefone2;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Boolean getStatusPessoa() {
		return statusPessoa;
	}

	public void setStatusPessoa(Boolean statusPessoa) {
		this.statusPessoa = statusPessoa;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
}
