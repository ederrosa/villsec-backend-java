package br.com.villsec.model.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.villsec.model.entities.domain.Galeria;
import br.com.villsec.model.entities.domain.Imagem;

@Repository
public interface IImagemRepository extends JpaRepository<Imagem, Long> {

	List<Imagem> findAllByTheGaleria(Galeria theGaleria);

	@Transactional(readOnly = true)
	Page<Imagem> findAllByTheGaleria(Galeria theGaleria, Pageable pageRequest);
}
