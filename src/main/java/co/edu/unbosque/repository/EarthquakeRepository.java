package co.edu.unbosque.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.model.Earthquake;

public interface EarthquakeRepository extends JpaRepository<Earthquake, Long>{

	public Optional<Earthquake> findByUuid(String uuid);
	public Earthquake getByUuid(String uuid);


}
