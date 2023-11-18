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

import co.edu.unbosque.model.Tsunami;
import co.edu.unbosque.services.InvestigatorService;
import co.edu.unbosque.services.TsunamiService;
import co.edu.unbosque.util.AESUtil;
import co.edu.unbosque.util.ContinentLocator;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/tsunami")
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:8081","*"})
@Transactional
public class TsunamiController {
	
	@Autowired
	private TsunamiService tsunamiServ;
	
	@Autowired
	private InvestigatorService invServ;
	
	public TsunamiController() {}
	
	@PostMapping(path = "/createtsunami", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createTsunami(@RequestBody Tsunami data) {
		String continent=AESUtil.encrypt(ContinentLocator.getContinent(data.getCountry()));
		Tsunami encripted=new Tsunami(AESUtil.encrypt(data.getUuid()), continent, AESUtil.encrypt(data.getCountry()), encryptInvestigators(data.getInvestigators()), AESUtil.encrypt(data.getDisasterName()), AESUtil.encrypt(data.getDescription()), data.getImage(), data.getWaveHeight());
		int status=tsunamiServ.create(encripted);
		if(status==0) {
			for(String aux: encripted.getInvestigators()) {
				invServ.addDisasterToInvestigator(aux, encripted.getUuid());
			}
			return new ResponseEntity<String>("Tsunami create successfully.",HttpStatus.ACCEPTED);
		}
		else if(status==1) {
			return new ResponseEntity<String>("Uuid already taked.",HttpStatus.NOT_ACCEPTABLE);
		}
		return new ResponseEntity<String>("Internal server error.",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping(path = "/updatetsunami/{uuid}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updatebyUiid(@PathVariable String uuid, @RequestBody Tsunami data){
		String continent=AESUtil.encrypt(ContinentLocator.getContinent(data.getCountry()));
		Tsunami encripted=new Tsunami(AESUtil.encrypt(data.getUuid()), continent, AESUtil.encrypt(data.getCountry()), encryptInvestigators(data.getInvestigators()), AESUtil.encrypt(data.getDisasterName()), AESUtil.encrypt(data.getDescription()), data.getImage(),data.getWaveHeight());
		String auxUuid=AESUtil.encrypt(uuid);
		int status=tsunamiServ.updateByUuid(auxUuid, encripted);
		if(status==0) {
			invServ.removeDisasterOfInvestigator(auxUuid);
			for(String aux: encripted.getInvestigators()) {
				invServ.addDisasterToInvestigator(aux, encripted.getUuid());
			}
			return new ResponseEntity<String>("Tsunami successfully update.",HttpStatus.ACCEPTED);
		}
		else if(status==1) {
			return new ResponseEntity<String>("Uuid already taked.",HttpStatus.NOT_ACCEPTABLE);
		}
		return new ResponseEntity<String>("Uuid not found.",HttpStatus.NOT_FOUND);
	}
	
	
	@DeleteMapping(path = "/deletetsunami/{uuid}")
	public ResponseEntity<String> deletebyUiid(@PathVariable String uuid){
		String auxUuid=AESUtil.encrypt(uuid);
		int status=tsunamiServ.deleteByUuid(auxUuid);
		if(status==0) {
			invServ.removeDisasterOfInvestigator(auxUuid);
			return new ResponseEntity<String>("Tsunami successfully delete.",HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<String>("Uuid not found.",HttpStatus.NOT_FOUND);	
	}
	
	@GetMapping(path = "/getall")
	public ResponseEntity<List<Tsunami>> getAll(){
		List<Tsunami> enDisasters=tsunamiServ.getAll();
		if(enDisasters.isEmpty())return new ResponseEntity<List<Tsunami>>(enDisasters,HttpStatus.NO_CONTENT);
		List<Tsunami> deDisasters=new ArrayList<>();
		for(Tsunami data: enDisasters) {
			String continent=AESUtil.decrypt(data.getContinent());
			Tsunami decripted=new Tsunami(AESUtil.decrypt(data.getUuid()), continent, AESUtil.decrypt(data.getCountry()), decryptInvestigators(data.getInvestigators()), AESUtil.decrypt(data.getDisasterName()), AESUtil.decrypt(data.getDescription()), data.getImage(),data.getWaveHeight());
			decripted.setId(data.getId());
			deDisasters.add(decripted);
		}
		return new ResponseEntity<List<Tsunami>>(deDisasters,HttpStatus.ACCEPTED);
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
