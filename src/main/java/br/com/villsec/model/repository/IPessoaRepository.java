package br.com.villsec.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.villsec.model.entities.domain.AutenticacaoSS;
import br.com.villsec.model.entities.domain.Pessoa;

@Repository
public interface IPessoaRepository extends JpaRepository<Pessoa, Long> {

	@Transactional(readOnly = true)
	Pessoa findByTheEmailEmail(String theEmail);

	@Transactional(readOnly = true)
	Pessoa findByTheAutenticacaoSS(AutenticacaoSS theAutenticacaoSS);

}
