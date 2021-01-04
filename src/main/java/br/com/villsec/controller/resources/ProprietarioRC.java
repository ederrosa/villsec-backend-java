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

import br.com.villsec.controller.helpers.ProprietarioVHWeb;
import br.com.villsec.model.entities.domain.Proprietario;
import br.com.villsec.model.services.ProprietarioServices;
import br.com.villsec.model.services.dtos.ProprietarioDTO;

@RestController
@RequestMapping(value = "/proprietarios")
public class ProprietarioRC {

	@Autowired
	private ProprietarioServices theProprietarioServices;

	@PreAuthorize("hasAnyRole('ADMINISTRADOR') OR hasAnyRole('PROPRIETARIO')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		find(id);
		theProprietarioServices.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ProprietarioDTO> find(@PathVariable Long id) {
		ProprietarioDTO obj = new ProprietarioDTO(theProprietarioServices.find(id));
		return ResponseEntity.ok().body(obj);
	}

	@PreAuthorize("hasAnyRole('ADMINISTRADOR') OR hasAnyRole('PROPRIETARIO')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ProprietarioDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Proprietario> list = theProprietarioServices.findAllPage(page, linesPerPage, orderBy, direction);
		Page<ProprietarioDTO> listDTO = list.map(obj -> new ProprietarioDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}
	
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid ProprietarioDTO objNewDTO,
			@RequestPart(name = "file", required = true) MultipartFile theMultipartFile) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(
				theProprietarioServices.insert(new ProprietarioVHWeb().create(objNewDTO), theMultipartFile).getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	

	@PreAuthorize("hasAnyRole('ADMINISTRADOR') OR hasAnyRole('PROPRIETARIO')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid ProprietarioDTO objDTO, @PathVariable Long id,
			@RequestPart(name = "file", required = false) MultipartFile theMultipartFile) {
		objDTO.setId(id);
		Proprietario theProprietario = theProprietarioServices.find(id);
		new ProprietarioVHWeb().update(theProprietario, objDTO);
		theProprietario = theProprietarioServices.update(theProprietario, theMultipartFile);
		return ResponseEntity.noContent().build();
	}	
}
