package br.com.villsec.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.villsec.model.entities.domain.Evento;

@Repository
public interface IEventoRepository extends JpaRepository<Evento, Long>{
	
	@Transactional(readOnly = true)
	Evento findByNome(String nome);

}
