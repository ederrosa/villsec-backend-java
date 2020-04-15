package br.com.villsec.controller.helpers;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.villsec.model.entities.domain.Proprietario;
import br.com.villsec.model.entities.domain.AutenticacaoSS;
import br.com.villsec.model.entities.domain.Email;
import br.com.villsec.model.entities.domain.Endereco;
import br.com.villsec.model.entities.domain.Telefone;
import br.com.villsec.model.entities.enums.Perfil;
import br.com.villsec.model.entities.enums.TipoTelefone;
import br.com.villsec.model.services.dtos.ProprietarioDTO;
import br.com.villsec.model.services.utilities.DateUtilities;

public class ProprietarioVHWeb {

	public Proprietario create(ProprietarioDTO objDTO) {

		Set<Perfil> thePerfis = new LinkedHashSet<>();

		thePerfis.add(Perfil.PROPRIETARIO);

		AutenticacaoSS theAutenticacaoSS = new AutenticacaoSS(null, objDTO.getEmail(), null,
				new BCryptPasswordEncoder().encode(objDTO.getSenha()), thePerfis, null, null, Perfil.PROPRIETARIO);

		Email theEmail = new Email(null, objDTO.getEmail());

		Endereco theEndereco = new Endereco(null, objDTO.getLogradouro(), 
				 objDTO.getCep(), objDTO.getBairro(), objDTO.getCidade(), objDTO.getEstado(),
				objDTO.getPais());

		Proprietario theProprietario = new Proprietario(null, objDTO.getNome(), objDTO.getGenero(), true, theAutenticacaoSS, theEmail,
				theEndereco, (objDTO.getDataNascimento() == null || objDTO.getDataNascimento().isEmpty()) ? null
						: DateUtilities.dateFormat(objDTO.getDataNascimento()), objDTO.getSobreMim());

		theProprietario.setTheTelefones(new LinkedHashSet<Telefone>());
		if (objDTO.getTipoTelefone1() != null) {
			theProprietario.getTheTelefones().add(new Telefone(null, objDTO.getNumeroTelefone1(),
					(objDTO.getTipoTelefone1() == null) ? null : TipoTelefone.toEnum(Integer.parseInt(objDTO.getTipoTelefone1()))));
		}
		if (objDTO.getTipoTelefone2() != null) {
			theProprietario.getTheTelefones().add(new Telefone(null, objDTO.getNumeroTelefone2(),
					(objDTO.getTipoTelefone2() == null) ? null : TipoTelefone.toEnum(Integer.parseInt(objDTO.getTipoTelefone2()))));
		}

		return theProprietario;
	}

	public void update(Proprietario theProprietario, ProprietarioDTO objDTO) {

		theProprietario.getTheAutenticacaoSS()
				.setSenha((objDTO.getSenha() == null || objDTO.getSenha().isEmpty())
						? theProprietario.getTheAutenticacaoSS().getSenha()
						: new BCryptPasswordEncoder().encode(objDTO.getSenha()));
		theProprietario.getTheAutenticacaoSS()
				.setLogin((objDTO.getEmail() == null || objDTO.getEmail().isEmpty()) ? theProprietario.getTheEmail().getEmail()
						: objDTO.getEmail());
		theProprietario.getTheEmail()
				.setEmail((objDTO.getEmail() == null || objDTO.getEmail().isEmpty()) ? theProprietario.getTheEmail().getEmail()
						: objDTO.getEmail());
		theProprietario.getTheEndereco()
				.setLogradouro((objDTO.getLogradouro() == null || objDTO.getLogradouro().isEmpty())
						? theProprietario.getTheEndereco().getLogradouro()
						: objDTO.getLogradouro());
		theProprietario.getTheEndereco()
				.setCep((objDTO.getCep() == null || objDTO.getCep().isEmpty()) ? theProprietario.getTheEndereco().getCep()
						: objDTO.getCep());
		theProprietario.getTheEndereco()
				.setBairro((objDTO.getBairro() == null || objDTO.getBairro().isEmpty())
						? theProprietario.getTheEndereco().getBairro()
						: objDTO.getBairro());
		theProprietario.getTheEndereco()
				.setCidade((objDTO.getCidade() == null || objDTO.getCidade().isEmpty())
						? theProprietario.getTheEndereco().getCidade()
						: objDTO.getCidade());
		theProprietario.getTheEndereco()
				.setEstado((objDTO.getEstado() == null || objDTO.getEstado().isEmpty())
						? theProprietario.getTheEndereco().getEstado()
						: objDTO.getEstado());
		theProprietario.getTheEndereco()
				.setPais((objDTO.getPais() == null || objDTO.getPais().isEmpty()) ? theProprietario.getTheEndereco().getPais()
						: objDTO.getPais());
		List<Telefone> theTelefones = new ArrayList<>();
		for (Telefone x : theProprietario.getTheTelefones()) {
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
		theProprietario.setTheTelefones(new LinkedHashSet<>());
		theProprietario.getTheTelefones().addAll(theTelefones);
		theProprietario.setNome(
				(objDTO.getNome() == null || objDTO.getNome().isEmpty()) ? theProprietario.getNome() : objDTO.getNome());
		theProprietario.setGenero((objDTO.getGenero() == null || objDTO.getGenero().isEmpty()) ? theProprietario.getGenero()
				: objDTO.getGenero());
		theProprietario.setStatusPessoa(
				(objDTO.getStatusPessoa() == null) ? theProprietario.getStatusPessoa() : objDTO.getStatusPessoa());
		theProprietario.setDataNascimento((objDTO.getDataNascimento() == null) ? theProprietario.getDataNascimento()
				: DateUtilities.dateFormat(objDTO.getDataNascimento()));
		theProprietario.setSobreMin(
				(objDTO.getSobreMim() == null || objDTO.getSobreMim().isEmpty()) ? theProprietario.getSobreMim() : objDTO.getSobreMim());
	
		}
}
