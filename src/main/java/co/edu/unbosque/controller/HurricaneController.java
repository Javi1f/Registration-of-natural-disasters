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

import co.edu.unbosque.model.Hurricane;
import co.edu.unbosque.services.HurricaneService;
import co.edu.unbosque.services.InvestigatorService;
import co.edu.unbosque.util.AESUtil;
import co.edu.unbosque.util.ContinentLocator;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/hurricane")
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:8081","*"})
@Transactional
public class HurricaneController {
	
	@Autowired
	private HurricaneService hurricaneServ;
	@Autowired
	private InvestigatorService invServ;
	
	public HurricaneController() {}
	
	@PostMapping(path = "/createhurricane", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createHurricane(@RequestBody Hurricane data) {
		String continent=AESUtil.encrypt(ContinentLocator.getContinent(data.getCountry()));
		Hurricane encripted=new Hurricane(AESUtil.encrypt(data.getUuid()), continent, AESUtil.encrypt(data.getCountry()), encryptInvestigators(data.getInvestigators()), AESUtil.encrypt(data.getDisasterName()), AESUtil.encrypt(data.getDescription()), data.getImage(), data.getMagnitude());
		int status=hurricaneServ.create(encripted);
		if(status==0) {
			for(String aux: encripted.getInvestigators()) {
				invServ.addDisasterToInvestigator(aux, encripted.getUuid());
			}
			return new ResponseEntity<String>("Hurricane create successfully.",HttpStatus.ACCEPTED);
		}
		else if(status==1) {
			return new ResponseEntity<String>("Uuid already taked.",HttpStatus.NOT_ACCEPTABLE);
		}
		return new ResponseEntity<String>("Internal server error.",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping(path = "/updatehurricane/{uuid}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updatebyUiid(@PathVariable String uuid, @RequestBody Hurricane data){
		String continent=AESUtil.encrypt(ContinentLocator.getContinent(data.getCountry()));
		Hurricane encripted=new Hurricane(AESUtil.encrypt(data.getUuid()), continent, AESUtil.encrypt(data.getCountry()), encryptInvestigators(data.getInvestigators()), AESUtil.encrypt(data.getDisasterName()), AESUtil.encrypt(data.getDescription()), data.getImage(),data.getMagnitude());
		String auxUuid=AESUtil.encrypt(uuid);
		int status=hurricaneServ.updateByUuid(auxUuid, encripted);
		if(status==0) {
			invServ.removeDisasterOfInvestigator(auxUuid);
			for(String aux: encripted.getInvestigators()) {
				invServ.addDisasterToInvestigator(aux, encripted.getUuid());
			}
			return new ResponseEntity<String>("Hurricane successfully update.",HttpStatus.ACCEPTED);
		}
		else if(status==1) {
			return new ResponseEntity<String>("Uuid already taked.",HttpStatus.NOT_ACCEPTABLE);
		}
		return new ResponseEntity<String>("Uuid not found.",HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping(path = "/deletehurricane/{uuid}")
	public ResponseEntity<String> deletebyUiid(@PathVariable String uuid){
		String auxUuid=AESUtil.encrypt(uuid);
		int status=hurricaneServ.deleteByUuid(auxUuid);
		if(status==0) {
			invServ.removeDisasterOfInvestigator(auxUuid);
			return new ResponseEntity<String>("Hurricane successfully delete.",HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<String>("Uuid not found.",HttpStatus.NOT_FOUND);	
	}
	
	@GetMapping(path = "/getall")
	public ResponseEntity<List<Hurricane>> getAll(){
		List<Hurricane> enDisasters=hurricaneServ.getAll();
		if(enDisasters.isEmpty())return new ResponseEntity<List<Hurricane>>(enDisasters,HttpStatus.NO_CONTENT);
		List<Hurricane> deDisasters=new ArrayList<>();
		for(Hurricane data: enDisasters) {
			String continent=AESUtil.decrypt(data.getContinent());
			Hurricane decripted=new Hurricane(AESUtil.decrypt(data.getUuid()), continent, AESUtil.decrypt(data.getCountry()), decryptInvestigators(data.getInvestigators()), AESUtil.decrypt(data.getDisasterName()), AESUtil.decrypt(data.getDescription()), data.getImage(),data.getMagnitude());
			decripted.setId(data.getId());
			deDisasters.add(decripted);
		}
		return new ResponseEntity<List<Hurricane>>(deDisasters,HttpStatus.ACCEPTED);
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
