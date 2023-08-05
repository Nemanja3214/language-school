package ssj.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ssj.managers.TestManager;
import ssj.utils.Izbrisiv;
import ssj.utils.KorisneFunkcije;

public class Termin extends Izbrisiv implements Zapisiv{

	private int id;
	private LocalDateTime vremeOdrzavanja;
	
	private Test test;
	
	private List<Racun> racuni;
	private List<Rezultat> rezultati;
	
	public Termin() {
		this.racuni = new ArrayList<Racun>();
		this.rezultati = new ArrayList<Rezultat>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getVremeOdrzavanja() {
		return vremeOdrzavanja;
	}

	public void setVremeOdrzavanja(LocalDateTime vremeOdrzavanja) {
		this.vremeOdrzavanja = vremeOdrzavanja;
	}

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public List<Racun> getRacuni() {
		return racuni;
	}

	public void setRacuni(List<Racun> racuni) {
		this.racuni = racuni;
	}

	public List<Rezultat> getRezultati() {
		return rezultati;
	}

	public void setRezultati(List<Rezultat> rezultati) {
		this.rezultati = rezultati;
	}

	public static Termin parse(String[] podaci) {
		Termin t = new Termin();
		t.setId(Integer.parseInt(podaci[0].trim()));
		t.setVremeOdrzavanja(LocalDateTime.parse(podaci[1].trim(), KorisneFunkcije.dtf1));
		t.setTest(TestManager.getInstance().getTestoviMapa().get(Integer.parseInt(podaci[2].trim())));
		t.setObrisan(Boolean.parseBoolean(podaci[3].trim()));
		return t;
	}

	public Object toCell(int columnIndex) {
		switch(columnIndex) {
		case 0: return id;
		case 1: return vremeOdrzavanja.format(KorisneFunkcije.dtf1);
		case 2: return test;
		default: return "";
		}
	}


	@Override
	public String toString() {
		return vremeOdrzavanja.format(KorisneFunkcije.dtf1);
	}

//	# id; vremeOdrzavanja; testId
	@Override
	public String toFile() {
		return String.join(";", Integer.toString(id), vremeOdrzavanja.format(KorisneFunkcije.dtf1), Integer.toString(test.getId()), Boolean.toString(obrisan));
	}

	public Termin(int id, LocalDateTime vremeOdrzavanja, Test test) {
		this();
		this.id = id;
		this.vremeOdrzavanja = vremeOdrzavanja;
		this.test = test;
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
		Termin other = (Termin) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
	
	
}
