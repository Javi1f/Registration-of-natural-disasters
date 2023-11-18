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
	private ArrayList<String> disastersInvestigated;

	public Investigator() {

	}

	public Investigator(String invName, String uuid) {
		this.invName = invName;
		this.uuid = uuid;
		this.disastersInvestigated = new ArrayList<>();
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

	public ArrayList<String> getDisastersInvestigated() {
		return disastersInvestigated;
	}

	public void setDisastersInvestigated(ArrayList<String> disastersInvestigated) {
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
	
	public boolean replaceNaturalDisaster(String old, String fresh) {
		for(int i=0;i<disastersInvestigated.size();i++) {
			if(disastersInvestigated.get(i).equals(old)) {
				disastersInvestigated.set(i, fresh);
				return true;
			}
		}
		return false;
	}
	
	public boolean removeDisaster(String removed) {
		for(int i=0;i<disastersInvestigated.size();i++) {
			if(disastersInvestigated.get(i).equals(removed)) {
				disastersInvestigated.remove(i);
				return true;
			}
		}
		return false;
	}

}
