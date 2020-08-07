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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.villsec.model.entities.domain.Proprietario;
import br.com.villsec.model.entities.enums.Perfil;
import br.com.villsec.model.repository.IProprietarioRepository;
import br.com.villsec.model.services.exceptions.AuthorizationException;
import br.com.villsec.model.services.exceptions.DataIntegrityException;
import br.com.villsec.model.services.exceptions.ObjectNotFoundException;
import br.com.villsec.model.services.utilities.CodeUtilities;
import br.com.villsec.model.services.utilities.ImageUtilities;

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

	@Autowired
	private BCryptPasswordEncoder theBCryptPasswordEncoder;

	@Value("${prefix.perfil.profile}")
	private String prefix;

	@Value("${image.profile.size}")
	private Integer size;

	@Value("${default.principal}")
	private Long principalID;
	
	@Transactional
	public Proprietario insert(Proprietario theProprietario, MultipartFile theMultipartFile) {
		if (UserLoggedInService.authenticated() != null
				&& UserLoggedInService.authenticated().hasRole(Perfil.ADMINISTRADOR)) {
			theProprietario.getTheAutenticacaoSS()
					.setMatricula(new CodeUtilities().registrationGenerator(this.theUserLoggedInService));
			BufferedImage jpgImage = this.theImageUtilities.getJpgImageFromFile(theMultipartFile);
			jpgImage = this.theImageUtilities.cropSquare(jpgImage);
			jpgImage = this.theImageUtilities.resize(jpgImage, this.size);
			theProprietario.getTheAutenticacaoSS()
					.setNomeImgPerfil(this.prefix + theProprietario.getTheAutenticacaoSS().getMatricula()
							+ "/imgPerfil." + FilenameUtils.getExtension(theMultipartFile.getOriginalFilename()));
			theProprietario.getTheAutenticacaoSS()
					.setUriImgPerfil(this.theS3Service.uploadFile(
							this.theImageUtilities.getInputStream(jpgImage,
									FilenameUtils.getExtension(theMultipartFile.getOriginalFilename())),
							theProprietario.getTheAutenticacaoSS().getNomeImgPerfil(), "image"));
			theProprietario.getTheAutenticacaoSS()
					.setSenha(theBCryptPasswordEncoder.encode(theProprietario.getTheAutenticacaoSS().getSenha()));
			return this.theIProprietarioRepository.save(theProprietario);
		}
		return null;
	}

	public Proprietario find(Long id) {

		Optional<Proprietario> theProprietario = this.theIProprietarioRepository.findById(id);
		return theProprietario.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Proprietario.class.getSimpleName()));
	}

	public Page<Proprietario> findAllPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		if (UserLoggedInService.authenticated() != null) {
			switch (UserLoggedInService.authenticated().getTipoUsuario()) {
			case PROPRIETARIO:
				return this.theIProprietarioRepository.findAllByTheAutenticacaoSS(UserLoggedInService.authenticated(),
						pageRequest);
			case ADMINISTRADOR:
				return this.theIProprietarioRepository.findAll(pageRequest);
			case SEGUIDOR:
				return this.theIProprietarioRepository.findAllByTheAutenticacaoSS(this.find(principalID).getTheAutenticacaoSS(),
						pageRequest);
			default:
				throw new AuthorizationException("Acesso negado! - Você não possui permissão para executar esta ação!");
			}
		} else {
			throw new AuthorizationException("Acesso negado! - Você não possui permissão para executar esta ação!");
		}
	}

	public Proprietario update(Proprietario theProprietario, MultipartFile theMultipartFile) {

		if (UserLoggedInService.authenticated() != null
				&& this.theUserLoggedInService.userLoggedIn().getId() == theProprietario.getId()
				|| UserLoggedInService.authenticated() != null && UserLoggedInService.authenticated().hasRole(Perfil.ADMINISTRADOR)) {
			if (theMultipartFile != null && !theMultipartFile.isEmpty()) {
				BufferedImage jpgImage = this.theImageUtilities.getJpgImageFromFile(theMultipartFile);
				jpgImage = this.theImageUtilities.cropSquare(jpgImage);
				jpgImage = this.theImageUtilities.resize(jpgImage, this.size);
				theProprietario.getTheAutenticacaoSS()
						.setNomeImgPerfil(this.prefix + theProprietario.getTheAutenticacaoSS().getMatricula()
								+ "/imgPerfil." + FilenameUtils.getExtension(theMultipartFile.getOriginalFilename()));
				theProprietario.getTheAutenticacaoSS()
						.setUriImgPerfil(
								this.theS3Service
										.uploadFile(
												this.theImageUtilities.getInputStream(jpgImage,
														FilenameUtils
																.getExtension(theMultipartFile.getOriginalFilename())),
												theProprietario.getTheAutenticacaoSS().getNomeImgPerfil(), "image"));
			}
			if (theProprietario.getTheAutenticacaoSS().getSenha().length() < 21) {
				theProprietario.getTheAutenticacaoSS()
						.setSenha(this.theBCryptPasswordEncoder.encode(theProprietario.getTheAutenticacaoSS().getSenha()));
			}
			return this.theIProprietarioRepository.save(theProprietario);
		} else {
			throw new AuthorizationException(
					"Acesso negado! - Você não possui permissão para Alterar esta Proprietario!");
		}
	}

	public void delete(Long id) {
		try {
			if (UserLoggedInService.authenticated() != null && this.theUserLoggedInService.userLoggedIn().getId() == id
					|| UserLoggedInService.authenticated().hasRole(Perfil.ADMINISTRADOR)) {
				if (find(id).getTheAutenticacaoSS().getNomeImgPerfil() != null) {
					this.theS3Service.deleteFile(find(id).getTheAutenticacaoSS().getNomeImgPerfil());
				}
				this.theIProprietarioRepository.deleteById(id);
			} else {
				throw new AuthorizationException(
						"Acesso negado! - Você não possui permissão para excluir esta Proprietario!");
			}
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há Proprietarios relacionadas");
		}
	}
}
