package br.com.villsec.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.villsec.model.entities.domain.AutenticacaoSS;
import br.com.villsec.model.repository.IAutenticacaoSSRepository;
import br.com.villsec.model.services.exceptions.ObjectNotFoundException;
import br.com.villsec.model.services.utilities.CodeUtilities;

@Service
public class AuthServices {

	@Autowired
	private IAutenticacaoSSRepository theIAutenticacaoSSRepository;

	@Autowired
	private BCryptPasswordEncoder theBCryptPasswordEncoder;

	@Autowired
	private IEmailServices theIEmailServices;

	public void sendNewPassword(String email) {

		AutenticacaoSS theAutenticacaoSS = this.theIAutenticacaoSSRepository.findByLogin(email);
		if (theAutenticacaoSS == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		String newPass = new CodeUtilities().newPassword();
		this.theIEmailServices.sendNewPasswordHtmlEmail(theAutenticacaoSS, newPass);
		theAutenticacaoSS.setSenha(this.theBCryptPasswordEncoder.encode(newPass));
		this.theIAutenticacaoSSRepository.save(theAutenticacaoSS);
	}
}
