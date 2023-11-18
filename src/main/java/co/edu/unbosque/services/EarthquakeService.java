package co.edu.unbosque.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.model.Earthquake;
import co.edu.unbosque.repository.EarthquakeRepository;

@Service
public class EarthquakeService implements CRUDOperations<Earthquake> {

	@Autowired
	private EarthquakeRepository earthRepo;
	@Autowired
	private UuidVerifier verifier;

	public EarthquakeService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int create(Earthquake data) {
		if (verifier.uuidAlreadyTaked(data.getUuid()))return 1;
		earthRepo.save(data);
		return 0;

	}

	@Override
	public List<Earthquake> getAll() {
		return earthRepo.findAll();
	}

	@Override
	public int deleteById(Long id) {
		Optional<Earthquake> found = earthRepo.findById(id);

		if (found.isPresent()) {
			earthRepo.delete(found.get());
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int updateById(Long id, Earthquake newData) {

		Optional<Earthquake> found = earthRepo.findById(id);
		boolean taked = false;
		if(found.isPresent())taked = verifier.uuidAlreadyTaked(newData.getUuid(),found.get().getUuid());
		else taked = verifier.uuidAlreadyTaked(newData.getUuid());
		if (found.isPresent() && !taked) {
			Earthquake temp = found.get();
			temp.setDisasterName(newData.getDisasterName());
			temp.setUuid(newData.getUuid());
			temp.setDescription(newData.getDescription());
			temp.setContinent(newData.getContinent());
			temp.setCountry(newData.getCountry());
			temp.setInvestigators(newData.getInvestigators());
			temp.setImage(newData.getImage());
			temp.setMagnitude(newData.getMagnitude());
			earthRepo.save(temp);
			return 0;
		} else if (found.isPresent() && taked) {
			return 1;
		} else if (!found.isPresent()) {
			return 2;
		} 
		return 3;

	}

	public int updateByUuid(String uuid, Earthquake newData) {

		Optional<Earthquake> found = earthRepo.findByUuid(uuid);
		boolean taked = verifier.uuidAlreadyTaked(newData.getUuid(),uuid);
		if (found.isPresent() && !taked) {
			Earthquake temp = found.get();
			temp.setDisasterName(newData.getDisasterName());
			temp.setUuid(newData.getUuid());
			temp.setDescription(newData.getDescription());
			temp.setContinent(newData.getContinent());
			temp.setCountry(newData.getCountry());
			temp.setInvestigators(newData.getInvestigators());
			temp.setImage(newData.getImage());
			temp.setMagnitude(newData.getMagnitude());
			earthRepo.save(temp);
			return 0;
		} else if (found.isPresent() && taked) {
			return 1;
		} else if (!found.isPresent()) {
			return 2;
		}
		return 3;
	}

	public int deleteByUuid(String uuid) {
		Optional<Earthquake> found = earthRepo.findByUuid(uuid);
		if (found.isPresent()) {
			earthRepo.delete(found.get());
			return 0;
		}
		return 1;
	}

	@Override
	public long count() {
		return earthRepo.count();
	}

	@Override
	public boolean exists(Long id) {
		return earthRepo.existsById(id);
	}

	public boolean exists(String uuid) {
		Optional<Earthquake> found=earthRepo.findByUuid(uuid);
		return found.isPresent();
	}

}
