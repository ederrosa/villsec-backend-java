package br.com.villsec.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.villsec.model.entities.domain.AutenticacaoSS;
import br.com.villsec.model.entities.domain.Proprietario;

@Repository
public interface IProprietario extends JpaRepository<Proprietario, Long>{

	@Transactional(readOnly = true)
	Proprietario findByTheEmailEmail(String theEmail);

	@Transactional(readOnly = true)
	Proprietario findByTheAutenticacaoSS(AutenticacaoSS theAutenticacaoSS);
}
