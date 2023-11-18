package co.edu.unbosque.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.model.NaturalDisaster;
import co.edu.unbosque.repository.NaturalDisasterRepository;

@Service
public class NaturalDisasterService implements CRUDOperations<NaturalDisaster> {

	@Autowired
	private NaturalDisasterRepository natRepo;

	@Autowired
	private UuidVerifier verifier;

	public NaturalDisasterService() {
	}

	@Override
	public int create(NaturalDisaster data) {
		if(verifier.uuidAlreadyTaked(data.getUuid()))return 1;
		natRepo.save(data);
		return 0;
	}

	@Override
	public List<NaturalDisaster> getAll() {
		return natRepo.findAll();
	}

	@Override
	public int deleteById(Long id) {
		Optional<NaturalDisaster> found = natRepo.findById(id);

		if (found.isPresent()) {
			natRepo.delete(found.get());
			return 0;
		} else {
			return 1;
		}
	}

	public int deleteByUuid(String uuid) {
		Optional<NaturalDisaster> found = natRepo.findByUuid(uuid);

		if (found.isPresent()) {
			natRepo.delete(found.get());
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int updateById(Long id, NaturalDisaster newData) {
		Optional<NaturalDisaster> found = natRepo.findById(id);
		boolean taked = false;
		if (found.isPresent())
			taked = verifier.uuidAlreadyTaked(newData.getUuid(),found.get().getUuid());
		else
			taked = verifier.uuidAlreadyTaked(newData.getUuid());
		if (found.isPresent() && !taked) {
			NaturalDisaster temp = found.get();
			temp.setDisasterName(newData.getDisasterName());
			temp.setUuid(newData.getUuid());
			temp.setDescription(newData.getDescription());
			temp.setContinent(newData.getContinent());
			temp.setCountry(newData.getCountry());
			temp.setInvestigators(newData.getInvestigators());
			temp.setImage(newData.getImage());
			natRepo.save(temp);
			return 0;
		} else if (found.isPresent() && taked) {
			return 1;
		} else if (!found.isPresent()) {
			return 2;
		}
		return 3;

	}

	public int updateByUuid(String uuid, NaturalDisaster newData) {
		Optional<NaturalDisaster> found = natRepo.findByUuid(uuid);
		boolean taked = verifier.uuidAlreadyTaked(newData.getUuid(),uuid);
		if (found.isPresent() && !taked) {
			NaturalDisaster temp = found.get();
			temp.setDisasterName(newData.getDisasterName());
			temp.setUuid(newData.getUuid());
			temp.setDescription(newData.getDescription());
			temp.setContinent(newData.getContinent());
			temp.setCountry(newData.getCountry());
			temp.setInvestigators(newData.getInvestigators());
			temp.setImage(newData.getImage());
			natRepo.save(temp);
			return 0;
		} else if (found.isPresent() && taked) {
			return 1;
		} else if (!found.isPresent()) {
			return 2;
		}
		return 3;
	}
	
	public void removeInvestigatorOfDisaster(String removed) {
		for(int i=0;i<natRepo.findAll().size();i++) {
			natRepo.findAll().get(i).removeInvestigator(removed);
		}
	}

	@Override
	public long count() {
		return natRepo.count();
	}

	@Override
	public boolean exists(Long id) {
		return natRepo.existsById(id);
	}

	public boolean exists(String uuid) {
		Optional<NaturalDisaster> found=natRepo.findByUuid(uuid);
		return found.isPresent();
	}
	
	public NaturalDisaster get(String uuid) {
		return natRepo.getByUuid(uuid);
	}

}
