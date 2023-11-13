package co.edu.unbosque.model;

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

	public ExtremeHeat() {
		// TODO Auto-generated constructor stub
	}

	public ExtremeHeat(Long id, float maximumTemperature) {
		super();
		this.id = id;
		this.maximumTemperature = maximumTemperature;
	}

	public ExtremeHeat(Long id, String uuid, Country place, Investigator[] investigators, String disasterName,
			byte[] image, Long id2, float maximumTemperature) {
		super(id, uuid, place, investigators, disasterName, image);
		id = id2;
		this.maximumTemperature = maximumTemperature;
	}

	public ExtremeHeat(Long id, String uuid, Country place, Investigator[] investigators, String disasterName,
			byte[] image) {
		super(id, uuid, place, investigators, disasterName, image);
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
