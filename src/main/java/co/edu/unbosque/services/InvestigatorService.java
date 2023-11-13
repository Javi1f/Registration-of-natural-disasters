package co.edu.unbosque.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.model.Investigator;
import co.edu.unbosque.repository.InvestigatorRepository;
import co.edu.unbosque.util.AESUtil;

@Service
public class InvestigatorService implements CRUDOperations<Investigator> {

	@Autowired
	private InvestigatorRepository investRepo;

	public InvestigatorService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int create(Investigator data) {

		try {

			data.setName(AESUtil.encrypt(data.getName()));
			data.setUuid(AESUtil.encrypt(data.getUuid()));

			investRepo.save(data);

			return 0;
		} catch (Exception e) {
			return 1;
		}

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
		} else {
			return 1;
		}
	}

	@Override
	public int updateById(Long id, Investigator newData) {

		Optional<Investigator> found = investRepo.findById(id);
		Optional<Investigator> newFound = investRepo.findByUuid(AESUtil.encrypt(newData.getUuid()));

		if (found.isPresent() && !newFound.isPresent()) {
			Investigator temp = found.get();
			temp.setName(AESUtil.encrypt(newData.getName()));
			temp.setUuid(AESUtil.encrypt(newData.getUuid()));
			investRepo.save(temp);
			return 0;
		} else if (found.isPresent() && newFound.isPresent()) {
			return 1;
		} else if (!found.isPresent()) {
			return 2;
		} else {
			return 3;
		}

	}

	@Override
	public long count() {
		return investRepo.count();
	}

	@Override
	public boolean exists(Long id) {
		return investRepo.existsById(id) ? true : false;
	}

	public boolean exists(String uuid) {
		return investRepo.findByUuid(uuid).isEmpty();
	}

}
