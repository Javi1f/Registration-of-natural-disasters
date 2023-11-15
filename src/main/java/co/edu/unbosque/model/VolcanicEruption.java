package co.edu.unbosque.model;

import java.util.Objects;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@DiscriminatorValue("volcaniceruption")
public class VolcanicEruption extends NaturalDisaster {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	private float scope;

	public VolcanicEruption() {
		// TODO Auto-generated constructor stub
	}

	public VolcanicEruption(Long id, float scope) {
		super();
		this.id = id;
		this.scope = scope;
	}

	public VolcanicEruption(Long id, String uuid, Country place, Investigator[] investigators, String disasterName,
			String description, byte[] image, Long id2, float scope) {
		super(id, uuid, place, investigators, disasterName, description, image);
		id = id2;
		this.scope = scope;
	}

	public VolcanicEruption(Long id, String uuid, Country place, Investigator[] investigators, String disasterName,
			String description, byte[] image) {
		super(id, uuid, place, investigators, disasterName, description, image);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getScope() {
		return scope;
	}

	public void setScope(float scope) {
		this.scope = scope;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(id, scope);
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
		VolcanicEruption other = (VolcanicEruption) obj;
		return Objects.equals(id, other.id) && Float.floatToIntBits(scope) == Float.floatToIntBits(other.scope);
	}

	@Override
	public String toString() {
		return "VolcanicEruption [id=" + id + ", scope=" + scope + "]";
	}

}
