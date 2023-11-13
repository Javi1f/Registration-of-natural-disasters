package co.edu.unbosque.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.model.Country;
import co.edu.unbosque.model.NaturalDisaster;
import co.edu.unbosque.repository.countryRepository;
import co.edu.unbosque.util.AESUtil;

@Service
public class CountryService implements CRUDOperations<Country> {

	@Autowired
	private countryRepository countryRepo;

	public CountryService() {

	}

	public boolean findNameAlreadyTaken(String name) {

		return countryRepo.findByCountName(name).isPresent();
	}

	@Override
	public int create(Country data) {

		try {

			if (!findNameAlreadyTaken(data.getName())) {

				data.setName(AESUtil.encrypt(data.getName()));
				countryRepo.save(data);
			} else {

				for (NaturalDisaster n : data.getDisasters()) {
					addDisaster(data.getName(), n);
				}

			}
			return 0;
		} catch (Exception e) {
			return 1;
		}

	}

	@Override
	public List<Country> getAll() {
		return countryRepo.findAll();
	}

	@Override
	public int deleteById(Long id) {
		Optional<Country> found = countryRepo.findById(id);

		if (found.isPresent()) {
			countryRepo.delete(found.get());
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int updateById(Long id, Country newData) {

		Optional<Country> found = countryRepo.findById(id);
		Optional<Country> newFound = countryRepo.findByCountName(AESUtil.encrypt(newData.getName()));

		if (found.isPresent() && !newFound.isPresent()) {
			Country temp = found.get();
			temp.setName(AESUtil.encrypt(newData.getName()));
			countryRepo.save(temp);
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
		return countryRepo.count();
	}

	@Override
	public boolean exists(Long id) {
		return countryRepo.existsById(id) ? true : false;
	}

	public void addDisaster(String name, NaturalDisaster newDisaster) {

		Optional<Country> country = countryRepo.findByCountName(name);

		if (country.isPresent()) {
			Country temp = country.get();

			if (temp.getDisasters().indexOf(newDisaster) != -1) {
				temp.getDisasters().add(newDisaster);
			}

			countryRepo.save(temp);
		}
	}
}
