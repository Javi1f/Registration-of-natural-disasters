package co.edu.unbosque.model;

import java.util.ArrayList;
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

	public VolcanicEruption(float scope) {
		this.scope = scope;
	}

	public VolcanicEruption(String uuid, String continent, String country, ArrayList<String> investigators,String disasterName, String description, byte[] image, float scope) {
		super(uuid, continent, country, investigators, disasterName, description, image);
		this.scope = scope;
	}
	
	

	public VolcanicEruption(String uuid, String continent, String country, ArrayList<String> investigators,String disasterName, String description, byte[] image) {
		super(uuid, continent, country, investigators, disasterName, description, image);
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
