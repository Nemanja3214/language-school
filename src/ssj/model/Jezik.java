package ssj.model;

import java.util.ArrayList;
import java.util.List;

import ssj.model.osoba.Predavac;
import ssj.utils.Izbrisiv;

public class Jezik extends Izbrisiv implements Zapisiv{
	private int id;
	private String naziv;
	
	private List<Kurs> kursevi;
	
	private List<Predavac> predavaci;
	
	public Jezik() {
		this.kursevi = new ArrayList<Kurs>();
		this.predavaci = new ArrayList<Predavac>();
	}


	public static Jezik parse(String[] podaci) {
		Jezik j = new Jezik();
		j.setId(Integer.parseInt(podaci[0].trim()));
		j.setNaziv(podaci[1].trim());
		j.setObrisan(Boolean.parseBoolean(podaci[2].trim()));
		return j;
	}
	
	public String toFile() {
		return String.join(";", Integer.toString(id), naziv, Boolean.toString(obrisan));
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNaziv() {
		return naziv;
	}


	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}


	public List<Kurs> getKursevi() {
		return kursevi;
	}


	public void setKursevi(List<Kurs> kursevi) {
		this.kursevi = kursevi;
	}


	public List<Predavac> getPredavaci() {
		return predavaci;
	}


	public void setPredavaci(List<Predavac> predavaci) {
		this.predavaci = predavaci;
	}


	public Object toCell(int columnIndex) {
		switch(columnIndex) {
		case 0: return this.id;
		case 1: return this.naziv;
		default: return "";
		}
	}


	@Override
	public String toString() {
		return naziv;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((naziv == null) ? 0 : naziv.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jezik other = (Jezik) obj;
		if (id != other.id)
			return false;
		if (naziv == null) {
			if (other.naziv != null)
				return false;
		} else if (!naziv.equals(other.naziv))
			return false;
		return true;
	}


	public Jezik(int id, String naziv) {
		super();
		this.id = id;
		this.naziv = naziv;
	}
	
	






	
	
	

}
