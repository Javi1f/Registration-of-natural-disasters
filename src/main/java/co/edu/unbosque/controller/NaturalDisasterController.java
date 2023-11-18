package co.edu.unbosque.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.model.Earthquake;
import co.edu.unbosque.model.ExtremeHeat;
import co.edu.unbosque.model.Hurricane;
import co.edu.unbosque.model.NaturalDisaster;
import co.edu.unbosque.model.Tsunami;
import co.edu.unbosque.model.VolcanicEruption;
import co.edu.unbosque.services.InvestigatorService;
import co.edu.unbosque.services.NaturalDisasterService;
import co.edu.unbosque.util.AESUtil;
import co.edu.unbosque.util.ContinentLocator;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/naturaldisaster")
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:8081","*"})
@Transactional
public class NaturalDisasterController {
	
	@Autowired
	private NaturalDisasterService naturalDisasterServ;
	
	@Autowired
	private InvestigatorService invServ;
	
	public NaturalDisasterController() {}
	
	@PostMapping(path = "/createdisaster", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createNaturalDisaster(@RequestBody NaturalDisaster data) {
		String continent=AESUtil.encrypt(ContinentLocator.getContinent(data.getCountry()));
		NaturalDisaster encripted=new NaturalDisaster(AESUtil.encrypt(data.getUuid()), continent, AESUtil.encrypt(data.getCountry()), encryptInvestigators(data.getInvestigators()), AESUtil.encrypt(data.getDisasterName()), AESUtil.encrypt(data.getDescription()), data.getImage());
		int status=naturalDisasterServ.create(encripted);
		if(status==0) {
			for(String aux: encripted.getInvestigators()) {
				invServ.addDisasterToInvestigator(aux, encripted.getUuid());
			}
			return new ResponseEntity<String>("Natural disaster create successfully.",HttpStatus.ACCEPTED);
		}
		else if(status==1) {
			return new ResponseEntity<String>("Uuid already taked.",HttpStatus.NOT_ACCEPTABLE);
		}
		return new ResponseEntity<String>("Internal server error.",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping(path = "/updatedisaster/{uuid}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updatebyUiid(@PathVariable String uuid, @RequestBody NaturalDisaster data){
		String continent=AESUtil.encrypt(ContinentLocator.getContinent(data.getCountry()));
		NaturalDisaster encripted=new NaturalDisaster(AESUtil.encrypt(data.getUuid()), continent, AESUtil.encrypt(data.getCountry()), encryptInvestigators(data.getInvestigators()), AESUtil.encrypt(data.getDisasterName()), AESUtil.encrypt(data.getDescription()), data.getImage());
		String auxUuid=AESUtil.encrypt(uuid);
		int status=naturalDisasterServ.updateByUuid(auxUuid, encripted);
		if(status==0) {
			invServ.removeDisasterOfInvestigator(auxUuid);
			for(String aux: encripted.getInvestigators()) {
				invServ.addDisasterToInvestigator(aux, encripted.getUuid());
			}
			return new ResponseEntity<String>("Disaster successfully update.",HttpStatus.ACCEPTED);
		}
		else if(status==1) {
			return new ResponseEntity<String>("Uuid already taked.",HttpStatus.NOT_ACCEPTABLE);
		}
		return new ResponseEntity<String>("Uuid not found.",HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping(path = "/deletedesaster/{uuid}")
	public ResponseEntity<String> deletebyUiid(@PathVariable String uuid){
		String auxUuid=AESUtil.encrypt(uuid);
		int status=naturalDisasterServ.deleteByUuid(auxUuid);
		if(status==0) {
			int status2=invServ.removeDisasterOfInvestigator(auxUuid);
			if(status2==1)return new ResponseEntity<String>("Error in delete investigator with this natural service.",HttpStatus.NOT_FOUND);
			return new ResponseEntity<String>("Disaster successfully delete.",HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<String>("Uuid not found.",HttpStatus.NOT_FOUND);	
	}
	
	@GetMapping(path = "/getall")
	public ResponseEntity<List<NaturalDisaster>> getAll(){
		List<NaturalDisaster> enDisasters=naturalDisasterServ.getAll();
		if(enDisasters.isEmpty())return new ResponseEntity<List<NaturalDisaster>>(enDisasters,HttpStatus.NO_CONTENT);
		List<NaturalDisaster> deDisasters=new ArrayList<>();
		for(NaturalDisaster data: enDisasters) {
			String continent=AESUtil.decrypt(data.getContinent());
			NaturalDisaster decripted=new NaturalDisaster();
			if(data instanceof Earthquake) {
				decripted=new Earthquake(AESUtil.decrypt(data.getUuid()), continent, AESUtil.decrypt(data.getCountry()), decryptInvestigators(data.getInvestigators()), AESUtil.decrypt(data.getDisasterName()), AESUtil.decrypt(data.getDescription()), data.getImage(),((Earthquake)data).getMagnitude());
			}
			else if(data instanceof ExtremeHeat) {
				decripted=new ExtremeHeat(AESUtil.decrypt(data.getUuid()), continent, AESUtil.decrypt(data.getCountry()), decryptInvestigators(data.getInvestigators()), AESUtil.decrypt(data.getDisasterName()), AESUtil.decrypt(data.getDescription()), data.getImage(),((ExtremeHeat)data).getMaximumTemperature());
			}
			else if(data instanceof Hurricane) {
				decripted=new Hurricane(AESUtil.decrypt(data.getUuid()), continent, AESUtil.decrypt(data.getCountry()), decryptInvestigators(data.getInvestigators()), AESUtil.decrypt(data.getDisasterName()), AESUtil.decrypt(data.getDescription()), data.getImage(),((Hurricane)data).getMagnitude());
			}
			else if(data instanceof Tsunami) {
				decripted=new Tsunami(AESUtil.decrypt(data.getUuid()), continent, AESUtil.decrypt(data.getCountry()), decryptInvestigators(data.getInvestigators()), AESUtil.decrypt(data.getDisasterName()), AESUtil.decrypt(data.getDescription()), data.getImage(),((Tsunami)data).getWaveHeight());
			}
			else if(data instanceof VolcanicEruption) {
				decripted=new VolcanicEruption(AESUtil.decrypt(data.getUuid()), continent, AESUtil.decrypt(data.getCountry()), decryptInvestigators(data.getInvestigators()), AESUtil.decrypt(data.getDisasterName()), AESUtil.decrypt(data.getDescription()), data.getImage(),((VolcanicEruption)data).getScope());
			}
			else {
				decripted=new NaturalDisaster(AESUtil.decrypt(data.getUuid()), continent, AESUtil.decrypt(data.getCountry()), decryptInvestigators(data.getInvestigators()), AESUtil.decrypt(data.getDisasterName()), AESUtil.decrypt(data.getDescription()), data.getImage());
			}
			decripted.setId(data.getId());
			deDisasters.add(decripted);
		}
		return new ResponseEntity<List<NaturalDisaster>>(deDisasters,HttpStatus.ACCEPTED);
	}
	
	@GetMapping(path = "/get/{uuid}")
	public ResponseEntity<NaturalDisaster> get(@PathVariable String uuid){
		String enUuid=AESUtil.encrypt(uuid);
		if(naturalDisasterServ.exists(enUuid)) {
			NaturalDisaster data=naturalDisasterServ.get(enUuid);
			String continent=AESUtil.decrypt(data.getContinent());
			NaturalDisaster decripted=new NaturalDisaster();
			if(data instanceof Earthquake) {
				decripted=new Earthquake(AESUtil.decrypt(data.getUuid()), continent, AESUtil.decrypt(data.getCountry()), decryptInvestigators(data.getInvestigators()), AESUtil.decrypt(data.getDisasterName()), AESUtil.decrypt(data.getDescription()), data.getImage(),((Earthquake)data).getMagnitude());
			}
			else if(data instanceof ExtremeHeat) {
				decripted=new ExtremeHeat(AESUtil.decrypt(data.getUuid()), continent, AESUtil.decrypt(data.getCountry()), decryptInvestigators(data.getInvestigators()), AESUtil.decrypt(data.getDisasterName()), AESUtil.decrypt(data.getDescription()), data.getImage(),((ExtremeHeat)data).getMaximumTemperature());
			}
			else if(data instanceof Hurricane) {
				decripted=new Hurricane(AESUtil.decrypt(data.getUuid()), continent, AESUtil.decrypt(data.getCountry()), decryptInvestigators(data.getInvestigators()), AESUtil.decrypt(data.getDisasterName()), AESUtil.decrypt(data.getDescription()), data.getImage(),((Hurricane)data).getMagnitude());
			}
			else if(data instanceof Tsunami) {
				decripted=new Tsunami(AESUtil.decrypt(data.getUuid()), continent, AESUtil.decrypt(data.getCountry()), decryptInvestigators(data.getInvestigators()), AESUtil.decrypt(data.getDisasterName()), AESUtil.decrypt(data.getDescription()), data.getImage(),((Tsunami)data).getWaveHeight());
			}
			else if(data instanceof VolcanicEruption) {
				decripted=new VolcanicEruption(AESUtil.decrypt(data.getUuid()), continent, AESUtil.decrypt(data.getCountry()), decryptInvestigators(data.getInvestigators()), AESUtil.decrypt(data.getDisasterName()), AESUtil.decrypt(data.getDescription()), data.getImage(),((VolcanicEruption)data).getScope());
			}
			else {
				decripted=new NaturalDisaster(AESUtil.decrypt(data.getUuid()), continent, AESUtil.decrypt(data.getCountry()), decryptInvestigators(data.getInvestigators()), AESUtil.decrypt(data.getDisasterName()), AESUtil.decrypt(data.getDescription()), data.getImage());
			}
			decripted.setId(data.getId());
			return new ResponseEntity<NaturalDisaster>(decripted,HttpStatus.FOUND);
		}
		return new ResponseEntity<NaturalDisaster>(new NaturalDisaster(),HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(path = "/getbycountry/{country}")
	public ResponseEntity<List<NaturalDisaster>> getByCountry(@PathVariable String country){
		List<NaturalDisaster> enDisasters=naturalDisasterServ.getAll();
		List<NaturalDisaster> deDisasters=new ArrayList<>();
		String filter=AESUtil.encrypt(country);
		for(NaturalDisaster data: enDisasters) {
			if(filter.equals(data.getCountry())) {
				String continent=AESUtil.decrypt(data.getContinent());
				NaturalDisaster decripted=new NaturalDisaster();
				if(data instanceof Earthquake) {
					decripted=new Earthquake(AESUtil.decrypt(data.getUuid()), continent, AESUtil.decrypt(data.getCountry()), decryptInvestigators(data.getInvestigators()), AESUtil.decrypt(data.getDisasterName()), AESUtil.decrypt(data.getDescription()), data.getImage(),((Earthquake)data).getMagnitude());
				}
				else if(data instanceof ExtremeHeat) {
					decripted=new ExtremeHeat(AESUtil.decrypt(data.getUuid()), continent, AESUtil.decrypt(data.getCountry()), decryptInvestigators(data.getInvestigators()), AESUtil.decrypt(data.getDisasterName()), AESUtil.decrypt(data.getDescription()), data.getImage(),((ExtremeHeat)data).getMaximumTemperature());
				}
				else if(data instanceof Hurricane) {
					decripted=new Hurricane(AESUtil.decrypt(data.getUuid()), continent, AESUtil.decrypt(data.getCountry()), decryptInvestigators(data.getInvestigators()), AESUtil.decrypt(data.getDisasterName()), AESUtil.decrypt(data.getDescription()), data.getImage(),((Hurricane)data).getMagnitude());
				}
				else if(data instanceof Tsunami) {
					decripted=new Tsunami(AESUtil.decrypt(data.getUuid()), continent, AESUtil.decrypt(data.getCountry()), decryptInvestigators(data.getInvestigators()), AESUtil.decrypt(data.getDisasterName()), AESUtil.decrypt(data.getDescription()), data.getImage(),((Tsunami)data).getWaveHeight());
				}
				else if(data instanceof VolcanicEruption) {
					decripted=new VolcanicEruption(AESUtil.decrypt(data.getUuid()), continent, AESUtil.decrypt(data.getCountry()), decryptInvestigators(data.getInvestigators()), AESUtil.decrypt(data.getDisasterName()), AESUtil.decrypt(data.getDescription()), data.getImage(),((VolcanicEruption)data).getScope());
				}
				else {
					decripted=new NaturalDisaster(AESUtil.decrypt(data.getUuid()), continent, AESUtil.decrypt(data.getCountry()), decryptInvestigators(data.getInvestigators()), AESUtil.decrypt(data.getDisasterName()), AESUtil.decrypt(data.getDescription()), data.getImage());
				}
				decripted.setId(data.getId());
				deDisasters.add(decripted);
			}
		}
		if(deDisasters.isEmpty())return new ResponseEntity<List<NaturalDisaster>>(deDisasters,HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<NaturalDisaster>>(deDisasters,HttpStatus.ACCEPTED);
	}
	
	@GetMapping(path = "/getbycontinent/{continent}")
	public ResponseEntity<List<NaturalDisaster>> getByContinent(@PathVariable String continent){
		List<NaturalDisaster> enDisasters=naturalDisasterServ.getAll();
		List<NaturalDisaster> deDisasters=new ArrayList<>();
		String filter=AESUtil.encrypt(continent);
		for(NaturalDisaster data: enDisasters) {
			if(filter.equals(data.getContinent())) {
				NaturalDisaster decripted=new NaturalDisaster();
				if(data instanceof Earthquake) {
					decripted=new Earthquake(AESUtil.decrypt(data.getUuid()), continent, AESUtil.decrypt(data.getCountry()), decryptInvestigators(data.getInvestigators()), AESUtil.decrypt(data.getDisasterName()), AESUtil.decrypt(data.getDescription()), data.getImage(),((Earthquake)data).getMagnitude());
				}
				else if(data instanceof ExtremeHeat) {
					decripted=new ExtremeHeat(AESUtil.decrypt(data.getUuid()), continent, AESUtil.decrypt(data.getCountry()), decryptInvestigators(data.getInvestigators()), AESUtil.decrypt(data.getDisasterName()), AESUtil.decrypt(data.getDescription()), data.getImage(),((ExtremeHeat)data).getMaximumTemperature());
				}
				else if(data instanceof Hurricane) {
					decripted=new Hurricane(AESUtil.decrypt(data.getUuid()), continent, AESUtil.decrypt(data.getCountry()), decryptInvestigators(data.getInvestigators()), AESUtil.decrypt(data.getDisasterName()), AESUtil.decrypt(data.getDescription()), data.getImage(),((Hurricane)data).getMagnitude());
				}
				else if(data instanceof Tsunami) {
					decripted=new Tsunami(AESUtil.decrypt(data.getUuid()), continent, AESUtil.decrypt(data.getCountry()), decryptInvestigators(data.getInvestigators()), AESUtil.decrypt(data.getDisasterName()), AESUtil.decrypt(data.getDescription()), data.getImage(),((Tsunami)data).getWaveHeight());
				}
				else if(data instanceof VolcanicEruption) {
					decripted=new VolcanicEruption(AESUtil.decrypt(data.getUuid()), continent, AESUtil.decrypt(data.getCountry()), decryptInvestigators(data.getInvestigators()), AESUtil.decrypt(data.getDisasterName()), AESUtil.decrypt(data.getDescription()), data.getImage(),((VolcanicEruption)data).getScope());
				}
				else {
					decripted=new NaturalDisaster(AESUtil.decrypt(data.getUuid()), continent, AESUtil.decrypt(data.getCountry()), decryptInvestigators(data.getInvestigators()), AESUtil.decrypt(data.getDisasterName()), AESUtil.decrypt(data.getDescription()), data.getImage());
				}
				decripted.setId(data.getId());
				deDisasters.add(decripted);
			}
		}
		if(deDisasters.isEmpty())return new ResponseEntity<List<NaturalDisaster>>(deDisasters,HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<NaturalDisaster>>(deDisasters,HttpStatus.ACCEPTED);
	}
	
	@GetMapping(path = "/getbyinvestigatoruuid/{uuid}")
	public ResponseEntity<List<NaturalDisaster>> getByInvestigadorUuid(@PathVariable String uuid){
		List<NaturalDisaster> enDisasters=naturalDisasterServ.getAll();
		List<NaturalDisaster> deDisasters=new ArrayList<>();
		String filter=AESUtil.encrypt(uuid);
		for(NaturalDisaster data: enDisasters) {
			if(data.containsInvestigator(filter)) {
				String continent=AESUtil.decrypt(data.getContinent());
				NaturalDisaster decripted=new NaturalDisaster();
				if(data instanceof Earthquake) {
					decripted=new Earthquake(AESUtil.decrypt(data.getUuid()), continent, AESUtil.decrypt(data.getCountry()), decryptInvestigators(data.getInvestigators()), AESUtil.decrypt(data.getDisasterName()), AESUtil.decrypt(data.getDescription()), data.getImage(),((Earthquake)data).getMagnitude());
				}
				else if(data instanceof ExtremeHeat) {
					decripted=new ExtremeHeat(AESUtil.decrypt(data.getUuid()), continent, AESUtil.decrypt(data.getCountry()), decryptInvestigators(data.getInvestigators()), AESUtil.decrypt(data.getDisasterName()), AESUtil.decrypt(data.getDescription()), data.getImage(),((ExtremeHeat)data).getMaximumTemperature());
				}
				else if(data instanceof Hurricane) {
					decripted=new Hurricane(AESUtil.decrypt(data.getUuid()), continent, AESUtil.decrypt(data.getCountry()), decryptInvestigators(data.getInvestigators()), AESUtil.decrypt(data.getDisasterName()), AESUtil.decrypt(data.getDescription()), data.getImage(),((Hurricane)data).getMagnitude());
				}
				else if(data instanceof Tsunami) {
					decripted=new Tsunami(AESUtil.decrypt(data.getUuid()), continent, AESUtil.decrypt(data.getCountry()), decryptInvestigators(data.getInvestigators()), AESUtil.decrypt(data.getDisasterName()), AESUtil.decrypt(data.getDescription()), data.getImage(),((Tsunami)data).getWaveHeight());
				}
				else if(data instanceof VolcanicEruption) {
					decripted=new VolcanicEruption(AESUtil.decrypt(data.getUuid()), continent, AESUtil.decrypt(data.getCountry()), decryptInvestigators(data.getInvestigators()), AESUtil.decrypt(data.getDisasterName()), AESUtil.decrypt(data.getDescription()), data.getImage(),((VolcanicEruption)data).getScope());
				}
				else {
					decripted=new NaturalDisaster(AESUtil.decrypt(data.getUuid()), continent, AESUtil.decrypt(data.getCountry()), decryptInvestigators(data.getInvestigators()), AESUtil.decrypt(data.getDisasterName()), AESUtil.decrypt(data.getDescription()), data.getImage());
				}
				decripted.setId(data.getId());
				deDisasters.add(decripted);
			}
		}
		if(deDisasters.isEmpty())return new ResponseEntity<List<NaturalDisaster>>(deDisasters,HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<NaturalDisaster>>(deDisasters,HttpStatus.ACCEPTED);
	}

	public ArrayList<String> encryptInvestigators(ArrayList<String> encryptInvestigators){
		ArrayList<String> aux=new ArrayList<>();
		for(String investigator: encryptInvestigators) {
			aux.add(AESUtil.encrypt(investigator));
		}
		return aux;
	}
	
	public ArrayList<String> decryptInvestigators(ArrayList<String> encryptInvestigators){
		ArrayList<String> aux=new ArrayList<>();
		for(String investigator: encryptInvestigators) {
			aux.add(AESUtil.decrypt(investigator));
		}
		return aux;
	}
}
