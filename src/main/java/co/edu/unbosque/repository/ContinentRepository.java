package co.edu.unbosque.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.model.Continent;

public interface ContinentRepository extends JpaRepository<Continent, Long> {

	public Optional<Continent> findByNameCont(String name);

	public void deleteByNameCont(String name);

}
