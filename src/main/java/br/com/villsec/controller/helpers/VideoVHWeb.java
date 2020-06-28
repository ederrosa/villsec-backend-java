package br.com.villsec.controller.helpers;

import br.com.villsec.model.entities.domain.Video;
import br.com.villsec.model.services.dtos.VideoDTO;

public class VideoVHWeb {

	public Video create(VideoDTO objDTO) {
		
		Video theVideo = new Video(
				null,
				objDTO.getDescricao(),
				objDTO.getEmbed(),
				null,
				null,
				objDTO.getTitulo());
		return theVideo;
	}

	public void update(Video theVideo, VideoDTO objDTO) {

		theVideo.setDescricao(objDTO.getDescricao());
		theVideo.setTitulo(objDTO.getTitulo());
		theVideo.setEmbed(objDTO.getEmbed());			
	}
}
