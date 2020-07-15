package br.com.villsec.model.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.villsec.model.services.IEmailServices;
import br.com.villsec.model.services.SmtpEmailServices;

@Configuration
public class Config {

	@Bean
	public IEmailServices emailService() {
		return new SmtpEmailServices();
	}
}
