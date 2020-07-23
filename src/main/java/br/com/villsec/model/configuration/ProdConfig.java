package br.com.villsec.model.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.villsec.model.services.IEmailServices;
import br.com.villsec.model.services.SmtpEmailServices;

@Configuration
@Profile("prod")
public class ProdConfig {

	@Bean
	public IEmailServices emailService() {
		return new SmtpEmailServices();
	}
}
