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

import br.com.villsec.model.entities.domain.Arquivo;
import br.com.villsec.model.entities.domain.Imagem;
import br.com.villsec.model.entities.enums.Perfil;
import br.com.villsec.model.repository.IImagemRepository;
import br.com.villsec.model.services.exceptions.AuthorizationException;
import br.com.villsec.model.services.exceptions.DataIntegrityException;
import br.com.villsec.model.services.exceptions.ObjectNotFoundException;
import br.com.villsec.model.services.utilities.ImageUtilities;

@Service
public class ImagemServices {

	@Autowired
	private IImagemRepository theIImagemRepository;

	@Autowired
	private S3Service theS3Service;

	@Autowired
	private ImageUtilities theImageUtilities;

	@Value("${prefix.imagem.profile}")
	private String prefix;

	@Transactional
	public Imagem insert(Imagem theEntidade, MultipartFile theMultipartFile) {

		if (UserLoggedInService.authenticated() == null
				|| !UserLoggedInService.authenticated().hasRole(Perfil.PROPRIETARIO)) {
			throw new AuthorizationException("Acesso negado");
		}
		theEntidade.setId(null);
		if (theMultipartFile != null && !theMultipartFile.isEmpty()) {
			String fileName = this.prefix + "/" + theEntidade.getTitulo() + "."
					+ FilenameUtils.getExtension(theMultipartFile.getOriginalFilename());
			Arquivo theFile = new Arquivo(null, fileName,
					this.theS3Service.uploadFile(this.theImageUtilities.getInputStream(theMultipartFile), fileName,
							theMultipartFile.getContentType()));
			theEntidade.setTheArquivo(theFile);
		}
		return this.theIImagemRepository.save(theEntidade);
	}

	public Imagem find(Long id) {
		Optional<Imagem> theEntidade = this.theIImagemRepository.findById(id);
		return theEntidade.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Imagem.class.getSimpleName()));
	}

	public List<Imagem> findAll() {
		return this.theIImagemRepository.findAll();
	}

	public Page<Imagem> findAllPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return this.theIImagemRepository.findAll(pageRequest);
	}

	public Imagem update(Imagem theEntidade, MultipartFile theMultipartFile) {

		if (UserLoggedInService.authenticated() == null
				|| !UserLoggedInService.authenticated().hasRole(Perfil.PROPRIETARIO)) {
			throw new AuthorizationException("Acesso negado");
		}
		if (theMultipartFile != null && !theMultipartFile.isEmpty()) {
			this.theS3Service.deleteFile(theEntidade.getTheArquivo().getNome());
			String fileName = this.prefix + "/" + theEntidade.getTitulo() + "."
					+ FilenameUtils.getExtension(theMultipartFile.getOriginalFilename());
			Arquivo theFile = new Arquivo(null, fileName,
					this.theS3Service.uploadFile(this.theImageUtilities.getInputStream(theMultipartFile), fileName,
							theMultipartFile.getContentType()));
			theEntidade.setTheArquivo(theFile);
		}
		return this.theIImagemRepository.save(theEntidade);
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
			this.theIImagemRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há Entidades relacionadas");
		}
	}

}
