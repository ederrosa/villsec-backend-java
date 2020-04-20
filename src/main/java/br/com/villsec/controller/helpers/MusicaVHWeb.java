package br.com.villsec.controller.helpers;

import br.com.villsec.model.entities.domain.Musica;
import br.com.villsec.model.services.dtos.MusicaDTO;

public class MusicaVHWeb {

	public Musica create(MusicaDTO objDTO) {

		Musica theMusica = new Musica(
				null,
				objDTO.getNome(),
				Integer.parseInt(objDTO.getBpm()),
				objDTO.getAutor(),
				objDTO.getCoautor(),
				Integer.parseInt(objDTO.getDuracao()),
				null,
				objDTO.getCopyright(),
				objDTO.getIdioma());
		return theMusica;
	}
	
	public void update(Musica theMusica, MusicaDTO objDTO) {
		
			theMusica.setAutor(objDTO.getAutor());
			theMusica.setBpm(Integer.parseInt(objDTO.getBpm()));
			theMusica.setCoautor(objDTO.getCoautor());
			theMusica.setCopyright(objDTO.getCopyright());
			theMusica.setDuracao(Integer.parseInt(objDTO.getDuracao()));
			theMusica.setIdioma(objDTO.getIdioma());
			theMusica.setNome(objDTO.getNome());
	}
}
