package co.edu.unbosque.model;

import java.util.ArrayList;
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
	private String continent;
	private String country;
	private ArrayList<String> investigators;
	private String disasterName;
	private String description;
	@Lob
	private byte[] image;

	public NaturalDisaster() {}

	public NaturalDisaster(String uuid, String continent, String country, ArrayList<String> investigators,String disasterName, String description, byte[] image) {
		this.uuid = uuid;
		this.continent = continent;
		this.country = country;
		this.investigators = investigators;
		this.disasterName = disasterName;
		this.description = description;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public ArrayList<String> getInvestigators() {
		return investigators;
	}

	public void setInvestigators(ArrayList<String> investigators) {
		this.investigators = investigators;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(image);
		result = prime * result + Objects.hash(continent, country, description, disasterName, id, investigators, uuid);
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
		return Objects.equals(continent, other.continent) && Objects.equals(country, other.country)
				&& Objects.equals(description, other.description) && Objects.equals(disasterName, other.disasterName)
				&& Objects.equals(id, other.id) && Arrays.equals(image, other.image)
				&& Objects.equals(investigators, other.investigators) && Objects.equals(uuid, other.uuid);
	}

	@Override
	public String toString() {
		return "NaturalDisaster [id=" + id + ", uuid=" + uuid + ", continent=" + continent + ", country=" + country
				+ ", investigators=" + investigators + ", disasterName=" + disasterName + ", description=" + description
				+ ", image=" + Arrays.toString(image) + "]";
	}
	
	public boolean containsInvestigator(String uuidInv) {
		for(String aux:investigators) {
			if(aux.equals(uuidInv))return true;
		}
		return false;
	}
	
	public boolean removeInvestigator(String removed) {
		for(int i=0;i<investigators.size();i++) {
			if(investigators.get(i).equals(removed)) {
				investigators.remove(i);
				return true;
			}
		}
		return false;
	}

}
