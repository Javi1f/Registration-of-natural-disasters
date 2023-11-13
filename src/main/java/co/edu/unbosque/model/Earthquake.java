package co.edu.unbosque.model;

import java.util.Objects;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@DiscriminatorValue("earthquake")
public class Earthquake extends NaturalDisaster {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	private float magnitude;

	public Earthquake() {
		// TODO Auto-generated constructor stub
	}

	public Earthquake(Long id, float magnitude) {
		super();
		this.id = id;
		this.magnitude = magnitude;
	}

	public Earthquake(Long id, String uuid, Country place, Investigator[] investigators, String disasterName,
			byte[] image, Long id2, float magnitude) {
		super(id, uuid, place, investigators, disasterName, image);
		id = id2;
		this.magnitude = magnitude;
	}

	public Earthquake(Long id, String uuid, Country place, Investigator[] investigators, String disasterName,
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
		Earthquake other = (Earthquake) obj;
		return Objects.equals(id, other.id) && Float.floatToIntBits(magnitude) == Float.floatToIntBits(other.magnitude);
	}

	@Override
	public String toString() {
		return "Earthquake [id=" + id + ", magnitude=" + magnitude + "]";
	}

}
