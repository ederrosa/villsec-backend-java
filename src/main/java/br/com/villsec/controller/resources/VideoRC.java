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

import br.com.villsec.controller.helpers.VideoVHWeb;
import br.com.villsec.model.entities.domain.Video;
import br.com.villsec.model.services.VideoServices;
import br.com.villsec.model.services.dtos.VideoDTO;

@RestController
@RequestMapping(value = "/videos")
public class VideoRC {

	@Autowired
	private VideoServices theElementoServices;

	@PreAuthorize("hasAnyRole('PROPRIETARIO')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid VideoDTO objNewDTO,
			@RequestPart(name = "file", required = false) MultipartFile theMultipartFile,
			@RequestParam(value = "galeriaID") String theGaleria) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(theElementoServices
				.insert(new VideoVHWeb().create(objNewDTO), theMultipartFile, Long.parseLong(theGaleria)).getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<VideoDTO> find(@PathVariable Long id) {
		VideoDTO obj = new VideoDTO(theElementoServices.find(id));
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<VideoDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "titulo") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "theGaleria") Long theGaleria) {
		Page<Video> list = theElementoServices.findAllPage(page, linesPerPage, orderBy, direction, theGaleria);
		Page<VideoDTO> listDTO = list.map(obj -> new VideoDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}

	@PreAuthorize("hasAnyRole('PROPRIETARIO')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid VideoDTO objDTO, @PathVariable Long id,
			@RequestPart(name = "file", required = false) MultipartFile theMultipartFile) {
		objDTO.setId(id);
		Video theElemento = theElementoServices.find(id);
		new VideoVHWeb().update(theElemento, objDTO);
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
