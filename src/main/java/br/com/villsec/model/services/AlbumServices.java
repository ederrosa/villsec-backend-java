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
import br.com.villsec.model.entities.domain.Arquivo;
import br.com.villsec.model.entities.domain.Musica;
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
	private MusicaServices theMusicaServices;

	@Autowired
	private S3Service theS3Service;

	@Autowired
	private ImageUtilities theImageUtilities;

	@Value("${prefix.album.profile}")
	private String prefix;

	@Value("${image.size}")
	private Integer size;

	@Transactional
	public Album insert(Album theEntidade, MultipartFile theMultipartFile) {

		if (UserLoggedInService.authenticated() == null
				&& !UserLoggedInService.authenticated().hasRole(Perfil.PROPRIETARIO)) {
			throw new AuthorizationException("Acesso negado");
		}
		theEntidade.setId(null);
		theEntidade.setCodigo(new CodeUtilities().albumCode(this.theAlbumRepository));
		BufferedImage jpgImage = this.theImageUtilities.getJpgImageFromFile(theMultipartFile);
		jpgImage = this.theImageUtilities.resize(jpgImage, this.size);
		String fileName = this.prefix + theEntidade.getNome() + "/" + theEntidade.getNome() + "."
				+ FilenameUtils.getExtension(theMultipartFile.getOriginalFilename());
		Arquivo theFile = new Arquivo(null, fileName,
				this.theS3Service.uploadFile(
						this.theImageUtilities.getInputStream(jpgImage,
								FilenameUtils.getExtension(theMultipartFile.getOriginalFilename())),
						fileName, theMultipartFile.getContentType()));
		theEntidade.setCapa(theFile);
		return this.theAlbumRepository.save(theEntidade);
	}

	public Album find(Long id) {
		Optional<Album> theEntidade = this.theAlbumRepository.findById(id);
		return theEntidade.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Album.class.getSimpleName()));
	}

	public Page<Album> findAllPage(Integer page, Integer linesPerPage, String orderBy, String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return this.theAlbumRepository.findAll(pageRequest);
	}

	public Album update(Album theEntidade, MultipartFile theMultipartFile) {

		if (UserLoggedInService.authenticated() == null
				&& !UserLoggedInService.authenticated().hasRole(Perfil.PROPRIETARIO)) {
			throw new AuthorizationException("Acesso negado");
		}
		if (theMultipartFile != null && !theMultipartFile.isEmpty()) {
			this.theS3Service.deleteFile(theEntidade.getCapa().getNome());
			BufferedImage jpgImage = this.theImageUtilities.getJpgImageFromFile(theMultipartFile);
			jpgImage = this.theImageUtilities.cropSquare(jpgImage);
			jpgImage = this.theImageUtilities.resize(jpgImage, this.size);
			String fileName = this.prefix + theEntidade.getNome() + "/" + theEntidade.getNome() + "."
					+ FilenameUtils.getExtension(theMultipartFile.getOriginalFilename());
			Arquivo theFile = new Arquivo(null, fileName,
					this.theS3Service.uploadFile(
							this.theImageUtilities.getInputStream(jpgImage,
									FilenameUtils.getExtension(theMultipartFile.getOriginalFilename())),
							fileName, theMultipartFile.getContentType()));
			theEntidade.setCapa(theFile);
		}
		return this.theAlbumRepository.save(theEntidade);
	}

	public void delete(Long id) {

		if (UserLoggedInService.authenticated() == null
				|| !UserLoggedInService.authenticated().hasRole(Perfil.PROPRIETARIO)) {
			throw new AuthorizationException("Acesso negado");
		}
		try {
			if (find(id).getCapa() != null) {
				this.theS3Service.deleteFile(find(id).getCapa().getNome());
			}
			for(Musica theMusica : this.theMusicaServices.findAll(this.find(id))) {
				this.theMusicaServices.delete(theMusica.getId());			
			}
			this.theAlbumRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há Entidades relacionadas");
		}
	}

}
