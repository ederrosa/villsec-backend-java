package br.com.villsec.controller.helpers;

import br.com.villsec.model.entities.domain.Elemento;
import br.com.villsec.model.entities.enums.TipoElemento;
import br.com.villsec.model.services.dtos.ElementoDTO;

public class ElementoVHWeb {

	public Elemento create(ElementoDTO objDTO) {
		
		Elemento theElemento = new Elemento(
				null,
				objDTO.getDescricao(),
				null,
				(objDTO.getTipoElemento() == null) ? null : TipoElemento.toEnum(Integer.parseInt(objDTO.getTipoElemento())),
				objDTO.getTitulo(),
				objDTO.getStatus());
		return theElemento;
	}

	public void update(Elemento theElemento, ElementoDTO objDTO) {

		theElemento.setDescricao(objDTO.getDescricao());
		theElemento.setStatus(objDTO.getStatus());
		theElemento.setTipoElemento((objDTO.getTipoElemento() == null) ? null : TipoElemento.toEnum(Integer.parseInt(objDTO.getTipoElemento())));
		theElemento.setTitulo(objDTO.getTitulo());		
	}
}
