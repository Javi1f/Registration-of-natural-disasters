package co.edu.unbosque.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.model.Hurricane;
import co.edu.unbosque.repository.HurricaneRepository;

@Service
public class HurricaneService implements CRUDOperations<Hurricane> {

	@Autowired
	private HurricaneRepository hurRepo;
	@Autowired
	private UuidVerifier verifier;
	

	public HurricaneService() {
	}

	@Override
	public int create(Hurricane data) {
		if(verifier.uuidAlreadyTaked(data.getUuid()))return 1;
		hurRepo.save(data);
		return 0;

	}

	@Override
	public List<Hurricane> getAll() {
		return hurRepo.findAll();
	}

	@Override
	public int deleteById(Long id) {
		Optional<Hurricane> found = hurRepo.findById(id);

		if (found.isPresent()) {
			hurRepo.delete(found.get());
			return 0;
		}
		return 1;
	}
	
	public int deleteByUuid(String uuid) {
		Optional<Hurricane> found = hurRepo.findByUuid(uuid);

		if (found.isPresent()) {
			hurRepo.delete(found.get());
			return 0;
		}
		return 1;
	}

	@Override
	public int updateById(Long id, Hurricane newData) {
		Optional<Hurricane> found = hurRepo.findById(id);
		boolean taked = false;
		if(found.isPresent())taked = verifier.uuidAlreadyTaked(newData.getUuid(),found.get().getUuid());
		else taked = verifier.uuidAlreadyTaked(newData.getUuid());
		if (found.isPresent() && !taked) {
			Hurricane temp = found.get();
			temp.setDisasterName(newData.getDisasterName());
			temp.setUuid(newData.getUuid());
			temp.setDescription(newData.getDescription());
			temp.setContinent(newData.getContinent());
			temp.setCountry(newData.getCountry());
			temp.setInvestigators(newData.getInvestigators());
			temp.setImage(newData.getImage());
			temp.setMagnitude(newData.getMagnitude());;
			hurRepo.save(temp);
			return 0;
		} else if (found.isPresent() && taked) {
			return 1;
		} else if (!found.isPresent()) {
			return 2;
		} 
		return 3;
	}
	
	public int updateByUuid(String uuid, Hurricane newData) {
		Optional<Hurricane> found = hurRepo.findByUuid(uuid);
		boolean taked = verifier.uuidAlreadyTaked(newData.getUuid(),uuid);
		if (found.isPresent() && !taked) {
			Hurricane temp = found.get();
			temp.setDisasterName(newData.getDisasterName());
			temp.setUuid(newData.getUuid());
			temp.setDescription(newData.getDescription());
			temp.setContinent(newData.getContinent());
			temp.setCountry(newData.getCountry());
			temp.setInvestigators(newData.getInvestigators());
			temp.setImage(newData.getImage());
			temp.setMagnitude(newData.getMagnitude());
			hurRepo.save(temp);
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
		return hurRepo.count();
	}

	@Override
	public boolean exists(Long id) {
		return hurRepo.existsById(id) ? true : false;
	}

	public boolean exists(String uuid) {
		Optional<Hurricane> found=hurRepo.findByUuid(uuid);
		return found.isPresent();
	}

}
