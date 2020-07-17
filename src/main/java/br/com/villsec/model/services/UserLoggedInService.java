package br.com.villsec.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.villsec.model.entities.domain.AutenticacaoSS;
import br.com.villsec.model.entities.domain.Pessoa;
import br.com.villsec.model.repository.IAutenticacaoSSRepository;
import br.com.villsec.model.repository.IPessoaRepository;
import br.com.villsec.model.services.exceptions.AuthorizationException;

@Service
public class UserLoggedInService {

	@Autowired
	private IPessoaRepository theIPessoaRepository;

	@Autowired
	private IAutenticacaoSSRepository theIAutenticacaoSSRepository;

	public static AutenticacaoSS authenticated() {
		try {
			return (AutenticacaoSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}

	public Pessoa userLoggedIn() {

		if (authenticated() == null) {
			throw new AuthorizationException("Acesso negado, erro de validação de usuario");
		}
		return this.theIPessoaRepository.findByTheAutenticacaoSS(authenticated());
	}
	
	public Boolean IsThereMatricula(String matricula) {
		return (this.theIAutenticacaoSSRepository.findByMatricula(matricula) == null) ? false : true;		
	}

}
