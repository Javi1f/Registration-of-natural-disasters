package co.edu.unbosque.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.unbosque.model.NaturalDisaster;
import co.edu.unbosque.repository.NaturalDisasterRepository;
import co.edu.unbosque.util.AESUtil;

public class NaturalDisasterService implements CRUDOperations<NaturalDisaster> {

	@Autowired
	private NaturalDisasterRepository natRepo;
	@Autowired
	private CountryService aux;

	public NaturalDisasterService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int create(NaturalDisaster data) {

		try {

			data.setDisasterName(AESUtil.encrypt(data.getDisasterName()));
			data.setUuid(AESUtil.encrypt(data.getUuid()));
			data.getPlace().getDisasters().add(data);
			aux.create(data.getPlace());
			natRepo.save(data);

			return 0;
		} catch (Exception e) {
			return 1;
		}

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

	@Override
	public int updateById(Long id, NaturalDisaster newData) {

		Optional<NaturalDisaster> found = natRepo.findById(id);
		Optional<NaturalDisaster> newFound = natRepo.findByUuid(AESUtil.encrypt(newData.getUuid()));

		if (found.isPresent() && !newFound.isPresent()) {
			NaturalDisaster temp = found.get();
			temp.setDisasterName(AESUtil.encrypt(newData.getDisasterName()));
			temp.setUuid(AESUtil.encrypt(newData.getUuid()));
			temp.getPlace().getDisasters().add(newData);
			aux.create(temp.getPlace());
			natRepo.save(temp);
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
		return natRepo.count();
	}

	@Override
	public boolean exists(Long id) {
		return natRepo.existsById(id) ? true : false;
	}

	public boolean exists(String uuid) {
		return natRepo.findByUuid(uuid).isEmpty();
	}

}
