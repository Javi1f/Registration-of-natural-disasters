package co.edu.unbosque.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.unbosque.model.ExtremeHeat;
import co.edu.unbosque.repository.ExtremeHeatRepository;
import co.edu.unbosque.util.AESUtil;

public class ExtremeHeatService implements CRUDOperations<ExtremeHeat> {

	@Autowired
	private ExtremeHeatRepository exheRepo;
	@Autowired
	private CountryService aux;

	public ExtremeHeatService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int create(ExtremeHeat data) {

		try {

			data.setDisasterName(AESUtil.encrypt(data.getDisasterName()));
			data.setUuid(AESUtil.encrypt(data.getUuid()));
			data.getPlace().getDisasters().add(data);
			aux.create(data.getPlace());
			exheRepo.save(data);

			return 0;
		} catch (Exception e) {
			return 1;
		}

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
		} else {
			return 1;
		}
	}

	@Override
	public int updateById(Long id, ExtremeHeat newData) {

		Optional<ExtremeHeat> found = exheRepo.findById(id);
		Optional<ExtremeHeat> newFound = exheRepo.findByUuid(AESUtil.encrypt(newData.getUuid()));

		if (found.isPresent() && !newFound.isPresent()) {
			ExtremeHeat temp = found.get();
			temp.setDisasterName(AESUtil.encrypt(newData.getDisasterName()));
			temp.setUuid(AESUtil.encrypt(newData.getUuid()));
			temp.getPlace().getDisasters().add(newData);
			aux.create(temp.getPlace());
			exheRepo.save(temp);
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
		return exheRepo.count();
	}

	@Override
	public boolean exists(Long id) {
		return exheRepo.existsById(id) ? true : false;
	}

	public boolean exists(String uuid) {
		return exheRepo.findByUuid(uuid).isEmpty();
	}



}
