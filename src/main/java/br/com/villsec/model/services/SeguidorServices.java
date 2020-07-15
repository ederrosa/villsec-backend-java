package br.com.villsec.model.services;

import java.awt.image.BufferedImage;
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

import br.com.villsec.model.services.S3Service;
import br.com.villsec.model.services.UserLoggedInService;
import br.com.villsec.model.services.utilities.ImageUtilities;
import br.com.villsec.model.entities.domain.Seguidor;
import br.com.villsec.model.entities.enums.Perfil;
import br.com.villsec.model.services.exceptions.AuthorizationException;
import br.com.villsec.model.services.exceptions.DataIntegrityException;
import br.com.villsec.model.services.exceptions.ObjectNotFoundException;
import br.com.villsec.model.services.utilities.CodeUtilities;
import br.com.villsec.model.repository.ISeguidorRepository;

@Service
public class SeguidorServices {

	@Autowired
	private ISeguidorRepository theISeguidorRepository;

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
	public Seguidor insert(Seguidor theSeguidor, MultipartFile theMultipartFile) {

		theSeguidor.getTheAutenticacaoSS().setMatricula(new CodeUtilities().registrationGenerator(theUserLoggedInService));
		BufferedImage jpgImage = theImageUtilities.getJpgImageFromFile(theMultipartFile);
		jpgImage = theImageUtilities.cropSquare(jpgImage);
		jpgImage = theImageUtilities.resize(jpgImage, size);
		theSeguidor.getTheAutenticacaoSS().setNomeImgPerfil(prefix + theSeguidor.getTheAutenticacaoSS().getMatricula()
				+ "/imgPerfil." + FilenameUtils.getExtension(theMultipartFile.getOriginalFilename()));
		theSeguidor.getTheAutenticacaoSS()
				.setUriImgPerfil(theS3Service.uploadFile(
						theImageUtilities.getInputStream(jpgImage,
								FilenameUtils.getExtension(theMultipartFile.getOriginalFilename())),
						theSeguidor.getTheAutenticacaoSS().getNomeImgPerfil(), "image"));
		theSeguidor = theISeguidorRepository.save(theSeguidor);
		return theSeguidor;
	}

	public Seguidor find(Long id) {

		if (UserLoggedInService.authenticated() == null) {
			throw new AuthorizationException("Acesso negado");
		}
		Optional<Seguidor> theSeguidor = theISeguidorRepository.findById(id);
		return theSeguidor.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Seguidor.class.getSimpleName()));
	}
	
	public List<Seguidor> findAllByCidade(String cidade){
		return this.theISeguidorRepository.findAllByTheEnderecoCidade(cidade);
	}

	public Page<Seguidor> findAllPage(Integer page, Integer linesPerPage, String orderBy, String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		if (UserLoggedInService.authenticated() != null) {
			switch (UserLoggedInService.authenticated().getTipoUsuario()) {
			case PROPRIETARIO:
				return theISeguidorRepository.findAll(pageRequest);
			case ADMINISTRADOR:
				return theISeguidorRepository.findAll(pageRequest);
			case SEGUIDOR:
				return theISeguidorRepository.findAllByTheAutenticacaoSS(UserLoggedInService.authenticated(),
						pageRequest);
			default:
				throw new AuthorizationException("Acesso negado! - Você não possui permissão para executar esta ação!");
			}
		} else {
			throw new AuthorizationException("Acesso negado! - Você não possui permissão para executar esta ação!");
		}
	}

	public Seguidor update(Seguidor theSeguidor, MultipartFile theMultipartFile) {

		if (UserLoggedInService.authenticated() != null
				&& theUserLoggedInService.userLoggedIn().getId() == theSeguidor.getId()
				|| UserLoggedInService.authenticated().hasRole(Perfil.ADMINISTRADOR)) {
			if (theMultipartFile != null && !theMultipartFile.isEmpty()) {
				BufferedImage jpgImage = theImageUtilities.getJpgImageFromFile(theMultipartFile);
				jpgImage = theImageUtilities.cropSquare(jpgImage);
				jpgImage = theImageUtilities.resize(jpgImage, size);
				theSeguidor.getTheAutenticacaoSS()
						.setNomeImgPerfil(prefix + theSeguidor.getTheAutenticacaoSS().getMatricula() + "/imgPerfil."
								+ FilenameUtils.getExtension(theMultipartFile.getOriginalFilename()));
				theSeguidor.getTheAutenticacaoSS()
						.setUriImgPerfil(
								theS3Service
										.uploadFile(
												theImageUtilities.getInputStream(jpgImage,
														FilenameUtils
																.getExtension(theMultipartFile.getOriginalFilename())),
												theSeguidor.getTheAutenticacaoSS().getNomeImgPerfil(), "image"));
			}
			return theISeguidorRepository.save(theSeguidor);
		} else {
			throw new AuthorizationException("Acesso negado! - Você não possui permissão para Alterar esta Seguidor!");
		}
	}

	public void delete(Long id) {
		try {
			if (UserLoggedInService.authenticated() != null && theUserLoggedInService.userLoggedIn().getId() == id
					|| UserLoggedInService.authenticated().hasRole(Perfil.ADMINISTRADOR)) {
				if (find(id).getTheAutenticacaoSS().getNomeImgPerfil() != null) {
					theS3Service.deleteFile(find(id).getTheAutenticacaoSS().getNomeImgPerfil());
				}
				theISeguidorRepository.deleteById(id);
			} else {
				throw new AuthorizationException(
						"Acesso negado! - Você não possui permissão para excluir esta Seguidor!");
			}
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há Seguidors relacionadas");
		}
	}
}
