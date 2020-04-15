package br.com.villsec.controller.helpers;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.villsec.model.entities.domain.Seguidor;
import br.com.villsec.model.entities.domain.AutenticacaoSS;
import br.com.villsec.model.entities.domain.Email;
import br.com.villsec.model.entities.domain.Endereco;
import br.com.villsec.model.entities.domain.Telefone;
import br.com.villsec.model.entities.enums.Perfil;
import br.com.villsec.model.entities.enums.TipoTelefone;
import br.com.villsec.model.services.dtos.SeguidorDTO;
import br.com.villsec.model.services.utilities.DateUtilities;

public class SeguidorVHWeb {

	public Seguidor create(SeguidorDTO objDTO) {

		Set<Perfil> thePerfis = new LinkedHashSet<>();

		thePerfis.add(Perfil.SEGUIDOR);

		AutenticacaoSS theAutenticacaoSS = new AutenticacaoSS(null, objDTO.getEmail(), null,
				new BCryptPasswordEncoder().encode(objDTO.getSenha()), thePerfis, null, null, Perfil.SEGUIDOR);

		Email theEmail = new Email(null, objDTO.getEmail());

		Endereco theEndereco = new Endereco(null, objDTO.getLogradouro(), 
				 objDTO.getCep(), objDTO.getBairro(), objDTO.getCidade(), objDTO.getEstado(),
				objDTO.getPais());

		Seguidor theSeguidor = new Seguidor(null, objDTO.getNome(), objDTO.getGenero(), true, theAutenticacaoSS, theEmail,
				theEndereco, (objDTO.getDataNascimento() == null || objDTO.getDataNascimento().isEmpty()) ? null
						: DateUtilities.dateFormat(objDTO.getDataNascimento()));

		theSeguidor.setTheTelefones(new LinkedHashSet<Telefone>());
		if (objDTO.getTipoTelefone1() != null) {
			theSeguidor.getTheTelefones().add(new Telefone(null, objDTO.getNumeroTelefone1(),
					(objDTO.getTipoTelefone1() == null) ? null : TipoTelefone.toEnum(Integer.parseInt(objDTO.getTipoTelefone1()))));
		}
		if (objDTO.getTipoTelefone2() != null) {
			theSeguidor.getTheTelefones().add(new Telefone(null, objDTO.getNumeroTelefone2(),
					(objDTO.getTipoTelefone2() == null) ? null : TipoTelefone.toEnum(Integer.parseInt(objDTO.getTipoTelefone2()))));
		}

		return theSeguidor;
	}

	public void update(Seguidor theSeguidor, SeguidorDTO objDTO) {

		theSeguidor.getTheAutenticacaoSS()
				.setSenha((objDTO.getSenha() == null || objDTO.getSenha().isEmpty())
						? theSeguidor.getTheAutenticacaoSS().getSenha()
						: new BCryptPasswordEncoder().encode(objDTO.getSenha()));
		theSeguidor.getTheAutenticacaoSS()
				.setLogin((objDTO.getEmail() == null || objDTO.getEmail().isEmpty()) ? theSeguidor.getTheEmail().getEmail()
						: objDTO.getEmail());
		theSeguidor.getTheEmail()
				.setEmail((objDTO.getEmail() == null || objDTO.getEmail().isEmpty()) ? theSeguidor.getTheEmail().getEmail()
						: objDTO.getEmail());
		theSeguidor.getTheEndereco()
				.setLogradouro((objDTO.getLogradouro() == null || objDTO.getLogradouro().isEmpty())
						? theSeguidor.getTheEndereco().getLogradouro()
						: objDTO.getLogradouro());
		theSeguidor.getTheEndereco()
				.setCep((objDTO.getCep() == null || objDTO.getCep().isEmpty()) ? theSeguidor.getTheEndereco().getCep()
						: objDTO.getCep());
		theSeguidor.getTheEndereco()
				.setBairro((objDTO.getBairro() == null || objDTO.getBairro().isEmpty())
						? theSeguidor.getTheEndereco().getBairro()
						: objDTO.getBairro());
		theSeguidor.getTheEndereco()
				.setCidade((objDTO.getCidade() == null || objDTO.getCidade().isEmpty())
						? theSeguidor.getTheEndereco().getCidade()
						: objDTO.getCidade());
		theSeguidor.getTheEndereco()
				.setEstado((objDTO.getEstado() == null || objDTO.getEstado().isEmpty())
						? theSeguidor.getTheEndereco().getEstado()
						: objDTO.getEstado());
		theSeguidor.getTheEndereco()
				.setPais((objDTO.getPais() == null || objDTO.getPais().isEmpty()) ? theSeguidor.getTheEndereco().getPais()
						: objDTO.getPais());
		List<Telefone> theTelefones = new ArrayList<>();
		for (Telefone x : theSeguidor.getTheTelefones()) {
			if (x != null) {
				theTelefones.add(x);
			}
		}
		if (objDTO.getNumeroTelefone1() != null) {
			if (theTelefones.get(0).getId() != null) {
				theTelefones.get(0).setNumeroTelefone(objDTO.getNumeroTelefone1());
				theTelefones.get(0).setTipoTelefone(
						(objDTO.getTipoTelefone1() == null) ? null : TipoTelefone.toEnum(Integer.parseInt(objDTO.getTipoTelefone1())));
			}
		}
		if (objDTO.getNumeroTelefone2() != null) {
			if (theTelefones.get(1).getId() != null) {
				theTelefones.get(1).setNumeroTelefone(objDTO.getNumeroTelefone2());
				theTelefones.get(1).setTipoTelefone(
						(objDTO.getTipoTelefone2() == null) ? null : TipoTelefone.toEnum(Integer.parseInt(objDTO.getTipoTelefone2())));
			}
		}
		theSeguidor.setTheTelefones(new LinkedHashSet<>());
		theSeguidor.getTheTelefones().addAll(theTelefones);
		theSeguidor.setNome(
				(objDTO.getNome() == null || objDTO.getNome().isEmpty()) ? theSeguidor.getNome() : objDTO.getNome());
		theSeguidor.setGenero((objDTO.getGenero() == null || objDTO.getGenero().isEmpty()) ? theSeguidor.getGenero()
				: objDTO.getGenero());
		theSeguidor.setStatusPessoa(
				(objDTO.getStatusPessoa() == null) ? theSeguidor.getStatusPessoa() : objDTO.getStatusPessoa());
		theSeguidor.setDataNascimento((objDTO.getDataNascimento() == null) ? theSeguidor.getDataNascimento()
				: DateUtilities.dateFormat(objDTO.getDataNascimento()));
		}
}
