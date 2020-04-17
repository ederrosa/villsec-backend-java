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

import br.com.villsec.controller.helpers.AlbumVHWeb;
import br.com.villsec.model.entities.domain.Album;
import br.com.villsec.model.services.AlbumServices;
import br.com.villsec.model.services.dtos.AlbumDTO;

@RestController
@RequestMapping(value = "/albuns")
public class AlbumRC {

	@Autowired
	private AlbumServices theAlbumServices;

	@PreAuthorize("hasAnyRole('PROPRIETARIO')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid AlbumDTO objNewDTO,
			@RequestPart(name = "file", required = true) MultipartFile theMultipartFile) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(theAlbumServices.insert(new AlbumVHWeb().create(objNewDTO), theMultipartFile).getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<AlbumDTO> find(@PathVariable Long id) {
		AlbumDTO obj = new AlbumDTO(theAlbumServices.find(id));
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<AlbumDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "ano") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Album> list = theAlbumServices.findAllPage(page, linesPerPage, orderBy, direction);
		Page<AlbumDTO> listDTO = list.map(obj -> new AlbumDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}

	@PreAuthorize("hasAnyRole('PROPRIETARIO')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid AlbumDTO objDTO, @PathVariable Long id,
			@RequestPart(name = "file", required = false) MultipartFile theMultipartFile) {
		objDTO.setId(id);
		Album theAlbum = theAlbumServices.find(id);
		new AlbumVHWeb().update(theAlbum, objDTO);
		theAlbum = theAlbumServices.update(theAlbum, theMultipartFile);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('PROPRIETARIO')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		find(id);
		theAlbumServices.delete(id);
		return ResponseEntity.noContent().build();
	}
}
