package br.com.villsec.model.services.utilities;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.com.villsec.model.services.exceptions.FileException;

@Component
public class FileUtilities {

	public InputStream getInputStream(MultipartFile uploadedFile) {
		
	    try {
			return new BufferedInputStream(uploadedFile.getInputStream());
		} catch (IOException e) {
			throw new FileException("Erro ao ler arquivo, Formato desconhecido!!");
		}
	}
}
