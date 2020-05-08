package br.com.villsec.model.services;

import java.awt.image.BufferedImage;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.villsec.model.entities.domain.Musica;
import br.com.villsec.model.entities.domain.File;
import br.com.villsec.model.entities.enums.Perfil;
import br.com.villsec.model.repository.IMusicaRepository;
import br.com.villsec.model.services.exceptions.AuthorizationException;
import br.com.villsec.model.services.exceptions.DataIntegrityException;
import br.com.villsec.model.services.exceptions.ObjectNotFoundException;
import br.com.villsec.model.services.utilities.ImageUtilities;

@Service
public class MusicaServices {

	@Autowired
	private IMusicaRepository theMusicaRepository;

	@Autowired
	private S3Service theS3Service;

	@Autowired
	private ImageUtilities theImageUtilities;

	@Value("${prefix.album.profile}")
	private String prefix;

	@Value("${img.profile.size}")
	private Integer size;

	@Transactional
	public Musica insert(Musica theEntidade, MultipartFile theMultipartFile) {

		if (UserLoggedInService.authenticated() == null
				&& !UserLoggedInService.authenticated().hasRole(Perfil.PROPRIETARIO)) {
			throw new AuthorizationException("Acesso negado");
		}
		theEntidade.setId(null);
		BufferedImage jpgImage = theImageUtilities.getJpgImageFromFile(theMultipartFile);
		jpgImage = theImageUtilities.cropSquare(jpgImage);
		jpgImage = theImageUtilities.resize(jpgImage, size);
		String fileName = prefix + theEntidade.getAutor() + "/" + theEntidade.getNome() + "."
				+ FilenameUtils.getExtension(theMultipartFile.getOriginalFilename());
		File theFile = new File(null, fileName,
				theS3Service.uploadFile(
						theImageUtilities.getInputStream(jpgImage,
								FilenameUtils.getExtension(theMultipartFile.getOriginalFilename())),
						fileName, theMultipartFile.getContentType()));
		theEntidade.setArquivo(theFile);
		return theMusicaRepository.save(theEntidade);
	}

	public Musica find(Long id) {
		Optional<Musica> theEntidade = theMusicaRepository.findById(id);
		return theEntidade.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Musica.class.getSimpleName()));
	}

	public Page<Musica> findAllPage(Integer page, Integer linesPerPage, String orderBy, String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return theMusicaRepository.findAll(pageRequest);
	}

	public Musica update(Musica theEntidade, MultipartFile theMultipartFile) {

		if (UserLoggedInService.authenticated() == null
				&& !UserLoggedInService.authenticated().hasRole(Perfil.PROPRIETARIO)) {
			throw new AuthorizationException("Acesso negado");
		}
		if (theMultipartFile != null && !theMultipartFile.isEmpty()) {
			theS3Service.deleteFile(theEntidade.getArquivo().getNome());
			BufferedImage jpgImage = theImageUtilities.getJpgImageFromFile(theMultipartFile);
			jpgImage = theImageUtilities.cropSquare(jpgImage);
			jpgImage = theImageUtilities.resize(jpgImage, size);
			String fileName = prefix + theEntidade.getAutor() + "/" + theEntidade.getNome() + "."
					+ FilenameUtils.getExtension(theMultipartFile.getOriginalFilename());
			File theFile = new File(null, fileName,
					theS3Service.uploadFile(
							theImageUtilities.getInputStream(jpgImage,
									FilenameUtils.getExtension(theMultipartFile.getOriginalFilename())),
							fileName, theMultipartFile.getContentType()));
			theEntidade.setArquivo(theFile);
		}
		return theMusicaRepository.save(theEntidade);
	}

	public void delete(Long id) {

		if (UserLoggedInService.authenticated() == null
				|| !UserLoggedInService.authenticated().hasRole(Perfil.PROPRIETARIO)) {
			throw new AuthorizationException("Acesso negado");
		}
		try {
			if (find(id).getArquivo() != null) {
				theS3Service.deleteFile(find(id).getArquivo().getNome());
			}
			theMusicaRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há Entidades relacionadas");
		}
	}

}
