package br.com.villsec.model.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.villsec.model.entities.domain.Album;
import br.com.villsec.model.entities.domain.Musica;

@Repository
public interface IMusicaRepository extends JpaRepository<Musica, Long> {

	@Transactional(readOnly = true)
	List<Musica> findAllByTheAlbum(Album theAlbum);

	@Transactional(readOnly = true)
	Page<Musica> findAllByTheAlbum(Album theAlbum, Pageable pageRequest);
}
