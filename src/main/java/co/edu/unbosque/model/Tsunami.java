package co.edu.unbosque.model;

import java.util.Objects;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@DiscriminatorValue("tsunami")
public class Tsunami extends NaturalDisaster {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	private float waveHeight;

	public Tsunami() {
		// TODO Auto-generated constructor stub
	}

	public Tsunami(Long id, float waveHeight) {
		super();
		this.id = id;
		this.waveHeight = waveHeight;
	}

	public Tsunami(Long id, String uuid, Country place, Investigator[] investigators, String disasterName,
			String description, byte[] image, Long id2, float waveHeight) {
		super(id, uuid, place, investigators, disasterName, description, image);
		id = id2;
		this.waveHeight = waveHeight;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getWaveHeight() {
		return waveHeight;
	}

	public void setWaveHeight(float waveHeight) {
		this.waveHeight = waveHeight;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(id, waveHeight);
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
		Tsunami other = (Tsunami) obj;
		return Objects.equals(id, other.id)
				&& Float.floatToIntBits(waveHeight) == Float.floatToIntBits(other.waveHeight);
	}

	@Override
	public String toString() {
		return "Tsunami [id=" + id + ", waveHeight=" + waveHeight + "]";
	}

}
