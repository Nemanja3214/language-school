package ssj.model;

import ssj.managers.OsobaManager;
import ssj.managers.TerminManager;
import ssj.model.osoba.Ucenik;
import ssj.utils.Izbrisiv;
import ssj.utils.KorisneFunkcije;

public class Rezultat extends Izbrisiv implements Zapisiv{

	private int id;
	private double brojBodova;
	private int ocena;
	private boolean polozio;
	
	private Termin termin;
	private Ucenik ucenik;
	
	public Rezultat() {

	}
//	private String[] kolone = {"ID", "Broj bodova", "Ocena", "Polozio/Nije polozio", "Ucenik", "Termin", "Test"};
	public Object toCell(int columnIndex) {
		switch (columnIndex) {
		case 0: return id;
		case 1: return brojBodova;
		case 2: return ocena;
		case 3: return polozio ? "Da" : "Ne";
		case 4: return ucenik.getIme() + " " + ucenik.getPrezime();
		case 5: return termin.getVremeOdrzavanja().format(KorisneFunkcije.dtf1);
		case 6: return termin.getTest().getNaziv();
		default: return "";
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getBrojBodova() {
		return brojBodova;
	}

	public void setBrojBodova(double brojBodova) {
		this.brojBodova = brojBodova;
	}

	public int getOcena() {
		return ocena;
	}

	public void setOcena(int ocena) {
		this.ocena = ocena;
	}

	public boolean isPolozio() {
		return polozio;
	}

	public void setPolozio(boolean polozio) {
		this.polozio = polozio;
	}

	public Termin getTermin() {
		return termin;
	}

	public void setTermin(Termin termin) {
		this.termin = termin;
	}

	public Ucenik getUcenik() {
		return ucenik;
	}

	public void setUcenik(Ucenik ucenik) {
		this.ucenik = ucenik;
	}
	
	public static Rezultat parse(String[] podaci) {
		Rezultat r = new Rezultat();
		r.setId(Integer.parseInt(podaci[0].trim()));
		r.setBrojBodova(Double.parseDouble(podaci[1].trim()));
		r.setOcena(Integer.parseInt(podaci[2].trim()));
		r.setPolozio(Boolean.parseBoolean(podaci[3].trim()));
		r.setTermin(TerminManager.getInstance().getTerminiMapa().get(Integer.parseInt(podaci[4].trim())));
		r.setUcenik(OsobaManager.getInstance().getUceniciMapa().get(Integer.parseInt(podaci[5].trim())));
		r.setObrisan(Boolean.parseBoolean(podaci[6].trim()));
		return r;
	}
	@Override
	public String toFile() {
		return String.join(";", Integer.toString(id), Double.toString(brojBodova),
				Integer.toString(ocena), Boolean.toString(polozio), Integer.toString(termin.getId()), Integer.toString(ucenik.getId()), Boolean.toString(obrisan));
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
		Rezultat other = (Rezultat) obj;
		if (id != other.id)
			return false;
		return true;
	}
	public Rezultat(int id, double brojBodova, int ocena, boolean polozio, Termin termin, Ucenik ucenik) {
		this();
		this.id = id;
		this.brojBodova = brojBodova;
		this.ocena = ocena;
		this.polozio = polozio;
		this.termin = termin;
		this.ucenik = ucenik;
	}
	
	
	
}
