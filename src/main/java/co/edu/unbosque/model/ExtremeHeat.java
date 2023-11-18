package co.edu.unbosque.model;

import java.util.ArrayList;
import java.util.Objects;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@DiscriminatorValue("extremeheat")
public class ExtremeHeat extends NaturalDisaster {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	private float maximumTemperature;

	public ExtremeHeat() {}

	public ExtremeHeat(float maximumTemperature) {
		this.maximumTemperature = maximumTemperature;
	}
	
	

	public ExtremeHeat(String uuid, String continent, String country,ArrayList<String> investigators, String disasterName,String description, byte[] image, float maximumTemperature) {
		super(uuid, continent, country, investigators, disasterName, description, image);
		this.maximumTemperature=maximumTemperature;
	}
	
	

	public ExtremeHeat(String uuid, String continent, String country, ArrayList<String> investigators, String disasterName,String description, byte[] image) {
		super(uuid, continent, country, investigators, disasterName, description, image);
	}

	public float getMaximumTemperature() {
		return maximumTemperature;
	}

	public void setMaximumTemperature(float maximumTemperature) {
		this.maximumTemperature = maximumTemperature;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(id, maximumTemperature);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExtremeHeat other = (ExtremeHeat) obj;
		return Objects.equals(id, other.id)
				&& Float.floatToIntBits(maximumTemperature) == Float.floatToIntBits(other.maximumTemperature);
	}

	@Override
	public String toString() {
		return "ExtremeHeat [id=" + id + ", maximumTemperature=" + maximumTemperature + "]";
	}

}
