package co.edu.unbosque.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.model.Hurricane;

public interface HurricaneRepository extends JpaRepository<Hurricane, Long> {

	public Optional<Hurricane> findByUuid(String uuid);
	public Hurricane getByUuid(String uuid);
}