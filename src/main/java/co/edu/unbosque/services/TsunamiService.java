package co.edu.unbosque.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.model.Tsunami;
import co.edu.unbosque.repository.TsunamiRepository;

@Service
public class TsunamiService implements CRUDOperations<Tsunami> {

	@Autowired
	private TsunamiRepository tsuRepo;

	@Autowired
	private UuidVerifier verifier;

	public TsunamiService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int create(Tsunami data) {
		if(verifier.uuidAlreadyTaked(data.getUuid()))return 1;
		tsuRepo.save(data);
		return 0;

	}

	@Override
	public List<Tsunami> getAll() {
		return tsuRepo.findAll();
	}

	@Override
	public int deleteById(Long id) {
		Optional<Tsunami> found = tsuRepo.findById(id);

		if (found.isPresent()) {
			tsuRepo.delete(found.get());
			return 0;
		} else {
			return 1;
		}
	}
	
	public int deleteByUuid(String uuid) {
		Optional<Tsunami> found = tsuRepo.findByUuid(uuid);

		if (found.isPresent()) {
			tsuRepo.delete(found.get());
			return 0;
		} 
		return 1;
	}

	@Override
	public int updateById(Long id, Tsunami newData) {

		Optional<Tsunami> found = tsuRepo.findById(id);
		boolean taked = false;
		if (found.isPresent())
			taked = verifier.uuidAlreadyTaked(newData.getUuid(),found.get().getUuid());
		else
			taked = verifier.uuidAlreadyTaked(newData.getUuid());
		if (found.isPresent() && !taked) {
			Tsunami temp = found.get();
			temp.setDisasterName(newData.getDisasterName());
			temp.setUuid(newData.getUuid());
			temp.setDescription(newData.getDescription());
			temp.setContinent(newData.getContinent());
			temp.setCountry(newData.getCountry());
			temp.setInvestigators(newData.getInvestigators());
			temp.setImage(newData.getImage());
			temp.setWaveHeight(newData.getWaveHeight());
			tsuRepo.save(temp);
			return 0;
		} else if (found.isPresent() && taked) {
			return 1;
		} else if (!found.isPresent()) {
			return 2;
		}
		return 3;

	}

	public int updateByUuid(String uuid, Tsunami newData) {
		Optional<Tsunami> found = tsuRepo.findByUuid(uuid);
		boolean taked = verifier.uuidAlreadyTaked(newData.getUuid(),uuid);
		if (found.isPresent() && !taked) {
			Tsunami temp = found.get();
			temp.setDisasterName(newData.getDisasterName());
			temp.setUuid(newData.getUuid());
			temp.setDescription(newData.getDescription());
			temp.setContinent(newData.getContinent());
			temp.setCountry(newData.getCountry());
			temp.setInvestigators(newData.getInvestigators());
			temp.setImage(newData.getImage());
			temp.setWaveHeight(newData.getWaveHeight());
			tsuRepo.save(temp);
			return 0;
		} else if (found.isPresent() && taked) {
			return 1;
		} else if (!found.isPresent()) {
			return 2;
		}
		return 3;
	}

	@Override
	public long count() {
		return tsuRepo.count();
	}

	@Override
	public boolean exists(Long id) {
		return tsuRepo.existsById(id) ? true : false;
	}

	public boolean exists(String uuid) {
		Optional<Tsunami> found=tsuRepo.findByUuid(uuid);
		return found.isPresent();
	}

}
