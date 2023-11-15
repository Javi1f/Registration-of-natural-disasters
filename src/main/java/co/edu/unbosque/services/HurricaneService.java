package co.edu.unbosque.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.unbosque.model.Hurricane;
import co.edu.unbosque.repository.HurricaneRepository;
import co.edu.unbosque.util.AESUtil;

public class HurricaneService implements CRUDOperations<Hurricane> {

	@Autowired
	private HurricaneRepository hurRepo;
	@Autowired
	private CountryService aux;

	public HurricaneService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int create(Hurricane data) {

		try {

			data.setDisasterName(AESUtil.encrypt(data.getDisasterName()));
			data.setUuid(AESUtil.encrypt(data.getUuid()));
			data.setDescription(AESUtil.encrypt(data.getDescription()));
			data.getPlace().getDisasters().add(data);
			aux.create(data.getPlace());
			hurRepo.save(data);

			return 0;
		} catch (Exception e) {
			return 1;
		}

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
		} else {
			return 1;
		}
	}

	@Override
	public int updateById(Long id, Hurricane newData) {

		Optional<Hurricane> found = hurRepo.findById(id);
		Optional<Hurricane> newFound = hurRepo.findByUuid(AESUtil.encrypt(newData.getUuid()));

		if (found.isPresent() && !newFound.isPresent()) {
			Hurricane temp = found.get();
			temp.setDisasterName(AESUtil.encrypt(newData.getDisasterName()));
			temp.setUuid(AESUtil.encrypt(newData.getUuid()));
			temp.setDescription(AESUtil.encrypt(newData.getDescription()));
			temp.getPlace().getDisasters().add(newData);
			aux.create(temp.getPlace());
			hurRepo.save(temp);
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
		return hurRepo.count();
	}

	@Override
	public boolean exists(Long id) {
		return hurRepo.existsById(id) ? true : false;
	}

	public boolean exists(String uuid) {
		return hurRepo.findByUuid(uuid).isEmpty();
	}

}
