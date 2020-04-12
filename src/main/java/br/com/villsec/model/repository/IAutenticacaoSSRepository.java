package br.com.villsec.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.villsec.model.entities.domain.AutenticacaoSS;

@Repository
public interface IAutenticacaoSSRepository extends JpaRepository<AutenticacaoSS, Long> {

	@Transactional(readOnly=true)
	AutenticacaoSS findByLogin(String login);
	
	@Transactional(readOnly=true)
	AutenticacaoSS findByMatricula(String matricula);
}
