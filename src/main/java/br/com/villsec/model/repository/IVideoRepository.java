package br.com.villsec.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.villsec.model.entities.domain.Video;

@Repository
public interface IVideoRepository extends JpaRepository<Video, Long> {

}
