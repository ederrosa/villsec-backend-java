package br.com.villsec.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.villsec.model.entities.domain.Elemento;

@Repository
public interface IElementoRepository extends JpaRepository<Elemento, Long>{

	@Transactional(readOnly = true)
	Elemento findByTitulo(String titulo);
	
}
