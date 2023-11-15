package co.edu.unbosque.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.model.VolcanicEruption;

public interface VolcanicEruptionRepository extends JpaRepository<VolcanicEruption, Long>{

	public Optional<VolcanicEruption> findByUuid(String name);
	
	public void deleteByUuid(String name);
}
