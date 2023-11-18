package co.edu.unbosque.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.model.Investigator;
import co.edu.unbosque.repository.InvestigatorRepository;

@Service
public class InvestigatorService implements CRUDOperations<Investigator> {

	@Autowired
	private InvestigatorRepository investRepo;

	public InvestigatorService() {
	}

	@Override
	public int create(Investigator data) {
		if (uuidAlreadyTaked(data.getUuid()))return 1;
		investRepo.save(data);
		return 0;
	}

	@Override
	public List<Investigator> getAll() {
		return investRepo.findAll();
	}

	@Override
	public int deleteById(Long id) {
		Optional<Investigator> found = investRepo.findById(id);
		if (found.isPresent()) {
			investRepo.delete(found.get());
			return 0;
		}
		return 1;
	}

	public int deleteByUuid(String uuid) {
		Optional<Investigator> found = investRepo.findByUuid(uuid);
		if (found.isPresent()) {
			investRepo.delete(found.get());
			return 0;
		}
		return 1;
	}

	@Override
	public int updateById(Long id, Investigator newData) {

		Optional<Investigator> found = investRepo.findById(id);
		Optional<Investigator> newFound = investRepo.findByUuid(newData.getUuid());

		if (found.isPresent() && (!newFound.isPresent() || found.get().getUuid().equals(newData.getUuid()))) {
			Investigator temp = found.get();
			temp.setName(newData.getName());
			temp.setUuid(newData.getUuid());
			temp.setDisastersInvestigated(newData.getDisastersInvestigated());
			investRepo.save(temp);
			return 0;
		} else if (found.isPresent() && newFound.isPresent()) {
			return 1;
		} else if (!found.isPresent()) {
			return 2;
		}
		return 3;
	}

	public int updateByUuid(String uuid, Investigator newData) {
		Optional<Investigator> found = investRepo.findByUuid(uuid);
		Optional<Investigator> newFound = investRepo.findByUuid(newData.getUuid());

		if (found.isPresent() && (!newFound.isPresent() || found.get().getUuid().equals(newData.getUuid()))) {
			Investigator temp = found.get();
			temp.setName(newData.getName());
			temp.setUuid(newData.getUuid());
			investRepo.save(temp);
			return 0;
		} else if (found.isPresent() && newFound.isPresent()) {
			return 1;
		} else if (!found.isPresent()) {
			return 2;
		}
		return 3;
	}
	
	public int addDisasterToInvestigator(String uuidInvestigator, String uuidDisaster) {
		Optional<Investigator> found = investRepo.findByUuid(uuidInvestigator);
		if(found.isPresent()) {
			Investigator temp = found.get();
			temp.getDisastersInvestigated().add(uuidDisaster);
			investRepo.save(temp);
			return 0;
		}
		return 1;
	}
	
	public int removeDisasterOfInvestigator(String removed) {
		int status=1;
		for(int i=0;i<investRepo.findAll().size();i++) {
			if(investRepo.findAll().get(i).removeDisaster(removed))status=0;
		}
		return status;
	}

	@Override
	public long count() {
		return investRepo.count();
	}

	@Override
	public boolean exists(Long id) {
		return investRepo.existsById(id);
	}

	public boolean exists(String uuid) {
		Optional<Investigator> found=investRepo.findByUuid(uuid);
		return found.isPresent();
	}

	public boolean uuidAlreadyTaked(String uuid) {
		Optional<Investigator> found = investRepo.findByUuid(uuid);
		if (found.isPresent()) {
			return true;
		}
		return false;
	}
	
	public Investigator get(String uuid) {
		return investRepo.getByUuid(uuid);
	}

}
