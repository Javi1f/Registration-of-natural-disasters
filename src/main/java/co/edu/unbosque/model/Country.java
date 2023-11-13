package co.edu.unbosque.model;

import java.util.ArrayList;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "country")
public class Country {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	@Column(unique= true)
	private String countName;
	private ArrayList<NaturalDisaster> disasters;

	public Country() {
	}

	public Country(Long id, String name, ArrayList<NaturalDisaster> disasters) {
		super();
		this.id = id;
		this.countName = name;
		this.disasters = disasters;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return countName;
	}

	public void setName(String name) {
		this.countName = name;
	}

	public ArrayList<NaturalDisaster> getDisasters() {
		return disasters;
	}

	public void setDisasters(ArrayList<NaturalDisaster> disasters) {
		this.disasters = disasters;
	}

	@Override
	public int hashCode() {
		return Objects.hash(disasters, id, countName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		return Objects.equals(disasters, other.disasters) && Objects.equals(id, other.id)
				&& Objects.equals(countName, other.countName);
	}

	@Override
	public String toString() {
		return "Country [id=" + id + ", name=" + countName + ", disasters=" + disasters + "]";
	}

}
