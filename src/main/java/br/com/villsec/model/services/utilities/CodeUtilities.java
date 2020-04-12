package br.com.villsec.model.services.utilities;

import org.springframework.stereotype.Component;

@Component
public class CodeUtilities {

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

}
