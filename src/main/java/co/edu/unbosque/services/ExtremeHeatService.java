package co.edu.unbosque.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.model.ExtremeHeat;
import co.edu.unbosque.repository.ExtremeHeatRepository;

@Service
public class ExtremeHeatService implements CRUDOperations<ExtremeHeat> {

	@Autowired
	private ExtremeHeatRepository exheRepo;
	@Autowired
	private UuidVerifier verifier;

	public ExtremeHeatService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int create(ExtremeHeat data) {
		if(verifier.uuidAlreadyTaked(data.getUuid()))return 1;
		exheRepo.save(data);
		return 0;
	}

	@Override
	public List<ExtremeHeat> getAll() {
		return exheRepo.findAll();
	}

	@Override
	public int deleteById(Long id) {
		Optional<ExtremeHeat> found = exheRepo.findById(id);
		if (found.isPresent()) {
			exheRepo.delete(found.get());
			return 0;
		}
		return 1;
	}
	
	public int deleteByUuid(String uuid) {
		Optional<ExtremeHeat> found = exheRepo.findByUuid(uuid);
		if (found.isPresent()) {
			exheRepo.delete(found.get());
			return 0;
		}
		return 1;
	}

	@Override
	public int updateById(Long id, ExtremeHeat newData) {

		Optional<ExtremeHeat> found = exheRepo.findById(id);
		boolean taked = false;
		if(found.isPresent())taked = verifier.uuidAlreadyTaked(newData.getUuid(),found.get().getUuid());
		else taked = verifier.uuidAlreadyTaked(newData.getUuid());
		if (found.isPresent() && !taked) {
			ExtremeHeat temp = found.get();
			temp.setDisasterName(newData.getDisasterName());
			temp.setUuid(newData.getUuid());
			temp.setDescription(newData.getDescription());
			temp.setContinent(newData.getContinent());
			temp.setCountry(newData.getCountry());
			temp.setInvestigators(newData.getInvestigators());
			temp.setImage(newData.getImage());
			temp.setMaximumTemperature(newData.getMaximumTemperature());
			exheRepo.save(temp);
			return 0;
		} else if (found.isPresent() && taked) {
			return 1;
		} else if (!found.isPresent()) {
			return 2;
		}
		return 3;

	}
	
	public int updateByUuid(String uuid, ExtremeHeat newData) {

		Optional<ExtremeHeat> found = exheRepo.findByUuid(uuid);
		boolean taked = verifier.uuidAlreadyTaked(newData.getUuid(),uuid);
		if (found.isPresent() && !taked) {
			ExtremeHeat temp = found.get();
			temp.setDisasterName(newData.getDisasterName());
			temp.setUuid(newData.getUuid());
			temp.setDescription(newData.getDescription());
			temp.setContinent(newData.getContinent());
			temp.setCountry(newData.getCountry());
			temp.setInvestigators(newData.getInvestigators());
			temp.setImage(newData.getImage());
			temp.setMaximumTemperature(newData.getMaximumTemperature());
			exheRepo.save(temp);
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
		return exheRepo.count();
	}

	@Override
	public boolean exists(Long id) {
		return exheRepo.existsById(id) ? true : false;
	}

	public boolean exists(String uuid) {
		Optional<ExtremeHeat> found=exheRepo.findByUuid(uuid);
		return found.isPresent();
	}

}
