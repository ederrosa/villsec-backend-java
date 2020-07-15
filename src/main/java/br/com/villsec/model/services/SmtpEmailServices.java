package br.com.villsec.model.services;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

public class SmtpEmailServices extends AbstractEmailServices{

	@Autowired
	private JavaMailSender theJavaMailSender;
	
	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		theJavaMailSender.send(msg);
	}

}
