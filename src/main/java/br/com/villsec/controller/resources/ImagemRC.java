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

import br.com.villsec.controller.helpers.ImagemVHWeb;
import br.com.villsec.model.entities.domain.Imagem;
import br.com.villsec.model.services.ImagemServices;
import br.com.villsec.model.services.dtos.ImagemDTO;

@RestController
@RequestMapping(value = "/imagens")
public class ImagemRC {

	@Autowired
	private ImagemServices theElementoServices;
	
	@PreAuthorize("hasAnyRole('PROPRIETARIO')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		find(id);
		theElementoServices.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ImagemDTO> find(@PathVariable Long id) {
		ImagemDTO obj = new ImagemDTO(theElementoServices.find(id));
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ImagemDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "titulo") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Imagem> list = theElementoServices.findAllPage(page, linesPerPage, orderBy, direction);
		Page<ImagemDTO> listDTO = list.map(obj -> new ImagemDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}

	@PreAuthorize("hasAnyRole('PROPRIETARIO')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid ImagemDTO objNewDTO,
			@RequestPart(name = "file", required = false) MultipartFile theMultipartFile) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(theElementoServices
				.insert(new ImagemVHWeb().create(objNewDTO), theMultipartFile).getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@PreAuthorize("hasAnyRole('PROPRIETARIO')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid ImagemDTO objDTO, @PathVariable Long id,
			@RequestPart(name = "file", required = false) MultipartFile theMultipartFile) {
		objDTO.setId(id);
		Imagem theElemento = theElementoServices.find(id);
		new ImagemVHWeb().update(theElemento, objDTO);
		theElemento = theElementoServices.update(theElemento, theMultipartFile);
		return ResponseEntity.noContent().build();
	}	
}
