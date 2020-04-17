package br.com.villsec.controller.helpers;

import br.com.villsec.model.entities.domain.Evento;
import br.com.villsec.model.entities.domain.Endereco;
import br.com.villsec.model.entities.enums.TipoEvento;
import br.com.villsec.model.services.dtos.EventoDTO;
import br.com.villsec.model.services.utilities.DateUtilities;

public class EventoVHWeb {

	public Evento create(EventoDTO objDTO) {

		Evento theEvento = new Evento(null, objDTO.getClassificacao(), objDTO.getDuracao(),
				(objDTO.getData() == null || objDTO.getData().isEmpty()) ? null
						: DateUtilities.dateFormat(objDTO.getData()),
				objDTO.getDescricao(), null, objDTO.getNome(),
				(objDTO.getTipoEvento() == null) ? null : TipoEvento.toEnum(Integer.parseInt(objDTO.getTipoEvento())),
				new Endereco(null, objDTO.getLogradouro(), objDTO.getCep(), objDTO.getBairro(), objDTO.getCidade(),
						objDTO.getEstado(), objDTO.getPais()));
		return theEvento;
	}

	public void update(Evento theEvento, EventoDTO objDTO) {

		theEvento.setClassificacao(objDTO.getClassificacao());
		theEvento.setData((objDTO.getData() == null || objDTO.getData().isEmpty()) ? null
				: DateUtilities.dateFormat(objDTO.getData()));
		theEvento.setDescricao(objDTO.getDescricao());
		theEvento.setDuracao(objDTO.getDuracao());
		theEvento.setNome(objDTO.getNome());
		theEvento.setTheEndereco(new Endereco(theEvento.getTheEndereco().getId(), objDTO.getLogradouro(),
				objDTO.getCep(), objDTO.getBairro(), objDTO.getCidade(), objDTO.getEstado(), objDTO.getPais()));
		theEvento.setTipoEvento(
				(objDTO.getTipoEvento() == null) ? null : TipoEvento.toEnum(Integer.parseInt(objDTO.getTipoEvento())));
	}
}
