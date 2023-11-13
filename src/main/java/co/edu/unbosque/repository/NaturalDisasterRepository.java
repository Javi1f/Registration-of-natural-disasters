package co.edu.unbosque.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.model.NaturalDisaster;

public interface NaturalDisasterRepository extends JpaRepository<NaturalDisaster, Long> {

	public Optional<NaturalDisaster> findByUuid(String name);

	public void deleteByUuid(String name);

}
