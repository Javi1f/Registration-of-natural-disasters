package co.edu.unbosque.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.model.Tsunami;

public interface TsunamiRepository extends JpaRepository<Tsunami, Long> {

	public Optional<Tsunami> findByUuid(String name);

	public void deleteByUuid(String name);

}