package co.edu.unbosque.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.model.Earthquake;
import co.edu.unbosque.model.ExtremeHeat;
import co.edu.unbosque.model.Hurricane;
import co.edu.unbosque.model.NaturalDisaster;
import co.edu.unbosque.model.Tsunami;
import co.edu.unbosque.model.VolcanicEruption;
import co.edu.unbosque.repository.EarthquakeRepository;
import co.edu.unbosque.repository.ExtremeHeatRepository;
import co.edu.unbosque.repository.HurricaneRepository;
import co.edu.unbosque.repository.NaturalDisasterRepository;
import co.edu.unbosque.repository.TsunamiRepository;
import co.edu.unbosque.repository.VolcanicEruptionRepository;

@Service
public class UuidVerifier {
	
	@Autowired
	private NaturalDisasterRepository disasterRepo;
	@Autowired
	private ExtremeHeatRepository exheRepo;
	@Autowired
	private HurricaneRepository hurRepo;
	@Autowired
	private EarthquakeRepository earthRepo;
	@Autowired
	private TsunamiRepository tsuRepo;
	@Autowired
	private VolcanicEruptionRepository volRepo;
	
	public UuidVerifier() {}
	
	public boolean uuidAlreadyTaked(String uuid) {
		Optional<NaturalDisaster> found1=disasterRepo.findByUuid(uuid);
		Optional<ExtremeHeat> found2=exheRepo.findByUuid(uuid);
		Optional<Hurricane> found3=hurRepo.findByUuid(uuid);
		Optional<Earthquake> found4=earthRepo.findByUuid(uuid);
		Optional<Tsunami> found5=tsuRepo.findByUuid(uuid);
		Optional<VolcanicEruption> found6=volRepo.findByUuid(uuid);
		if(found1.isPresent() || found2.isPresent() || found3.isPresent() || found4.isPresent() || found5.isPresent() || found6.isPresent()) {
			return true;
		}
		return false;
	}
	
	public boolean uuidAlreadyTaked(String uuid, String auuid) {
		if(uuid.equals(auuid))return false;
		Optional<NaturalDisaster> found1=disasterRepo.findByUuid(uuid);
		Optional<ExtremeHeat> found2=exheRepo.findByUuid(uuid);
		Optional<Hurricane> found3=hurRepo.findByUuid(uuid);
		Optional<Earthquake> found4=earthRepo.findByUuid(uuid);
		Optional<Tsunami> found5=tsuRepo.findByUuid(uuid);
		Optional<VolcanicEruption> found6=volRepo.findByUuid(uuid);
		if(found1.isPresent() || found2.isPresent() || found3.isPresent() || found4.isPresent() || found5.isPresent() || found6.isPresent()) {
			return true;
		}
		return false;
	}

}
