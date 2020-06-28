package br.com.villsec.model.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.villsec.model.entities.domain.Galeria;
import br.com.villsec.model.entities.domain.Imagem;
import br.com.villsec.model.entities.domain.Video;
import br.com.villsec.model.entities.enums.Perfil;
import br.com.villsec.model.repository.IGaleriaRepository;
import br.com.villsec.model.services.exceptions.AuthorizationException;
import br.com.villsec.model.services.exceptions.DataIntegrityException;
import br.com.villsec.model.services.exceptions.ObjectNotFoundException;

@Service
public class GaleriaServices {

	@Autowired
	private IGaleriaRepository theGaleriaRepository;
	
	@Autowired
	private ImagemServices theImagemServices;
	
	@Autowired
	private VideoServices theVideoServices;
	
	@Transactional
	public Galeria insert(Galeria theEntidade) {

		if (UserLoggedInService.authenticated() == null
				&& !UserLoggedInService.authenticated().hasRole(Perfil.PROPRIETARIO)) {
			throw new AuthorizationException("Acesso negado");
		}
		theEntidade.setId(null);
		return theGaleriaRepository.save(theEntidade);
	}

	public Galeria find(Long id) {
		Optional<Galeria> theEntidade = theGaleriaRepository.findById(id);
		return theEntidade.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Galeria.class.getSimpleName()));
	}

	public Page<Galeria> findAllPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return theGaleriaRepository.findAll(pageRequest);
	}

	public Galeria update(Galeria theEntidade) {

		if (UserLoggedInService.authenticated() == null
				&& !UserLoggedInService.authenticated().hasRole(Perfil.PROPRIETARIO)) {
			throw new AuthorizationException("Acesso negado");
		}
		return theGaleriaRepository.save(theEntidade);
	}

	public void delete(Long id) {

		if (UserLoggedInService.authenticated() == null
				|| !UserLoggedInService.authenticated().hasRole(Perfil.PROPRIETARIO)) {
			throw new AuthorizationException("Acesso negado");
		}
		try {			
			for(Imagem theImagem : this.theImagemServices.findAll(this.find(id))) {
				this.theImagemServices.delete(theImagem.getId());			
			}
			for(Video theMusica : this.theVideoServices.findAll(this.find(id))) {
				this.theVideoServices.delete(theMusica.getId());			
			}
			theGaleriaRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há Entidades relacionadas");
		}
	}

}
