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
@Table(name = "continent")
public class Continent {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	@Column(unique = true)
	private String nameCont;
	private ArrayList<Country> countries;

	public Continent() {
		// TODO Auto-generated constructor stub
	}

	public Continent(Long id, String nameCont, ArrayList<Country> countries) {
		super();
		this.id = id;
		this.nameCont = nameCont;
		this.countries = countries;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return nameCont;
	}

	public void setName(String nameCont) {
		this.nameCont = nameCont;
	}

	public ArrayList<Country> getCountries() {
		return countries;
	}

	public void setCountries(ArrayList<Country> countries) {
		this.countries = countries;
	}

	@Override
	public int hashCode() {
		return Objects.hash(countries, id, nameCont);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Continent other = (Continent) obj;
		return Objects.equals(countries, other.countries) && Objects.equals(id, other.id)
				&& Objects.equals(nameCont, other.nameCont);
	}

	@Override
	public String toString() {
		return "Continent [id=" + id + ", name=" + nameCont + ", countries=" + countries + "]";
	}

}
