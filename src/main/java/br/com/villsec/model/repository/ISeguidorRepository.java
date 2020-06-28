package br.com.villsec.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.villsec.model.entities.domain.AutenticacaoSS;
import br.com.villsec.model.entities.domain.Seguidor;

@Repository
public interface ISeguidorRepository extends JpaRepository<Seguidor, Long> {

	@Transactional(readOnly = true)
	Seguidor findByTheEmailEmail(String theEmail);

	@Transactional(readOnly = true)
	Seguidor findByTheAutenticacaoSS(AutenticacaoSS theAutenticacaoSS);

	@Transactional(readOnly = true)
	Page<Seguidor> findAllByTheAutenticacaoSS(AutenticacaoSS theAutenticacaoSS, Pageable pageRequest);
}
