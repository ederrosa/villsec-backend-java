package br.com.villsec.model.services.utilities;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.com.villsec.model.services.exceptions.FileException;

@Component
public class VideoUtilities {

	@Value("${video.size}")
	private Long size;

	public InputStream getInputStream(MultipartFile uploadedFile) {

		String theExtension = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());
		if (!"mp4".equals(theExtension) && !"avi".equals(theExtension) && !"swf".equals(theExtension)) {
			throw new FileException("Somente vídeos no formato: MP4, SWF e AVI são permitidos");
		}
		if (uploadedFile.getSize() > this.size) {
			throw new FileException("O vídeo ultrapassa o limite do sistema de: " + this.size + " bytes ("
					+ (this.size / (1024 * 1024)) + "MB)!");
		}
		try {
			return new BufferedInputStream(uploadedFile.getInputStream());
		} catch (IOException e) {
			throw new FileException("Erro ao ler o vídeo");
		}
	}
}
