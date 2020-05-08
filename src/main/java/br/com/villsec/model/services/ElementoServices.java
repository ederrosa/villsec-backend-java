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

import br.com.villsec.model.entities.domain.Elemento;
import br.com.villsec.model.entities.domain.File;
import br.com.villsec.model.entities.enums.Perfil;
import br.com.villsec.model.repository.IElementoRepository;
import br.com.villsec.model.services.exceptions.AuthorizationException;
import br.com.villsec.model.services.exceptions.DataIntegrityException;
import br.com.villsec.model.services.exceptions.ObjectNotFoundException;
import br.com.villsec.model.services.utilities.FileUtilities;

@Service
public class ElementoServices {

	@Autowired
	private IElementoRepository theElementoRepository;

	@Autowired
	private S3Service theS3Service;

	@Autowired
	private FileUtilities theFileUtilities;

	@Value("${prefix.elemento.profile}")
	private String prefix;

	@Transactional
	public Elemento insert(Elemento theEntidade, MultipartFile theMultipartFile) {

		if (UserLoggedInService.authenticated() == null
				|| !UserLoggedInService.authenticated().hasRole(Perfil.PROPRIETARIO)) {
			throw new AuthorizationException("Acesso negado");
		}
		theEntidade.setId(null);
		String fileName = prefix + theEntidade.getTipoElemento().getDescricao() + "/" + theEntidade.getTitulo()
				+ "." + FilenameUtils.getExtension(theMultipartFile.getOriginalFilename());
		File theFile = new File(null, fileName, theS3Service.uploadFile(
				theFileUtilities.getInputStream(theMultipartFile), fileName, theMultipartFile.getContentType()));
		theEntidade.setElemento(theFile);
		return theElementoRepository.save(theEntidade);
	}

	public Elemento find(Long id) {
		Optional<Elemento> theEntidade = theElementoRepository.findById(id);
		return theEntidade.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Elemento.class.getSimpleName()));
	}

	public Page<Elemento> findAllPage(Integer page, Integer linesPerPage, String orderBy, String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return theElementoRepository.findAll(pageRequest);
	}

	public Elemento update(Elemento theEntidade, MultipartFile theMultipartFile) {

		if (UserLoggedInService.authenticated() == null
				|| !UserLoggedInService.authenticated().hasRole(Perfil.PROPRIETARIO)) {
			throw new AuthorizationException("Acesso negado");
		}
		if (theMultipartFile != null && !theMultipartFile.isEmpty()) {
			theS3Service.deleteFile(theEntidade.getElemento().getNome());
			String fileName = prefix + theEntidade.getTipoElemento().getDescricao() + "/"
					+ theEntidade.getTitulo() + "."
					+ FilenameUtils.getExtension(theMultipartFile.getOriginalFilename());
			File theFile = new File(null, fileName, theS3Service.uploadFile(
					theFileUtilities.getInputStream(theMultipartFile), fileName, theMultipartFile.getContentType()));
			theEntidade.setElemento(theFile);
		}
		return theElementoRepository.save(theEntidade);
	}

	public void delete(Long id) {

		if (UserLoggedInService.authenticated() == null
				|| !UserLoggedInService.authenticated().hasRole(Perfil.PROPRIETARIO)) {
			throw new AuthorizationException("Acesso negado");
		}
		try {
			if (find(id).getElemento() != null) {
				theS3Service.deleteFile(find(id).getElemento().getNome());
			}
			theElementoRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há Entidades relacionadas");
		}
	}

}
