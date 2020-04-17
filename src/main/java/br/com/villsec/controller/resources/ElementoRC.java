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

import br.com.villsec.controller.helpers.ElementoVHWeb;
import br.com.villsec.model.entities.domain.Elemento;
import br.com.villsec.model.services.ElementoServices;
import br.com.villsec.model.services.dtos.ElementoDTO;

@RestController
@RequestMapping(value = "/elementos")
public class ElementoRC {

	@Autowired
	private ElementoServices theElementoServices;

	@PreAuthorize("hasAnyRole('PROPRIETARIO')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid ElementoDTO objNewDTO,
			@RequestPart(name = "file", required = true) MultipartFile theMultipartFile) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(theElementoServices.insert(new ElementoVHWeb().create(objNewDTO), theMultipartFile).getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ElementoDTO> find(@PathVariable Long id) {
		ElementoDTO obj = new ElementoDTO(theElementoServices.find(id));
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ElementoDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Elemento> list = theElementoServices.findAllPage(page, linesPerPage, orderBy, direction);
		Page<ElementoDTO> listDTO = list.map(obj -> new ElementoDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}

	@PreAuthorize("hasAnyRole('PROPRIETARIO')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid ElementoDTO objDTO, @PathVariable Long id,
			@RequestPart(name = "file", required = false) MultipartFile theMultipartFile) {
		objDTO.setId(id);
		Elemento theElemento = theElementoServices.find(id);
		new ElementoVHWeb().update(theElemento, objDTO);
		theElemento = theElementoServices.update(theElemento, theMultipartFile);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('PROPRIETARIO')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		find(id);
		theElementoServices.delete(id);
		return ResponseEntity.noContent().build();
	}
}
