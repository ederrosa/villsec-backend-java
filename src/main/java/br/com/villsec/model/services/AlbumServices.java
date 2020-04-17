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

import br.com.villsec.model.entities.domain.Album;
import br.com.villsec.model.entities.domain.File;
import br.com.villsec.model.entities.enums.Perfil;
import br.com.villsec.model.repository.IAlbumRepository;
import br.com.villsec.model.services.exceptions.AuthorizationException;
import br.com.villsec.model.services.exceptions.DataIntegrityException;
import br.com.villsec.model.services.exceptions.ObjectNotFoundException;
import br.com.villsec.model.services.utilities.CodeUtilities;
import br.com.villsec.model.services.utilities.ImageUtilities;

@Service
public class AlbumServices {

	@Autowired
	private IAlbumRepository theAlbumRepository;

	@Autowired
	private S3Service theS3Service;

	@Autowired
	private ImageUtilities theImageUtilities;

	@Value("${prefix.evento.profile}")
	private String prefix;

	@Value("${img.profile.size}")
	private Integer size;

	@Transactional
	public Album insert(Album theEntidade, MultipartFile theMultipartFile) {

		if (UserLoggedInService.authenticated() == null
				&& !UserLoggedInService.authenticated().hasRole(Perfil.PROPRIETARIO)) {
			throw new AuthorizationException("Acesso negado");
		}
		theEntidade.setId(null);
		theEntidade.setCodigo(new CodeUtilities().codigoAlbum(theAlbumRepository));
		BufferedImage jpgImage = theImageUtilities.getJpgImageFromFile(theMultipartFile);
		jpgImage = theImageUtilities.cropSquare(jpgImage);
		jpgImage = theImageUtilities.resize(jpgImage, size);
		File theFile = new File(null,
				prefix + "/" + theEntidade.getGenero() + "/" + theEntidade.getCodigo() +"/" + theEntidade.getNome() + "."
						+ FilenameUtils.getExtension(theMultipartFile.getOriginalFilename()),
				theS3Service.uploadFile(
						theImageUtilities.getInputStream(jpgImage,
								FilenameUtils.getExtension(theMultipartFile.getOriginalFilename())),
						theEntidade.getCapa().getNome(), theMultipartFile.getContentType()));
		theEntidade.setCapa(theFile);
		return theAlbumRepository.save(theEntidade);
	}

	public Album find(Long id) {
		Optional<Album> theEntidade = theAlbumRepository.findById(id);
		return theEntidade.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Album.class.getSimpleName()));
	}

	public Page<Album> findAllPage(Integer page, Integer linesPerPage, String orderBy, String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return theAlbumRepository.findAll(pageRequest);
	}

	public Album update(Album theEntidade, MultipartFile theMultipartFile) {

		if (UserLoggedInService.authenticated() == null
				&& !UserLoggedInService.authenticated().hasRole(Perfil.PROPRIETARIO)) {
			throw new AuthorizationException("Acesso negado");
		}
		theEntidade.setCodigo(new CodeUtilities().codigoAlbum(theAlbumRepository));
		if (theMultipartFile != null && !theMultipartFile.isEmpty()) {
			theS3Service.deleteFile(theEntidade.getCapa().getNome());
			BufferedImage jpgImage = theImageUtilities.getJpgImageFromFile(theMultipartFile);
			jpgImage = theImageUtilities.cropSquare(jpgImage);
			jpgImage = theImageUtilities.resize(jpgImage, size);
			File theFile = new File(null,
					prefix + "/" + theEntidade.getGenero() + "/" + theEntidade.getCodigo() +"/" + theEntidade.getNome() + "."
							+ FilenameUtils.getExtension(theMultipartFile.getOriginalFilename()),
					theS3Service.uploadFile(
							theImageUtilities.getInputStream(jpgImage,
									FilenameUtils.getExtension(theMultipartFile.getOriginalFilename())),
							theEntidade.getCapa().getNome(), theMultipartFile.getContentType()));
			theEntidade.setCapa(theFile);
		}
		return theAlbumRepository.save(theEntidade);
	}

	public void delete(Long id) {

		if (UserLoggedInService.authenticated() == null
				|| !UserLoggedInService.authenticated().hasRole(Perfil.PROPRIETARIO)) {
			throw new AuthorizationException("Acesso negado");
		}
		try {
			if (find(id).getCapa() != null) {
				theS3Service.deleteFile(find(id).getCapa().getNome());
			}
			theAlbumRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há Entidades relacionadas");
		}
	}

}
