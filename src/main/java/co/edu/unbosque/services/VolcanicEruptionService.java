package co.edu.unbosque.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.model.VolcanicEruption;
import co.edu.unbosque.repository.VolcanicEruptionRepository;

@Service
public class VolcanicEruptionService implements CRUDOperations<VolcanicEruption> {

	@Autowired
	private VolcanicEruptionRepository volRepo;

	@Autowired
	private UuidVerifier verifier;

	public VolcanicEruptionService() {}

	@Override
	public int create(VolcanicEruption data) {
		if(verifier.uuidAlreadyTaked(data.getUuid()))return 1;
		volRepo.save(data);
		return 0;

	}

	@Override
	public List<VolcanicEruption> getAll() {
		return volRepo.findAll();
	}

	@Override
	public int deleteById(Long id) {
		Optional<VolcanicEruption> found = volRepo.findById(id);

		if (found.isPresent()) {
			volRepo.delete(found.get());
			return 0;
		}
		return 1;
	}
	
	public int deleteByUuid(String uuid) {
		Optional<VolcanicEruption> found = volRepo.findByUuid(uuid);

		if (found.isPresent()) {
			volRepo.delete(found.get());
			return 0;
		}
		return 1;
	}

	@Override
	public int updateById(Long id, VolcanicEruption newData) {

		Optional<VolcanicEruption> found = volRepo.findById(id);
		boolean taked = false;
		if(found.isPresent())taked = verifier.uuidAlreadyTaked(newData.getUuid(),found.get().getUuid());
		else taked = verifier.uuidAlreadyTaked(newData.getUuid());
		if (found.isPresent() && !taked) {
			VolcanicEruption temp = found.get();
			temp.setDisasterName(newData.getDisasterName());
			temp.setUuid(newData.getUuid());
			temp.setDescription(newData.getDescription());
			temp.setContinent(newData.getContinent());
			temp.setCountry(newData.getCountry());
			temp.setInvestigators(newData.getInvestigators());
			temp.setImage(newData.getImage());
			temp.setScope(newData.getScope());
			volRepo.save(temp);
			return 0;
		} else if (found.isPresent() && taked) {
			return 1;
		} else if (!found.isPresent()) {
			return 2;
		}
		return 3;
	}
	
	public int updateByUuid(String uuid, VolcanicEruption newData) {
		Optional<VolcanicEruption> found = volRepo.findByUuid(uuid);
		boolean taked = verifier.uuidAlreadyTaked(newData.getUuid(),uuid);
		if (found.isPresent() && !taked) {
			VolcanicEruption temp = found.get();
			temp.setDisasterName(newData.getDisasterName());
			temp.setUuid(newData.getUuid());
			temp.setDescription(newData.getDescription());
			temp.setContinent(newData.getContinent());
			temp.setCountry(newData.getCountry());
			temp.setInvestigators(newData.getInvestigators());
			temp.setImage(newData.getImage());
			temp.setScope(newData.getScope());
			volRepo.save(temp);
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
		return volRepo.count();
	}

	@Override
	public boolean exists(Long id) {
		return volRepo.existsById(id);
	}

	public boolean exists(String uuid) {
		Optional<VolcanicEruption> found=volRepo.findByUuid(uuid);
		return found.isPresent();
	}

}
