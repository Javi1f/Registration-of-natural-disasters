package co.edu.unbosque.model;

import java.util.ArrayList;
import java.util.Objects;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@DiscriminatorValue("hurricane")
public class Hurricane extends NaturalDisaster {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	private float magnitude;

	public Hurricane() {}

	public Hurricane(float magnitude) {
		this.magnitude = magnitude;
	}
	
	

	public Hurricane(String uuid, String continent, String country, ArrayList<String> investigators, String disasterName,String description, byte[] image, float magnitude) {
		super(uuid, continent, country, investigators, disasterName, description, image);
		this.magnitude = magnitude;
	}
	
	

	public Hurricane(String uuid, String continent, String country, ArrayList<String> investigators, String disasterName,String description, byte[] image) {
		super(uuid, continent, country, investigators, disasterName, description, image);
	}

	public float getMagnitude() {
		return magnitude;
	}

	public void setMagnitude(float magnitude) {
		this.magnitude = magnitude;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(id, magnitude);
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
		Hurricane other = (Hurricane) obj;
		return Objects.equals(id, other.id) && Float.floatToIntBits(magnitude) == Float.floatToIntBits(other.magnitude);
	}

	@Override
	public String toString() {
		return "Hurricane [id=" + id + ", magnitude=" + magnitude + "]";
	}

}
