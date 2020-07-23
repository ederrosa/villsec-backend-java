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

import br.com.villsec.controller.helpers.MusicaVHWeb;
import br.com.villsec.model.entities.domain.Musica;
import br.com.villsec.model.services.MusicaServices;
import br.com.villsec.model.services.dtos.MusicaDTO;

@RestController
@RequestMapping(value = "/musicas")
public class MusicaRC {

	@Autowired
	private MusicaServices theMusicaServices;

	@PreAuthorize("hasAnyRole('PROPRIETARIO')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid MusicaDTO objNewDTO,
			@RequestPart(name = "file", required = true) MultipartFile theMultipartFile,
			@RequestParam(value = "albumID") String theAlbum) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(theMusicaServices
				.insert(new MusicaVHWeb().create(objNewDTO), theMultipartFile, Long.parseLong(theAlbum)).getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<MusicaDTO> find(@PathVariable Long id) {
		MusicaDTO obj = new MusicaDTO(theMusicaServices.find(id));
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<MusicaDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "index") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "theAlbum") Long theAlbum) {
		Page<Musica> list = theMusicaServices.findAllPage(page, linesPerPage, orderBy, direction, theAlbum);
		Page<MusicaDTO> listDTO = list.map(obj -> new MusicaDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}

	@PreAuthorize("hasAnyRole('PROPRIETARIO')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid MusicaDTO objDTO, @PathVariable Long id,
			@RequestPart(name = "file", required = false) MultipartFile theMultipartFile) {
		objDTO.setId(id);
		Musica theMusica = theMusicaServices.find(id);
		new MusicaVHWeb().update(theMusica, objDTO);
		theMusica = theMusicaServices.update(theMusica, theMultipartFile);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('PROPRIETARIO')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		find(id);
		theMusicaServices.delete(id);
		return ResponseEntity.noContent().build();
	}
}
