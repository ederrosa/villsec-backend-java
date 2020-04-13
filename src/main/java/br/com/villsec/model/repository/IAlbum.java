package br.com.villsec.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.villsec.model.entities.domain.Album;

@Repository
public interface IAlbum extends JpaRepository<Album, Long>{

	@Transactional(readOnly = true)
	Album findByNome(String nome);
	
	@Transactional(readOnly = true)
	Album findByCodigo(String codigo);
	
	@Transactional(readOnly = true)
	Album findByGenero(String genero);
}
