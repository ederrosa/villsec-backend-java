package br.com.villsec.model.services;

import java.util.List;
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

import br.com.villsec.model.entities.domain.Video;
import br.com.villsec.model.entities.domain.Arquivo;
import br.com.villsec.model.entities.domain.Galeria;
import br.com.villsec.model.entities.enums.Perfil;
import br.com.villsec.model.repository.IGaleriaRepository;
import br.com.villsec.model.repository.IVideoRepository;
import br.com.villsec.model.services.exceptions.AuthorizationException;
import br.com.villsec.model.services.exceptions.DataIntegrityException;
import br.com.villsec.model.services.exceptions.ObjectNotFoundException;
import br.com.villsec.model.services.utilities.VideoUtilities;

@Service
public class VideoServices {

	@Autowired
	private IVideoRepository theIVideoRepository;

	@Autowired
	private IGaleriaRepository theIGaleriaRepository;

	@Autowired
	private S3Service theS3Service;

	@Autowired
	private VideoUtilities theVideoUtilities;

	@Value("${prefix.video.profile}")
	private String prefix;

	@Transactional
	public Video insert(Video theEntidade, MultipartFile theMultipartFile, Long theGaleriaID) {

		if (UserLoggedInService.authenticated() == null
				|| !UserLoggedInService.authenticated().hasRole(Perfil.PROPRIETARIO)) {
			throw new AuthorizationException("Acesso negado");
		}
		theEntidade.setId(null);
		Galeria theGaleria = theIGaleriaRepository.findById(theGaleriaID).get();
		theGaleria.getTheVideos().add(theEntidade);
		theEntidade.setTheGaleria(theGaleria);
		if (theMultipartFile != null && !theMultipartFile.isEmpty()) {
			String fileName = prefix + "/" + theEntidade.getTitulo() + "."
					+ FilenameUtils.getExtension(theMultipartFile.getOriginalFilename());
			Arquivo theFile = new Arquivo(null, fileName, theS3Service.uploadFile(
					theVideoUtilities.getInputStream(theMultipartFile), fileName, theMultipartFile.getContentType()));
			theEntidade.setTheArquivo(theFile);
		}
		return theIVideoRepository.save(theEntidade);
	}

	public Video find(Long id) {
		Optional<Video> theEntidade = theIVideoRepository.findById(id);
		return theEntidade.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Video.class.getSimpleName()));
	}

	public List<Video> findAll(Galeria theGaleria) {
		return this.theIVideoRepository.findAllByTheGaleria(theGaleria);
	}

	public Page<Video> findAllPage(Integer page, Integer linesPerPage, String orderBy, String direction,
			Long theGaleriaID) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return theIVideoRepository.findAllByTheGaleria(theIGaleriaRepository.findById(theGaleriaID).get(), pageRequest);
	}

	public Video update(Video theEntidade, MultipartFile theMultipartFile) {

		if (UserLoggedInService.authenticated() == null
				|| !UserLoggedInService.authenticated().hasRole(Perfil.PROPRIETARIO)) {
			throw new AuthorizationException("Acesso negado");
		}
		if (theMultipartFile != null && !theMultipartFile.isEmpty()) {
			theS3Service.deleteFile(theEntidade.getTheArquivo().getNome());
			String fileName = prefix + "/" + theEntidade.getTitulo() + "."
					+ FilenameUtils.getExtension(theMultipartFile.getOriginalFilename());
			Arquivo theFile = new Arquivo(null, fileName, theS3Service.uploadFile(
					theVideoUtilities.getInputStream(theMultipartFile), fileName, theMultipartFile.getContentType()));
			theEntidade.setTheArquivo(theFile);
		}
		return theIVideoRepository.save(theEntidade);
	}

	public void delete(Long id) {

		if (UserLoggedInService.authenticated() == null
				|| !UserLoggedInService.authenticated().hasRole(Perfil.PROPRIETARIO)) {
			throw new AuthorizationException("Acesso negado");
		}
		try {
			if (find(id).getTheArquivo() != null) {
				theS3Service.deleteFile(find(id).getTheArquivo().getNome());
			}
			theIVideoRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há Entidades relacionadas");
		}
	}
}
