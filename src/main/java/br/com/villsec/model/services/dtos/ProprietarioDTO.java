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
import br.com.villsec.model.entities.domain.Proprietario;

public class ProprietarioDTO extends EntidadeDominio implements Serializable {

	private static final long serialVersionUID = 1L;

	private String senha;
	private String login;
	private String matricula;
	private String uriImgPerfil;
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
	private String sobreMim;
	private String facebook;
	private String instagram;
	private String spotify;
	private String twitter;
	private String twitch;
	private String youtube;

	public ProprietarioDTO() {

	}

	public ProprietarioDTO(Proprietario theProprietario) {
		super(
				theProprietario.getId(),
				theProprietario.getVerificationCode(), 
				theProprietario.getDtCriacao(),
				theProprietario.getDtUltimaAlteracao());
		this.senha = theProprietario.getTheAutenticacaoSS().getSenha();
		this.login = theProprietario.getTheAutenticacaoSS().getLogin();
		this.matricula = theProprietario.getTheAutenticacaoSS().getMatricula();
		this.uriImgPerfil = theProprietario.getTheAutenticacaoSS().getUriImgPerfil().toString();
		this.tipoUsuario = theProprietario.getTheAutenticacaoSS().getTipoUsuario().getDescricao();
		this.email = theProprietario.getTheEmail().getEmail();
		this.logradouro = theProprietario.getTheEndereco().getLogradouro();
		this.cep = theProprietario.getTheEndereco().getCep();
		this.bairro = theProprietario.getTheEndereco().getBairro();
		this.cidade = theProprietario.getTheEndereco().getCidade();
		this.estado = theProprietario.getTheEndereco().getEstado();
		this.pais = theProprietario.getTheEndereco().getPais();
		this.nome = theProprietario.getNome();
		this.genero = theProprietario.getGenero();
		this.statusPessoa = theProprietario.getStatusPessoa();
		this.dataNascimento = theProprietario.getDataNascimento().toString();
		List<Telefone> list = new ArrayList<Telefone>();
		list.addAll(theProprietario.getTheTelefones());
		this.numeroTelefone1 = list.get(0).getNumeroTelefone();
		this.tipoTelefone1 = list.get(0).getTipoTelefone().getDescricao();
		if(list.size() > 1) {
			this.numeroTelefone2 = list.get(1).getNumeroTelefone();
			this.tipoTelefone2 = list.get(1).getTipoTelefone().getDescricao();
		}
		this.sobreMim = theProprietario.getSobreMim();
		this.facebook = theProprietario.getFacebook().toString();
		this.instagram = theProprietario.getInstagram().toString();
		this.spotify = theProprietario.getSpotify().toString();
		this.twitter = theProprietario.getTwitter().toString();
		this.twitch = theProprietario.getTwitch().toString();
		this.youtube = theProprietario.getYoutube().toString();
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

	public String getUriImgPerfil() {
		return uriImgPerfil;
	}

	public void setUriImgPerfil(String uriImgPerfil) {
		this.uriImgPerfil = uriImgPerfil;
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

	public String getSobreMim() {
		return sobreMim;
	}

	public void setSobreMim(String sobreMim) {
		this.sobreMim = sobreMim;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getInstagram() {
		return instagram;
	}

	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}

	public String getSpotify() {
		return spotify;
	}

	public void setSpotify(String spotify) {
		this.spotify = spotify;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getTwitch() {
		return twitch;
	}

	public void setTwitch(String twitch) {
		this.twitch = twitch;
	}

	public String getYoutube() {
		return youtube;
	}

	public void setYoutube(String youtube) {
		this.youtube = youtube;
	}
	
	
}
