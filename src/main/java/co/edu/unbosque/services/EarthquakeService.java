package co.edu.unbosque.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.unbosque.model.Earthquake;
import co.edu.unbosque.repository.EarthquakeRepository;
import co.edu.unbosque.util.AESUtil;

public class EarthquakeService implements CRUDOperations<Earthquake> {

	@Autowired
	private EarthquakeRepository earthRepo;
	@Autowired
	private CountryService aux;

	public EarthquakeService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int create(Earthquake data) {

		try {

			data.setDisasterName(AESUtil.encrypt(data.getDisasterName()));
			data.setUuid(AESUtil.encrypt(data.getUuid()));
			data.setDescription(AESUtil.encrypt(data.getDescription()));
			data.getPlace().getDisasters().add(data);
			aux.create(data.getPlace());
			earthRepo.save(data);

			return 0;
		} catch (Exception e) {
			return 1;
		}

	}

	@Override
	public List<Earthquake> getAll() {
		return earthRepo.findAll();
	}

	@Override
	public int deleteById(Long id) {
		Optional<Earthquake> found = earthRepo.findById(id);

		if (found.isPresent()) {
			earthRepo.delete(found.get());
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int updateById(Long id, Earthquake newData) {

		Optional<Earthquake> found = earthRepo.findById(id);
		Optional<Earthquake> newFound = earthRepo.findByUuid(AESUtil.encrypt(newData.getUuid()));

		if (found.isPresent() && !newFound.isPresent()) {
			Earthquake temp = found.get();
			temp.setDisasterName(AESUtil.encrypt(newData.getDisasterName()));
			temp.setUuid(AESUtil.encrypt(newData.getUuid()));
			temp.setDescription(AESUtil.encrypt(newData.getDescription()));
			temp.getPlace().getDisasters().add(newData);
			aux.create(temp.getPlace());
			earthRepo.save(temp);
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
		return earthRepo.count();
	}

	@Override
	public boolean exists(Long id) {
		return earthRepo.existsById(id) ? true : false;
	}

	public boolean exists(String uuid) {
		return earthRepo.findByUuid(uuid).isEmpty();
	}

}
