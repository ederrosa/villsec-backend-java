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

import br.com.villsec.controller.helpers.SeguidorVHWeb;
import br.com.villsec.model.entities.domain.Seguidor;
import br.com.villsec.model.services.SeguidorServices;
import br.com.villsec.model.services.dtos.SeguidorDTO;

@RestController
@RequestMapping(value = "/seguidores")
public class SeguidorRC {

	@Autowired
	private SeguidorServices theSeguidorServices;
	
	@PreAuthorize("hasAnyRole('ADMINISTRADOR') OR hasAnyRole('SEGUIDOR')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		find(id);
		theSeguidorServices.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<SeguidorDTO> find(@PathVariable Long id) {
		SeguidorDTO obj = new SeguidorDTO(theSeguidorServices.find(id));
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<SeguidorDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Seguidor> list = theSeguidorServices.findAllPage(page, linesPerPage, orderBy, direction);
		Page<SeguidorDTO> listDTO = list.map(obj -> new SeguidorDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid SeguidorDTO objNewDTO,
			@RequestPart(name = "file", required = true) MultipartFile theMultipartFile) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(
						theSeguidorServices.insert(new SeguidorVHWeb().create(objNewDTO), theMultipartFile).getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}	

	@PreAuthorize("hasAnyRole('ADMINISTRADOR') OR hasAnyRole('SEGUIDOR')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid SeguidorDTO objDTO, @PathVariable Long id,
			@RequestPart(name = "file", required = false) MultipartFile theMultipartFile) {
		objDTO.setId(id);
		Seguidor theSeguidor = theSeguidorServices.find(id);
		new SeguidorVHWeb().update(theSeguidor, objDTO);
		theSeguidor = theSeguidorServices.update(theSeguidor, theMultipartFile);
		return ResponseEntity.noContent().build();
	}	
}
