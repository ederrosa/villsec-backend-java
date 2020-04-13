package br.com.villsec.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.villsec.model.entities.domain.Musica;

@Repository
public interface IMusica  extends JpaRepository<Musica, Long>{

	@Transactional(readOnly = true)
	Musica findByNome(String nome);
	
	@Transactional(readOnly = true)
	Musica findByAutor(String autor);
	
	@Transactional(readOnly = true)
	Musica findByCoautor(String coautor);
}
