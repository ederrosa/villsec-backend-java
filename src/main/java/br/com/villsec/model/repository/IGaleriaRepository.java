package br.com.villsec.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.villsec.model.entities.domain.Galeria;

@Repository
public interface IGaleriaRepository extends JpaRepository<Galeria, Long> {

}
