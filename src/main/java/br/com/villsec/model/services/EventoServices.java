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

import br.com.villsec.model.entities.domain.Arquivo;
import br.com.villsec.model.entities.domain.Evento;
import br.com.villsec.model.entities.domain.Proprietario;
import br.com.villsec.model.entities.domain.Seguidor;
import br.com.villsec.model.entities.enums.Perfil;
import br.com.villsec.model.repository.IEventoRepository;
import br.com.villsec.model.services.exceptions.AuthorizationException;
import br.com.villsec.model.services.exceptions.DataIntegrityException;
import br.com.villsec.model.services.exceptions.ObjectNotFoundException;
import br.com.villsec.model.services.utilities.ImageUtilities;

@Service
public class EventoServices {

	@Autowired
	private IEventoRepository theEventoRepository;

	@Autowired
	private S3Service theS3Service;

	@Autowired
	private SeguidorServices theSeguidoServices;

	@Autowired
	private ProprietarioServices theProprietarioServices;

	@Autowired
	private IEmailServices theIEmailServices;

	@Autowired
	private ImageUtilities theImageUtilities;

	@Value("${prefix.evento.profile}")
	private String prefix;

	@Value("${image.profile.size}")
	private Integer size;

	@Value("${default.principal}")
	private Long proprietarioId;

	@Transactional
	public Evento insert(Evento theEvento, MultipartFile theMultipartFile) {

		if (UserLoggedInService.authenticated() == null
				|| !UserLoggedInService.authenticated().hasRole(Perfil.PROPRIETARIO)) {
			throw new AuthorizationException("Acesso negado");
		}
		theEvento.setId(null);
		BufferedImage jpgImage = this.theImageUtilities.getJpgImageFromFile(theMultipartFile);
		String fileName = this.prefix + theEvento.getTipoEvento().getDescricao() + "/" + theEvento.getNome() + "."
				+ FilenameUtils.getExtension(theMultipartFile.getOriginalFilename());
		Arquivo theFile = new Arquivo(null, fileName,
				this.theS3Service.uploadFile(
						this.theImageUtilities.getInputStream(jpgImage,
								FilenameUtils.getExtension(theMultipartFile.getOriginalFilename())),
						fileName, theMultipartFile.getContentType()));
		theEvento.setFolder(theFile);
		return this.theEventoRepository.save(theEvento);
	}

	public Evento find(Long id) {
		Optional<Evento> theEvento = this.theEventoRepository.findById(id);
		return theEvento.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Evento.class.getSimpleName()));
	}

	public Page<Evento> findAllPage(Integer page, Integer linesPerPage, String orderBy, String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return this.theEventoRepository.findAll(pageRequest);
	}

	public Evento update(Evento theEvento, MultipartFile theMultipartFile) {

		if (UserLoggedInService.authenticated() == null
				|| !UserLoggedInService.authenticated().hasRole(Perfil.PROPRIETARIO)) {
			throw new AuthorizationException("Acesso negado");
		}
		if (theMultipartFile != null && !theMultipartFile.isEmpty()) {
			this.theS3Service.deleteFile(theEvento.getFolder().getNome());
			BufferedImage jpgImage = this.theImageUtilities.getJpgImageFromFile(theMultipartFile);
			String fileName = this.prefix + theEvento.getTipoEvento().getDescricao() + "/" + theEvento.getNome() + "."
					+ FilenameUtils.getExtension(theMultipartFile.getOriginalFilename());
			Arquivo theFile = new Arquivo(null, fileName,
					this.theS3Service.uploadFile(
							this.theImageUtilities.getInputStream(jpgImage,
									FilenameUtils.getExtension(theMultipartFile.getOriginalFilename())),
							fileName, theMultipartFile.getContentType()));
			theEvento.setFolder(theFile);
		}
		return this.theEventoRepository.save(theEvento);
	}

	public void delete(Long id) {

		if (UserLoggedInService.authenticated() == null
				|| !UserLoggedInService.authenticated().hasRole(Perfil.PROPRIETARIO)) {
			throw new AuthorizationException("Acesso negado");
		}
		try {
			if (find(id).getFolder() != null) {
				this.theS3Service.deleteFile(find(id).getFolder().getNome());
			}
			this.theEventoRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há Eventos relacionados");
		}
	}

	public void enviarAlerta(Long id) {
		if (UserLoggedInService.authenticated() == null
				|| !UserLoggedInService.authenticated().hasRole(Perfil.PROPRIETARIO)) {
			throw new AuthorizationException("Acesso negado");
		}
		Evento theEvento = this.find(id);
		if (theEvento.isAlerta()) {
			throw new AuthorizationException(
					"Acesso negado, alertas ja foram enviados anteriormente para este evento!!");
		}
		Proprietario theProprietario = this.theProprietarioServices.find(this.proprietarioId);
		List<Seguidor> theSeguidorList = this.theSeguidoServices
				.findAllByCidade(theEvento.getTheEndereco().getCidade());
		for (Seguidor theSeguidor : theSeguidorList) {
			this.theIEmailServices.sendAlertaEventoHtmlEmail(theEvento, theSeguidor.getTheEmail(), theProprietario);
		}
		theEvento.setAlerta(true);
		this.theEventoRepository.save(theEvento);
	}
}
