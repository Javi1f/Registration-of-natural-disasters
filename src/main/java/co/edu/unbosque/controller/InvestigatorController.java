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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.model.Investigator;
import co.edu.unbosque.services.InvestigatorService;
import co.edu.unbosque.services.NaturalDisasterService;
import co.edu.unbosque.util.AESUtil;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/investigator")
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:8081","*"})
@Transactional
public class InvestigatorController {

	@Autowired
	private InvestigatorService invServ;
	
	@Autowired
	private NaturalDisasterService natServ;
	
	@PostMapping(path = "/createinvestigator", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createNaturalDisaster(@RequestBody Investigator data) {
		Investigator encripted=new Investigator(AESUtil.encrypt(data.getName()), AESUtil.encrypt(data.getUuid()));
		int status=invServ.create(encripted);
		if(status==0) {
			return new ResponseEntity<String>("Investigator create successfully.",HttpStatus.ACCEPTED);
		}
		else if(status==1) {
			return new ResponseEntity<String>("Uuid already taked.",HttpStatus.NOT_ACCEPTABLE);
		}
		return new ResponseEntity<String>("Internal server error.",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping(path = "/deleteinvestigator/{uuid}")
	public ResponseEntity<String> deletebyUiid(@PathVariable String uuid){
		String auxUuid=AESUtil.encrypt(uuid);
		int status=invServ.deleteByUuid(auxUuid);
		if(status==0) {
			natServ.removeInvestigatorOfDisaster(auxUuid);
			return new ResponseEntity<String>("Investigator successfully delete.",HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<String>("Uuid not found.",HttpStatus.NOT_FOUND);	
	}
	
	@GetMapping(path = "/getall")
	public ResponseEntity<List<Investigator>> getAll(){
		List<Investigator> enDisasters=invServ.getAll();
		if(enDisasters.isEmpty())return new ResponseEntity<List<Investigator>>(enDisasters,HttpStatus.NO_CONTENT);
		List<Investigator> deDisasters=new ArrayList<>();
		for(Investigator data: enDisasters) {
			Investigator decript=new Investigator(AESUtil.decrypt(data.getName()), AESUtil.decrypt(data.getUuid()));
			decript.setId(data.getId());
			decript.setDisastersInvestigated(decryptDisaster(data.getDisastersInvestigated()));
			deDisasters.add(decript);
		}
		return new ResponseEntity<List<Investigator>>(deDisasters,HttpStatus.ACCEPTED);
	}
	
	@GetMapping(path = "/get/{uuid}")
	public ResponseEntity<Investigator> get(@PathVariable String uuid){
		String enUuid=AESUtil.encrypt(uuid);
		if(invServ.exists(enUuid)) {
			Investigator data=invServ.get(enUuid);
			Investigator decripted=new Investigator(AESUtil.decrypt(data.getName()), AESUtil.decrypt(data.getUuid()));
			decripted.setDisastersInvestigated(decryptDisaster(data.getDisastersInvestigated()));
			decripted.setId(data.getId());
			return new ResponseEntity<Investigator>(decripted,HttpStatus.FOUND);
		}
		return new ResponseEntity<Investigator>(new Investigator(),HttpStatus.NOT_FOUND);
	}
	
	public ArrayList<String> decryptDisaster(ArrayList<String> encryptDisaster){
		ArrayList<String> aux=new ArrayList<>();
		for(String disaster: encryptDisaster) {
			aux.add(AESUtil.decrypt(disaster));
		}
		return aux;
	}
	
}
