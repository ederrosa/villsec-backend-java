package br.com.villsec.controller.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.villsec.controller.helpers.EventoVHWeb;
import br.com.villsec.model.entities.domain.Evento;
import br.com.villsec.model.services.EventoServices;
import br.com.villsec.model.services.dtos.EventoDTO;

@RestController
@RequestMapping(value = "/eventos")
public class EventoRC {

	@Autowired
	private EventoServices theEventoServices;
	
	@PreAuthorize("hasAnyRole('PROPRIETARIO')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.theEventoServices.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('PROPRIETARIO')")
	@RequestMapping(value = "/alertas/{id}", method = RequestMethod.OPTIONS)
	public ResponseEntity<Void> enviarAlerta(@PathVariable Long id) {
		this.theEventoServices.enviarAlerta(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<EventoDTO> find(@PathVariable Long id) {
		EventoDTO obj = new EventoDTO(theEventoServices.find(id));
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<EventoDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "diaInicio") String orderBy,
			@RequestParam(value = "direction", defaultValue = "DESC") String direction) {
		Page<Evento> list = this.theEventoServices.findAllPage(page, linesPerPage, orderBy, direction);
		Page<EventoDTO> listDTO = list.map(obj -> new EventoDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}
	
	@PreAuthorize("hasAnyRole('PROPRIETARIO')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid EventoDTO objNewDTO,
			@RequestPart(name = "file", required = true) MultipartFile theMultipartFile) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(
						this.theEventoServices.insert(new EventoVHWeb().create(objNewDTO), theMultipartFile).getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}	

	@PreAuthorize("hasAnyRole('PROPRIETARIO')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid EventoDTO objDTO, @PathVariable Long id,
			@RequestPart(name = "file", required = false) MultipartFile theMultipartFile) {
		objDTO.setId(id);
		Evento theEvento = theEventoServices.find(id);
		new EventoVHWeb().update(theEvento, objDTO);
		theEvento = this.theEventoServices.update(theEvento, theMultipartFile);
		return ResponseEntity.noContent().build();
	}	
}
