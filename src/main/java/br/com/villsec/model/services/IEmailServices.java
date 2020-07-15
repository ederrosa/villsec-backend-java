package br.com.villsec.model.services;

import javax.mail.internet.MimeMessage;

import br.com.villsec.model.entities.domain.AutenticacaoSS;
import br.com.villsec.model.entities.domain.Email;
import br.com.villsec.model.entities.domain.Evento;

public interface IEmailServices {

	void sendAlertaEventoHtmlEmail(Evento theEvento, Email theEmail);
	
	void sendHtmlEmail(MimeMessage msg);
	
	void sendNewPasswordHtmlEmail(AutenticacaoSS cliente, String newPass);
		
}
