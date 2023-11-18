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

import co.edu.unbosque.model.VolcanicEruption;
import co.edu.unbosque.services.InvestigatorService;
import co.edu.unbosque.services.VolcanicEruptionService;
import co.edu.unbosque.util.AESUtil;
import co.edu.unbosque.util.ContinentLocator;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/volcaniceruption")
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:8081","*"})
@Transactional
public class VolcanicEruptionController {
	
	@Autowired
	private VolcanicEruptionService volcanicServ;
	
	@Autowired
	private InvestigatorService invServ;
	
	public VolcanicEruptionController() {}
	
	@PostMapping(path = "/createvolcaniceruption", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createVolcanicEruption(@RequestBody VolcanicEruption data) {
		String continent=AESUtil.encrypt(ContinentLocator.getContinent(data.getCountry()));
		VolcanicEruption encripted=new VolcanicEruption(AESUtil.encrypt(data.getUuid()), continent, AESUtil.encrypt(data.getCountry()), encryptInvestigators(data.getInvestigators()), AESUtil.encrypt(data.getDisasterName()), AESUtil.encrypt(data.getDescription()), data.getImage(), data.getScope());
		int status=volcanicServ.create(encripted);
		if(status==0) {
			for(String aux: encripted.getInvestigators()) {
				invServ.addDisasterToInvestigator(aux, encripted.getUuid());
			}
			return new ResponseEntity<String>("Volcanic eruption create successfully.",HttpStatus.ACCEPTED);
		}
		else if(status==1) {
			return new ResponseEntity<String>("Uuid already taked.",HttpStatus.NOT_ACCEPTABLE);
		}
		return new ResponseEntity<String>("Internal server error.",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping(path = "/updatevolcaniceruption/{uuid}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updatebyUiid(@PathVariable String uuid, @RequestBody VolcanicEruption data){
		String continent=AESUtil.encrypt(ContinentLocator.getContinent(data.getCountry()));
		VolcanicEruption encripted=new VolcanicEruption(AESUtil.encrypt(data.getUuid()), continent, AESUtil.encrypt(data.getCountry()), encryptInvestigators(data.getInvestigators()), AESUtil.encrypt(data.getDisasterName()), AESUtil.encrypt(data.getDescription()), data.getImage(),data.getScope());
		String auxUuid=AESUtil.encrypt(uuid);
		int status=volcanicServ.updateByUuid(auxUuid, encripted);
		if(status==0) {
			invServ.removeDisasterOfInvestigator(auxUuid);
			for(String aux: encripted.getInvestigators()) {
				invServ.addDisasterToInvestigator(aux, encripted.getUuid());
			}
			return new ResponseEntity<String>("VolcanicEruption successfully update.",HttpStatus.ACCEPTED);
		}
		else if(status==1) {
			return new ResponseEntity<String>("Uuid already taked.",HttpStatus.NOT_ACCEPTABLE);
		}
		return new ResponseEntity<String>("Uuid not found.",HttpStatus.NOT_FOUND);
	}
	
	
	@DeleteMapping(path = "/deletevolcaniceruption/{uuid}")
	public ResponseEntity<String> deletebyUiid(@PathVariable String uuid){
		String auxUuid=AESUtil.encrypt(uuid);
		int status=volcanicServ.deleteByUuid(auxUuid);
		if(status==0) {
			invServ.removeDisasterOfInvestigator(auxUuid);
			return new ResponseEntity<String>("VolcanicEruption successfully delete.",HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<String>("Uuid not found.",HttpStatus.NOT_FOUND);	
	}
	
	@GetMapping(path = "/getall")
	public ResponseEntity<List<VolcanicEruption>> getAll(){
		List<VolcanicEruption> enDisasters=volcanicServ.getAll();
		if(enDisasters.isEmpty())return new ResponseEntity<List<VolcanicEruption>>(enDisasters,HttpStatus.NO_CONTENT);
		List<VolcanicEruption> deDisasters=new ArrayList<>();
		for(VolcanicEruption data: enDisasters) {
			String continent=AESUtil.decrypt(data.getContinent());
			VolcanicEruption decripted=new VolcanicEruption(AESUtil.decrypt(data.getUuid()), continent, AESUtil.decrypt(data.getCountry()), decryptInvestigators(data.getInvestigators()), AESUtil.decrypt(data.getDisasterName()), AESUtil.decrypt(data.getDescription()), data.getImage(),data.getScope());
			decripted.setId(data.getId());
			deDisasters.add(decripted);
		}
		return new ResponseEntity<List<VolcanicEruption>>(deDisasters,HttpStatus.ACCEPTED);
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
