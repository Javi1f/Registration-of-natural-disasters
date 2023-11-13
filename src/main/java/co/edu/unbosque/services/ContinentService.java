package co.edu.unbosque.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.model.Continent;
import co.edu.unbosque.model.Country;
import co.edu.unbosque.repository.ContinentRepository;
import co.edu.unbosque.repository.countryRepository;
import co.edu.unbosque.util.AESUtil;

@Service
public class ContinentService implements CRUDOperations<Continent> {

	@Autowired
	private ContinentRepository contRepo;

	public ContinentService() {
		// TODO Auto-generated constructor stub
	}

	public boolean findNameAlreadyTaken(String name) {

		return contRepo.findByNameCont(name).isPresent();
	}

	@Override
	public int create(Continent data) {

		try {

			if (!findNameAlreadyTaken(data.getName())) {

				data.setName(AESUtil.encrypt(data.getName()));
				contRepo.save(data);
			} else {
				updateById(data.getId(), data);
			}

			return 0;
		} catch (Exception e) {
			return 1;
		}

	}

	@Override
	public List<Continent> getAll() {
		return contRepo.findAll();
	}

	@Override
	public int deleteById(Long id) {
		Optional<Continent> found = contRepo.findById(id);

		if (found.isPresent()) {
			contRepo.delete(found.get());
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int updateById(Long id, Continent newData) {

		Optional<Continent> found = contRepo.findById(id);
		Optional<Continent> newFound = contRepo.findByNameCont(AESUtil.encrypt(newData.getName()));

		if (found.isPresent() && !newFound.isPresent()) {
			Continent temp = found.get();
			temp.setName(AESUtil.encrypt(newData.getName()));
			contRepo.save(temp);
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
		return contRepo.count();
	}

	@Override
	public boolean exists(Long id) {
		return contRepo.existsById(id) ? true : false;
	}

	public void addDisaster(String name, Country newCountry) {

		Optional<Continent> country = contRepo.findByNameCont(name);

		if (country.isPresent()) {
			Continent temp = country.get();
			temp.getCountries().add(newCountry);

			contRepo.save(temp);
		}
	}

	public void addCountry(String name, Country newCountry) {
		
		Optional<Continent> continent = contRepo.findByNameCont(name);
		
		if (continent.isPresent()) {
			Continent temp = continent.get();
			
			if (temp.getCountries().indexOf(newCountry) != -1) {
				temp.getCountries().add(newCountry);
			}
			
			contRepo.save(temp);
		}
		
	}
}
