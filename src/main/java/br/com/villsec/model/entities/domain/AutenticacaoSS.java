package br.com.villsec.model.entities.domain;

import java.net.URI;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.villsec.model.entities.domain.EntidadeDominio;
import br.com.villsec.model.entities.enums.Perfil;

@Entity
public class AutenticacaoSS extends EntidadeDominio implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Column(unique = true, columnDefinition = "VARCHAR(50)")
	private String login;
	@Column(unique = true, columnDefinition = "VARCHAR(20)")
	private String matricula;
	private String senha;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PERFIS")
	private Set<Integer> perfil;
	private URI uriImgPerfil;
	private String nomeImgPerfil;
	private Integer tipoUsuario;
	@Transient
	private Collection<? extends GrantedAuthority> authorities;

	public AutenticacaoSS() {

	}

	public AutenticacaoSS(Long id, String login, String matricula, String senha, Set<Perfil> perfis,
			URI uriImgPerfil, String nomeImgPerfil, Perfil tipoUsuario) {
		super(id);
		this.login = login;
		this.matricula = matricula;
		this.senha = senha;
		this.perfil = perfis.stream().map(x -> x.getCodigo()).collect(Collectors.toSet());
		this.uriImgPerfil = uriImgPerfil;
		this.nomeImgPerfil = nomeImgPerfil;
		this.tipoUsuario = (tipoUsuario == null) ? null : tipoUsuario.getCodigo();
		this.authorities = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getRole()))
				.collect(Collectors.toList());
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Set<Perfil> getPerfil() {
		return perfil.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}

	public void addPerfil(Perfil perfil) {
		this.perfil.add(perfil.getCodigo());
	}

	public URI getUriImgPerfil() {
		return uriImgPerfil;
	}

	public void setUriImgPerfil(URI uriImgPerfil) {
		this.uriImgPerfil = uriImgPerfil;
	}

	public String getNomeImgPerfil() {
		return nomeImgPerfil;
	}

	public void setNomeImgPerfil(String nomeImgPerfil) {
		this.nomeImgPerfil = nomeImgPerfil;
	}

	public Perfil getTipoUsuario() {
		return Perfil.toEnum(tipoUsuario);
	}

	public void setTipoUsuario(Perfil tipoUsuario) {
		this.tipoUsuario = tipoUsuario.getCodigo();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return perfil.stream().map(x -> new SimpleGrantedAuthority(Perfil.toEnum(x).getRole()))
				.collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public boolean hasRole(Perfil perfil) {
		return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getRole()));
	}

}
