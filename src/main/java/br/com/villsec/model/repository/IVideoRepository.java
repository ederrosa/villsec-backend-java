package br.com.villsec.model.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.villsec.model.entities.domain.Galeria;
import br.com.villsec.model.entities.domain.Video;

@Repository
public interface IVideoRepository extends JpaRepository<Video, Long> {

	@Transactional(readOnly = true)
	List<Video> findAllByTheGaleria(Galeria theGaleria);

	@Transactional(readOnly = true)
	Page<Video> findAllByTheGaleria(Galeria theGaleria, Pageable pageRequest);
}
