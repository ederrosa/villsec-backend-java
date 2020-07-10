package br.com.villsec.controller.helpers;

import br.com.villsec.model.entities.domain.Evento;

import java.net.URI;

import br.com.villsec.model.entities.domain.Endereco;
import br.com.villsec.model.entities.enums.TipoEvento;
import br.com.villsec.model.services.dtos.EventoDTO;
import br.com.villsec.model.services.utilities.DateUtilities;

public class EventoVHWeb {

	public Evento create(EventoDTO objDTO) {

		Evento theEvento = new Evento(null, objDTO.getClassificacao(),
				(objDTO.getDiaInicio() == null || objDTO.getDiaInicio().isEmpty()) ? null
						: DateUtilities.dateFormat(objDTO.getDiaInicio()),
				(objDTO.getDiaTermino() == null || objDTO.getDiaTermino().isEmpty()) ? null
						: DateUtilities.dateFormat(objDTO.getDiaTermino()),
				objDTO.getDescricao(), null, objDTO.getHoraInicio(), objDTO.getHoraTermino(),
				(objDTO.getIngressoUrl() == null || objDTO.getIngressoUrl().isEmpty()) ? null
						: URI.create(objDTO.getIngressoUrl()),
				objDTO.getNome(),
				(objDTO.getTipoEvento() == null) ? null : TipoEvento.toEnum(Integer.parseInt(objDTO.getTipoEvento())),
				new Endereco(null, objDTO.getBairro(), objDTO.getCep(), objDTO.getCidade(), objDTO.getEstado(),
						objDTO.getLogradouro(), objDTO.getPais()));
		if(objDTO.getGoogleMapsUrl() != null && !objDTO.getGoogleMapsUrl().isEmpty()) {
			theEvento.getTheEndereco().setGoogleMapsUrl(URI.create(objDTO.getGoogleMapsUrl()));
		}
		return theEvento;
	}

	public void update(Evento theEvento, EventoDTO objDTO) {

		theEvento.setClassificacao(objDTO.getClassificacao());
		theEvento.setDiaInicio((objDTO.getDiaInicio() == null || objDTO.getDiaInicio().isEmpty()) ? null
				: DateUtilities.dateFormat(objDTO.getDiaInicio()));
		theEvento.setDiaTermino((objDTO.getDiaTermino() == null || objDTO.getDiaTermino().isEmpty()) ? null
				: DateUtilities.dateFormat(objDTO.getDiaTermino()));
		theEvento.setDescricao(objDTO.getDescricao());
		theEvento.setHoraInicio(objDTO.getHoraInicio());
		theEvento.setHoraTermino(objDTO.getHoraTermino());
		theEvento.setIngressoUrl((objDTO.getIngressoUrl() == null || objDTO.getIngressoUrl().isEmpty()) ? null
				: URI.create(objDTO.getIngressoUrl()));
		theEvento.setNome(objDTO.getNome());
		theEvento.setTheEndereco(new Endereco(theEvento.getTheEndereco().getId(), objDTO.getBairro(), objDTO.getCep(),
				objDTO.getCidade(), objDTO.getEstado(), objDTO.getLogradouro(), objDTO.getPais()));
		theEvento.setTipoEvento(
				(objDTO.getTipoEvento() == null) ? null : TipoEvento.toEnum(Integer.parseInt(objDTO.getTipoEvento())));
		if(objDTO.getGoogleMapsUrl() != null && !objDTO.getGoogleMapsUrl().isEmpty()) {
			theEvento.getTheEndereco().setGoogleMapsUrl(URI.create(objDTO.getGoogleMapsUrl()));
		}
	}
}
