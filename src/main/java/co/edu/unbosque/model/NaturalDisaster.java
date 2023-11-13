package co.edu.unbosque.model;

import java.util.Arrays;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "naturaldisaster")
public class NaturalDisaster {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	@Column(unique = true)
	private String uuid;
	private Country place;
	private Investigator[] investigators;
	private String disasterName;
	@Lob
	private byte[] image;

	public NaturalDisaster() {
	}

	public NaturalDisaster(Long id, String uuid, Country place, Investigator[] investigators, String disasterName,
			byte[] image) {
		super();
		this.id = id;
		this.uuid = uuid;
		this.place = place;
		this.investigators = investigators;
		this.disasterName = disasterName;
		this.image = image;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Country getPlace() {
		return place;
	}

	public void setPlace(Country place) {
		this.place = place;
	}

	public Investigator[] getInvestigators() {
		return investigators;
	}

	public void setInvestigators(Investigator[] investigators) {
		this.investigators = investigators;
	}

	public String getDisasterName() {
		return disasterName;
	}

	public void setDisasterName(String disasterName) {
		this.disasterName = disasterName;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(image);
		result = prime * result + Arrays.hashCode(investigators);
		result = prime * result + Objects.hash(disasterName, id, place, uuid);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NaturalDisaster other = (NaturalDisaster) obj;
		return Objects.equals(disasterName, other.disasterName) && Objects.equals(id, other.id)
				&& Arrays.equals(image, other.image) && Arrays.equals(investigators, other.investigators)
				&& Objects.equals(place, other.place) && Objects.equals(uuid, other.uuid);
	}

	@Override
	public String toString() {
		return "NaturalDisaster [id=" + id + ", uuid=" + uuid + ", place=" + place + ", investigators="
				+ Arrays.toString(investigators) + ", disasterName=" + disasterName + ", image="
				+ Arrays.toString(image) + "]";
	}

}
