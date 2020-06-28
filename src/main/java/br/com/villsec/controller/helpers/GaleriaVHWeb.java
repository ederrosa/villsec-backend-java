package br.com.villsec.controller.helpers;

import br.com.villsec.model.entities.domain.Galeria;
import br.com.villsec.model.services.dtos.GaleriaDTO;

public class GaleriaVHWeb {

	public Galeria create(GaleriaDTO objDTO) {

		return new Galeria(null, objDTO.getDescricao(), objDTO.getStatus(), null, null, objDTO.getTitulo());
	}

	public void update(Galeria theGaleria, GaleriaDTO objDTO) {

		theGaleria.setDescricao(objDTO.getDescricao());
		theGaleria.setStatus(objDTO.getStatus());
		theGaleria.setTitulo(objDTO.getTitulo());
	}
}
