package co.edu.unbosque.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.model.ExtremeHeat;

public interface ExtremeHeatRepository extends JpaRepository<ExtremeHeat, Long>{

	public Optional<ExtremeHeat> findByUuid(String uuid);
	public ExtremeHeat getByUuid(String uuid);
}