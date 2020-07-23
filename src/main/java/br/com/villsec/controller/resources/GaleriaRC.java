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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.villsec.controller.helpers.GaleriaVHWeb;
import br.com.villsec.model.entities.domain.Galeria;
import br.com.villsec.model.services.GaleriaServices;
import br.com.villsec.model.services.dtos.GaleriaDTO;

@RestController
@RequestMapping(value = "/galerias")
public class GaleriaRC {

	@Autowired
	private GaleriaServices theGaleriaServices;

	@PreAuthorize("hasAnyRole('PROPRIETARIO')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid GaleriaDTO objNewDTO) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(theGaleriaServices.insert(new GaleriaVHWeb().create(objNewDTO)).getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<GaleriaDTO> find(@PathVariable Long id) {
		GaleriaDTO obj = new GaleriaDTO(theGaleriaServices.find(id));
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<GaleriaDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "ano") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Galeria> list = theGaleriaServices.findAllPage(page, linesPerPage, orderBy, direction);
		Page<GaleriaDTO> listDTO = list.map(obj -> new GaleriaDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}

	@PreAuthorize("hasAnyRole('PROPRIETARIO')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid GaleriaDTO objDTO, @PathVariable Long id) {
		objDTO.setId(id);
		Galeria theGaleria = theGaleriaServices.find(id);
		new GaleriaVHWeb().update(theGaleria, objDTO);
		theGaleria = theGaleriaServices.update(theGaleria);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('PROPRIETARIO')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		find(id);
		theGaleriaServices.delete(id);
		return ResponseEntity.noContent().build();
	}
}
