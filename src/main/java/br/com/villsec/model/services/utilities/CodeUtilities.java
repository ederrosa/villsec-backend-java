package br.com.villsec.model.services.utilities;

import java.util.Random;

import org.springframework.stereotype.Component;

import br.com.villsec.model.repository.IAlbumRepository;
import br.com.villsec.model.services.UserLoggedInService;

@Component
public class CodeUtilities {

	private Random rand = new Random();
	
	public String codeGenerator(int x, int y, int z) {

		String code;
		Double codigoVerificador;
		Double randomized;
	
		randomized = Math.ceil(Math.random() * Math.pow(x, y));
		codigoVerificador = Math.ceil(Math.log(randomized));
		while (codigoVerificador > z) {
			codigoVerificador = Math.ceil(Math.log(codigoVerificador));
		}
		code = randomized.intValue() + "-" + codigoVerificador.intValue();
		return code;
	}

	public String registrationGenerator(UserLoggedInService theUserLoggedInService) {
		String code = codeGenerator(10, 6, 10);
		if (theUserLoggedInService.IsThereMatricula(code))
			registrationGenerator(theUserLoggedInService);
		return code;
	}
	
	public String albumCode(IAlbumRepository theAlbumRepository) {
		String code = codeGenerator(10, 4, 100);
		if (theAlbumRepository.findByCodigo(code) != null)
			albumCode(theAlbumRepository);
		return code;
	}
	
	public String newPassword() {
		char[] vet = new char[10];
		for (int i=0; i<10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = this.rand.nextInt(3);
		if (opt == 0) { // gera um digito
			return (char) (rand.nextInt(10) + 48);
		}
		else if (opt == 1) { // gera letra maiuscula
			return (char) (this.rand.nextInt(26) + 65);
		}
		else { // gera letra minuscula
			return (char) (this.rand.nextInt(26) + 97);
		}
	}
}
