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

import br.com.villsec.model.entities.domain.Musica;
import br.com.villsec.model.entities.domain.Album;
import br.com.villsec.model.entities.domain.Arquivo;
import br.com.villsec.model.entities.enums.Perfil;
import br.com.villsec.model.repository.IMusicaRepository;
import br.com.villsec.model.services.exceptions.AuthorizationException;
import br.com.villsec.model.services.exceptions.DataIntegrityException;
import br.com.villsec.model.services.exceptions.ObjectNotFoundException;
import br.com.villsec.model.services.utilities.AudioUtilities;

@Service
public class MusicaServices {

	@Autowired
	private IMusicaRepository theMusicaRepository;
	
	@Autowired
	private AlbumServices theAlbumServices;
	
	@Autowired
	private S3Service theS3Service;
	
	@Autowired
	private AudioUtilities theAudioUtilities;
	
	@Value("${prefix.album.profile}")
	private String prefix;

	@Transactional
	public Musica insert(Musica theEntidade, MultipartFile theMultipartFile, Long theAlbumID) {

		if (UserLoggedInService.authenticated() == null
				&& !UserLoggedInService.authenticated().hasRole(Perfil.PROPRIETARIO)) {
			throw new AuthorizationException("Acesso negado");
		}
		theEntidade.setId(null);
		Album theAlbum = this.theAlbumServices.find(theAlbumID);
		theAlbum.getTheMusicas().add(theEntidade);
		theEntidade.setTheAlbum(theAlbum);
		String fileName = this.prefix + theAlbum.getNome() + "/" + theEntidade.getNome() + "."
				+ FilenameUtils.getExtension(theMultipartFile.getOriginalFilename());
		Arquivo theFile = new Arquivo(null, fileName, this.theS3Service.uploadFile(
				this.theAudioUtilities.getInputStream(theMultipartFile), fileName, theMultipartFile.getContentType()));
		theEntidade.setTheArquivo(theFile);
		return this.theMusicaRepository.save(theEntidade);
	}

	public Musica find(Long id) {
		Optional<Musica> theEntidade = this.theMusicaRepository.findById(id);
		return theEntidade.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Musica.class.getSimpleName()));
	}
	
	public List<Musica> findAll(Album theAlbum){
		return this.theMusicaRepository.findAllByTheAlbum(theAlbum);
	}

	public Page<Musica> findAllPage(Integer page, Integer linesPerPage, String orderBy, String direction,
			Long theAlbum) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return this.theMusicaRepository.findAllByTheAlbum(this.theAlbumServices.find(theAlbum), pageRequest);
	}

	public Musica update(Musica theEntidade, MultipartFile theMultipartFile) {

		if (UserLoggedInService.authenticated() == null
				&& !UserLoggedInService.authenticated().hasRole(Perfil.PROPRIETARIO)) {
			throw new AuthorizationException("Acesso negado");
		}
		if (theMultipartFile != null && !theMultipartFile.isEmpty()) {
			String fileName = this.prefix + theEntidade.getTheAlbum().getNome() + "/" + theEntidade.getNome() + "."
					+ FilenameUtils.getExtension(theMultipartFile.getOriginalFilename());
			Arquivo theFile = new Arquivo(null, fileName, this.theS3Service.uploadFile(
					this.theAudioUtilities.getInputStream(theMultipartFile), fileName, theMultipartFile.getContentType()));
			theEntidade.setTheArquivo(theFile);
		}
		return this.theMusicaRepository.save(theEntidade);
	}

	public void delete(Long id) {

		if (UserLoggedInService.authenticated() == null
				|| !UserLoggedInService.authenticated().hasRole(Perfil.PROPRIETARIO)) {
			throw new AuthorizationException("Acesso negado");
		}
		try {
			if (find(id).getTheArquivo() != null) {
				this.theS3Service.deleteFile(find(id).getTheArquivo().getNome());
			}
			this.theMusicaRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há Entidades relacionadas");
		}
	}
}
