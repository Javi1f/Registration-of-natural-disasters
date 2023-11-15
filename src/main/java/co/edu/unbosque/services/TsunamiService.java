package co.edu.unbosque.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.unbosque.model.Tsunami;
import co.edu.unbosque.repository.TsunamiRepository;
import co.edu.unbosque.util.AESUtil;

public class TsunamiService implements CRUDOperations<Tsunami> {

	@Autowired
	private TsunamiRepository tsuRepo;
	@Autowired
	private CountryService aux;

	public TsunamiService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int create(Tsunami data) {

		try {

			data.setDisasterName(AESUtil.encrypt(data.getDisasterName()));
			data.setUuid(AESUtil.encrypt(data.getUuid()));
			data.getPlace().getDisasters().add(data);
			aux.create(data.getPlace());
			tsuRepo.save(data);

			return 0;
		} catch (Exception e) {
			return 1;
		}

	}

	@Override
	public List<Tsunami> getAll() {
		return tsuRepo.findAll();
	}

	@Override
	public int deleteById(Long id) {
		Optional<Tsunami> found = tsuRepo.findById(id);

		if (found.isPresent()) {
			tsuRepo.delete(found.get());
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int updateById(Long id, Tsunami newData) {

		Optional<Tsunami> found = tsuRepo.findById(id);
		Optional<Tsunami> newFound = tsuRepo.findByUuid(AESUtil.encrypt(newData.getUuid()));

		if (found.isPresent() && !newFound.isPresent()) {
			Tsunami temp = found.get();
			temp.setDisasterName(AESUtil.encrypt(newData.getDisasterName()));
			temp.setUuid(AESUtil.encrypt(newData.getUuid()));
			temp.getPlace().getDisasters().add(newData);
			aux.create(temp.getPlace());
			tsuRepo.save(temp);
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
		return tsuRepo.count();
	}

	@Override
	public boolean exists(Long id) {
		return tsuRepo.existsById(id) ? true : false;
	}

	public boolean exists(String uuid) {
		return tsuRepo.findByUuid(uuid).isEmpty();
	}

}
