package br.com.villsec.model.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.villsec.model.entities.domain.AutenticacaoSS;
import br.com.villsec.model.entities.domain.Email;
import br.com.villsec.model.entities.domain.Evento;

public abstract class AbstractEmailServices implements IEmailServices {

	@Value("${default.sender}")
	private String sender;

	@Autowired
	private TemplateEngine theTemplateEngine;

	@Autowired
	private JavaMailSender theJavaMailSender;

	@Override
	public void sendNewPasswordHtmlEmail(AutenticacaoSS cliente, String newPass) {
		MimeMessage theMimeMessage;
		try {
			theMimeMessage = prepareNewPasswordHtmlEmail(cliente, newPass);
			sendHtmlEmail(theMimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
	}

	protected MimeMessage prepareNewPasswordHtmlEmail(AutenticacaoSS cliente, String newPass) throws MessagingException {
		MimeMessage theMimeMessage = theJavaMailSender.createMimeMessage();
		MimeMessageHelper theMimeMessageHelper = new MimeMessageHelper(theMimeMessage, true);
		theMimeMessageHelper.setTo(cliente.getLogin());
		theMimeMessageHelper.setFrom(sender);
		theMimeMessageHelper.setSubject("V1llsec: Solicitação de nova senha:");
		theMimeMessageHelper.setSentDate(new Date(System.currentTimeMillis()));
		theMimeMessageHelper.setText(htmlFromTemplateNewPassword(cliente), true);
		return theMimeMessage;
	}

	protected String htmlFromTemplateNewPassword(AutenticacaoSS theAutenticacaoSS) {
		Context context = new Context();
		context.setVariable("element", theAutenticacaoSS);
		return theTemplateEngine.process("forgot/reset_password", context);
	}

	protected String htmlFromTemplateEvento(Evento obj) {
		Context context = new Context();
		context.setVariable("evento", obj);
		return theTemplateEngine.process("alerta/evento/event_alert", context);
	}

	@Override
	public void sendAlertaEventoHtmlEmail(Evento theEvento, Email theEmail) {
		MimeMessage theMimeMessage;
		try {
			theMimeMessage = prepareMimeMessageFromEvento(theEvento, theEmail);
			sendHtmlEmail(theMimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	protected MimeMessage prepareMimeMessageFromEvento(Evento theEvento, Email theEmail) throws MessagingException {
		MimeMessage theMimeMessage = theJavaMailSender.createMimeMessage();
		MimeMessageHelper theMimeMessageHelper = new MimeMessageHelper(theMimeMessage, true);
		theMimeMessageHelper.setTo(theEmail.getEmail());
		theMimeMessageHelper.setFrom(sender);
		theMimeMessageHelper.setSubject("V1LLSEC -- Alerta de novo evento próximo a você: " + theEvento.getNome());
		theMimeMessageHelper.setSentDate(new Date(System.currentTimeMillis()));
		theMimeMessageHelper.setText(htmlFromTemplateEvento(theEvento), true);
		return theMimeMessage;
	}
}
