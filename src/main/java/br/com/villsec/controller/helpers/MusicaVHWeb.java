package br.com.villsec.controller.helpers;

import br.com.villsec.model.entities.domain.Musica;
import br.com.villsec.model.services.dtos.MusicaDTO;

public class MusicaVHWeb {

	public Musica create(MusicaDTO objDTO) {

		return new Musica(null, objDTO.getAutor(), Integer.parseInt(objDTO.getBpm()), objDTO.getCoautor(),
				objDTO.getCopyright(), objDTO.getDuracao(), Integer.parseInt(objDTO.getFaixa()), objDTO.getIdioma(),
				objDTO.getNome(), null, null);
	}

	public void update(Musica theMusica, MusicaDTO objDTO) {

		theMusica.setAutor(objDTO.getAutor());
		theMusica.setBpm(Integer.parseInt(objDTO.getBpm()));
		theMusica.setCoautor(objDTO.getCoautor());
		theMusica.setCopyright(objDTO.getCopyright());
		theMusica.setDuracao(objDTO.getDuracao());
		theMusica.setIdioma(objDTO.getIdioma());
		theMusica.setFaixa(Integer.parseInt(objDTO.getFaixa()));
		theMusica.setNome(objDTO.getNome());
	}
}
