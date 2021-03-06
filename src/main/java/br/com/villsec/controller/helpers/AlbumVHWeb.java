package br.com.villsec.controller.helpers;

import br.com.villsec.model.entities.domain.Album;
import br.com.villsec.model.services.dtos.AlbumDTO;

public class AlbumVHWeb {

	public Album create(AlbumDTO objDTO) {

		return new Album(null, objDTO.getAno(), null, null, objDTO.getDescricao(), objDTO.getGenero(), objDTO.getNome(),
				null);
	}

	public void update(Album theAlbum, AlbumDTO objDTO) {

		theAlbum.setAno(objDTO.getAno());
		theAlbum.setDescricao(objDTO.getDescricao());
		theAlbum.setGenero(objDTO.getGenero());
		theAlbum.setNome(objDTO.getNome());
	}
}
