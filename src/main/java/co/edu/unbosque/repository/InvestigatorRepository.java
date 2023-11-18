package co.edu.unbosque.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.model.Investigator;

public interface InvestigatorRepository extends JpaRepository<Investigator, Long> {

	public Optional<Investigator> findByUuid(String uuid);
	public Investigator getByUuid(String uuid);

}
