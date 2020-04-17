package br.com.villsec.model.services;

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

import br.com.villsec.model.entities.domain.Evento;
import br.com.villsec.model.entities.domain.File;
import br.com.villsec.model.entities.enums.Perfil;
import br.com.villsec.model.repository.IEventoRepository;
import br.com.villsec.model.services.exceptions.AuthorizationException;
import br.com.villsec.model.services.exceptions.DataIntegrityException;
import br.com.villsec.model.services.exceptions.ObjectNotFoundException;
import br.com.villsec.model.services.utilities.FileUtilities;

@Service
public class EventoServices {

	@Autowired
	private IEventoRepository theEventoRepository;

	@Autowired
	private S3Service theS3Service;

	@Autowired
	private FileUtilities theFileUtilities;

	@Value("${prefix.elemento.profile}")
	private String prefix;

	@Transactional
	public Evento insert(Evento theEntidade, MultipartFile theMultipartFile) {

		if (UserLoggedInService.authenticated() == null
				|| !UserLoggedInService.authenticated().hasRole(Perfil.PROPRIETARIO)) {
			throw new AuthorizationException("Acesso negado");
		}
		theEntidade.setId(null);
		File theFile = new File(null,
				prefix + "/" + theEntidade.getTipoEvento().getDescricao() + "/" + theEntidade.getNome() + "."
						+ FilenameUtils.getExtension(theMultipartFile.getOriginalFilename()),
				theS3Service.uploadFile(theFileUtilities.getInputStream(theMultipartFile),
						theEntidade.getFolder().getNome(), theMultipartFile.getContentType()));
		theEntidade.setFolder(theFile);
		return theEventoRepository.save(theEntidade);
	}

	public Evento find(Long id) {
		Optional<Evento> theEntidade = theEventoRepository.findById(id);
		return theEntidade.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Evento.class.getSimpleName()));
	}

	public Page<Evento> findAllPage(Integer page, Integer linesPerPage, String orderBy, String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return theEventoRepository.findAll(pageRequest);
	}

	public Evento update(Evento theEntidade, MultipartFile theMultipartFile) {

		if (UserLoggedInService.authenticated() == null
				|| !UserLoggedInService.authenticated().hasRole(Perfil.PROPRIETARIO)) {
			throw new AuthorizationException("Acesso negado");
		}
		if (theMultipartFile != null && !theMultipartFile.isEmpty()) {
			theS3Service.deleteFile(theEntidade.getFolder().getNome());
			File theFile = new File(null,
					prefix + "/" + theEntidade.getTipoEvento().getDescricao() + "/" + theEntidade.getNome() + "."
							+ FilenameUtils.getExtension(theMultipartFile.getOriginalFilename()),
					theS3Service.uploadFile(theFileUtilities.getInputStream(theMultipartFile),
							theEntidade.getFolder().getNome(), theMultipartFile.getContentType()));
			theEntidade.setFolder(theFile);
		}
		return theEventoRepository.save(theEntidade);
	}

	public void delete(Long id) {

		if (UserLoggedInService.authenticated() == null
				|| !UserLoggedInService.authenticated().hasRole(Perfil.PROPRIETARIO)) {
			throw new AuthorizationException("Acesso negado");
		}
		try {
			if (find(id).getFolder() != null) {
				theS3Service.deleteFile(find(id).getFolder().getNome());
			}
			theEventoRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há Entidades relacionadas");
		}
	}

}