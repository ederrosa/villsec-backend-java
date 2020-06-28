package br.com.villsec.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.villsec.model.entities.domain.Album;

@Repository
public interface IAlbumRepository extends JpaRepository<Album, Long> {

	@Transactional(readOnly = true)
	Album findByCodigo(String codigo);
}
