package ssj.model;

import java.time.LocalDateTime;

import ssj.utils.Izbrisiv;

public abstract class Transakcija extends Izbrisiv{
	protected int id;
	protected LocalDateTime vremeNaplate;
	protected double iznos;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDateTime getVremeNaplate() {
		return vremeNaplate;
	}
	public void setVremeNaplate(LocalDateTime vremeNaplate) {
		this.vremeNaplate = vremeNaplate;
	}
	public double getIznos() {
		return iznos;
	}
	public void setIznos(double iznos) {
		this.iznos = iznos;
	}
	public abstract Object toCell(int columnIndex);
	public Transakcija(int id, LocalDateTime vremeNaplate, double iznos) {
		super();
		this.id = id;
		this.vremeNaplate = vremeNaplate;
		this.iznos = iznos;
	}
	public Transakcija() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Transakcija other = (Transakcija) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
