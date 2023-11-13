package co.edu.unbosque.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.model.Country;

public interface countryRepository extends JpaRepository<Country, Long> {

	public Optional<Country> findByCountName(String name);

	public void deleteByCountName(String name);

}
