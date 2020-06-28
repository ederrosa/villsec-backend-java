package br.com.villsec.controller.helpers;

import br.com.villsec.model.entities.domain.Imagem;
import br.com.villsec.model.services.dtos.ImagemDTO;

public class ImagemVHWeb {

	public Imagem create(ImagemDTO objDTO) {

		return new Imagem(null, objDTO.getDescricao(), null, null, objDTO.getTitulo());		
	}

	public void update(Imagem theImagem, ImagemDTO objDTO) {

		theImagem.setDescricao(objDTO.getDescricao());
		theImagem.setTitulo(objDTO.getTitulo());		
	}
}
