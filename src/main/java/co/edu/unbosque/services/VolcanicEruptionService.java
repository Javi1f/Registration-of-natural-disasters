package co.edu.unbosque.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.unbosque.model.VolcanicEruption;
import co.edu.unbosque.repository.VolcanicEruptionRepository;
import co.edu.unbosque.util.AESUtil;

public class VolcanicEruptionService implements CRUDOperations<VolcanicEruption> {

	@Autowired
	private VolcanicEruptionRepository volRepo;

	@Autowired
	private CountryService aux;

	public VolcanicEruptionService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int create(VolcanicEruption data) {

		try {

			data.setDisasterName(AESUtil.encrypt(data.getDisasterName()));
			data.setUuid(AESUtil.encrypt(data.getUuid()));
			data.setDescription(AESUtil.encrypt(data.getDescription()));
			data.getPlace().getDisasters().add(data);
			aux.create(data.getPlace());
			volRepo.save(data);

			return 0;
		} catch (Exception e) {
			return 1;
		}

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
		} else {
			return 1;
		}
	}

	@Override
	public int updateById(Long id, VolcanicEruption newData) {

		Optional<VolcanicEruption> found = volRepo.findById(id);
		Optional<VolcanicEruption> newFound = volRepo.findByUuid(AESUtil.encrypt(newData.getUuid()));

		if (found.isPresent() && !newFound.isPresent()) {
			VolcanicEruption temp = found.get();
			temp.setDisasterName(AESUtil.encrypt(newData.getDisasterName()));
			temp.setUuid(AESUtil.encrypt(newData.getUuid()));
			temp.setDescription(AESUtil.encrypt(newData.getDescription()));
			temp.getPlace().getDisasters().add(newData);
			aux.create(temp.getPlace());
			volRepo.save(temp);
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
		return volRepo.count();
	}

	@Override
	public boolean exists(Long id) {
		return volRepo.existsById(id) ? true : false;
	}

	public boolean exists(String uuid) {
		return volRepo.findByUuid(uuid).isEmpty();
	}

}
