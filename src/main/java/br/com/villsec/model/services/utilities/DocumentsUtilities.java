package br.com.villsec.model.services.utilities;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.com.villsec.model.services.exceptions.FileException;

@Component
public class DocumentsUtilities {

	public InputStream getInputStream(MultipartFile uploadedFile) {
		
		String theExtension = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());
		if (!"pdf".equals(theExtension) && !"doc".equals(theExtension) && !"docx".equals(theExtension)) {
			throw new FileException("Somente arquivos PDF, DOC e DOCx são permitidas");
		}
		try {
			return new BufferedInputStream(uploadedFile.getInputStream());
		} catch (IOException e) {
			throw new FileException("Erro ao ler arquivo");
		}
	}
}
