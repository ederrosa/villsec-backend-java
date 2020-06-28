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

import br.com.villsec.model.services.S3Service;
import br.com.villsec.model.services.UserLoggedInService;
import br.com.villsec.model.services.utilities.ImageUtilities;
import br.com.villsec.model.entities.domain.Proprietario;
import br.com.villsec.model.entities.enums.Perfil;
import br.com.villsec.model.services.exceptions.AuthorizationException;
import br.com.villsec.model.services.exceptions.DataIntegrityException;
import br.com.villsec.model.services.exceptions.ObjectNotFoundException;
import br.com.villsec.model.services.utilities.CodeUtilities;
import br.com.villsec.model.repository.IProprietarioRepository;

@Service
public class ProprietarioServices {

	@Autowired
	private IProprietarioRepository theIProprietarioRepository;

	@Autowired
	private UserLoggedInService theUserLoggedInService;

	@Autowired
	private S3Service theS3Service;

	@Autowired
	private ImageUtilities theImageUtilities;

	@Value("${prefix.perfil.profile}")
	private String prefix;

	@Value("${image.profile.size}")
	private Integer size;

	@Transactional
	public Proprietario insert(Proprietario theProprietario, MultipartFile theMultipartFile) {

		if (UserLoggedInService.authenticated() != null
				&& UserLoggedInService.authenticated().hasRole(Perfil.ADMINISTRADOR)) {
			theProprietario.getTheAutenticacaoSS()
					.setMatricula(new CodeUtilities().geradorDeMatricula(theUserLoggedInService));
			BufferedImage jpgImage = theImageUtilities.getJpgImageFromFile(theMultipartFile);
			jpgImage = theImageUtilities.cropSquare(jpgImage);
			jpgImage = theImageUtilities.resize(jpgImage, size);
			theProprietario.getTheAutenticacaoSS()
					.setNomeImgPerfil(prefix + theProprietario.getTheAutenticacaoSS().getMatricula() + "/imgPerfil."
							+ FilenameUtils.getExtension(theMultipartFile.getOriginalFilename()));
			theProprietario.getTheAutenticacaoSS()
					.setUriImgPerfil(theS3Service.uploadFile(
							theImageUtilities.getInputStream(jpgImage,
									FilenameUtils.getExtension(theMultipartFile.getOriginalFilename())),
							theProprietario.getTheAutenticacaoSS().getNomeImgPerfil(), "image"));
			theProprietario = theIProprietarioRepository.save(theProprietario);
			return theProprietario;
		}
		return null;
	}

	public Proprietario find(Long id) {

		Optional<Proprietario> theProprietario = theIProprietarioRepository.findById(id);
		return theProprietario.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Proprietario.class.getSimpleName()));
	}

	public Page<Proprietario> findAllPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return theIProprietarioRepository.findAll(pageRequest);
	}

	public Proprietario update(Proprietario theProprietario, MultipartFile theMultipartFile) {

		if (UserLoggedInService.authenticated() != null
				&& theUserLoggedInService.userLoggedIn().getId() == theProprietario.getId()
				|| UserLoggedInService.authenticated().hasRole(Perfil.ADMINISTRADOR)) {
			if (theMultipartFile != null && !theMultipartFile.isEmpty()) {
				BufferedImage jpgImage = theImageUtilities.getJpgImageFromFile(theMultipartFile);
				jpgImage = theImageUtilities.cropSquare(jpgImage);
				jpgImage = theImageUtilities.resize(jpgImage, size);
				theProprietario.getTheAutenticacaoSS()
						.setNomeImgPerfil(prefix + theProprietario.getTheAutenticacaoSS().getMatricula() + "/imgPerfil."
								+ FilenameUtils.getExtension(theMultipartFile.getOriginalFilename()));
				theProprietario.getTheAutenticacaoSS()
						.setUriImgPerfil(
								theS3Service
										.uploadFile(
												theImageUtilities.getInputStream(jpgImage,
														FilenameUtils
																.getExtension(theMultipartFile.getOriginalFilename())),
												theProprietario.getTheAutenticacaoSS().getNomeImgPerfil(), "image"));
			}
			return theIProprietarioRepository.save(theProprietario);
		} else {
			throw new AuthorizationException(
					"Acesso negado! - Você não possui permissão para Alterar esta Proprietario!");
		}
	}

	public void delete(Long id) {
		try {
			if (UserLoggedInService.authenticated() != null && theUserLoggedInService.userLoggedIn().getId() == id
					|| UserLoggedInService.authenticated().hasRole(Perfil.ADMINISTRADOR)) {
				if (find(id).getTheAutenticacaoSS().getNomeImgPerfil() != null) {
					theS3Service.deleteFile(find(id).getTheAutenticacaoSS().getNomeImgPerfil());
				}
				theIProprietarioRepository.deleteById(id);
			} else {
				throw new AuthorizationException(
						"Acesso negado! - Você não possui permissão para excluir esta Proprietario!");
			}
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há Proprietarios relacionadas");
		}
	}
}
