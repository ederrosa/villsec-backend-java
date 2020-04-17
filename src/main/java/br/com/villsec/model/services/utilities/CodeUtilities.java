package br.com.villsec.model.services.utilities;

import org.springframework.stereotype.Component;

import br.com.villsec.model.repository.IAlbumRepository;
import br.com.villsec.model.services.UserLoggedInService;

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

	public String geradorDeMatricula(UserLoggedInService theUserLoggedInService) {
		String code = codeGenerator(10, 6, 10);
		if (theUserLoggedInService.IsThereMatricula(code))
			geradorDeMatricula(theUserLoggedInService);
		return code;
	}
	
	public String codigoAlbum(IAlbumRepository theAlbumRepository) {
		String code = codeGenerator(10, 4, 100);
		if (theAlbumRepository.findByCodigo(code) != null)
			codigoAlbum(theAlbumRepository);
		return code;
	}
}
