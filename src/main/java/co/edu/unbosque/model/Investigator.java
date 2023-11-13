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
@Table(name = "investigator")
public class Investigator {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	private String invName;
	@Column(unique = true)
	private String uuid;
	private ArrayList<NaturalDisaster> disastersInvestigated;

	public Investigator() {

	}

	public Investigator(Long id, String invName, String uuid, ArrayList<NaturalDisaster> disastersInvestigated) {
		super();
		this.id = id;
		this.invName = invName;
		this.uuid = uuid;
		this.disastersInvestigated = disastersInvestigated;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return invName;
	}

	public void setName(String invName) {
		this.invName = invName;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public ArrayList<NaturalDisaster> getDisastersInvestigated() {
		return disastersInvestigated;
	}

	public void setDisastersInvestigated(ArrayList<NaturalDisaster> disastersInvestigated) {
		this.disastersInvestigated = disastersInvestigated;
	}

	@Override
	public int hashCode() {
		return Objects.hash(disastersInvestigated, id, invName, uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Investigator other = (Investigator) obj;
		return Objects.equals(disastersInvestigated, other.disastersInvestigated) && Objects.equals(id, other.id)
				&& Objects.equals(invName, other.invName) && Objects.equals(uuid, other.uuid);
	}

	@Override
	public String toString() {
		return "Investigator [id=" + id + ", name=" + invName + ", uuid=" + uuid + ", disastersInvestigated="
				+ disastersInvestigated + "]";
	}

}
