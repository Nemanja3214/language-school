package ssj.model;

import java.util.ArrayList;
import java.util.List;

import ssj.managers.KursManager;
import ssj.utils.Izbrisiv;

public class Test extends Izbrisiv implements Zapisiv{

	private int id;
	private String naziv;
	private String opis;
	
	private Kurs kurs;
	
	private List<Termin> termini;
	
	public Test() {
		this.termini = new ArrayList<Termin>();
	}
//	# id; naziv; opis; kursId; 

	public static Test parse(String[] podaci) {
		Test t = new Test();
		
		t.setId(Integer.parseInt(podaci[0].trim()));
		t.setNaziv(podaci[1].trim());
		t.setOpis(podaci[2].trim());
		t.setKurs(KursManager.getInstance().getKurseviMapa().get(Integer.parseInt(podaci[3].trim())));
		t.setObrisan(Boolean.parseBoolean(podaci[4].trim()));
		
		return t;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Kurs getKurs() {
		return kurs;
	}

	public void setKurs(Kurs kurs) {
		this.kurs = kurs;
	}

	public List<Termin> getTermini() {
		return termini;
	}

	public void setTermini(List<Termin> termini) {
		this.termini = termini;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Object toCell(int columnIndex) {
		switch(columnIndex) {
		case 0: return id;
		case 1: return naziv;
		case 2: return kurs;
		default: return "";
		}
	}

	@Override
	public String toString() {
		return naziv;
	}

//	# id; naziv; opis; kursId; 
	public String toFile() {
		return String.join(";", Integer.toString(id), naziv, opis, Integer.toString(kurs.getId()), Boolean.toString(obrisan));
	}

	public Test(int id, String naziv, String opis, Kurs kurs) {
		this();
		this.id = id;
		this.naziv = naziv;
		this.opis = opis;
		this.kurs = kurs;
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
		Test other = (Test) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
	
}
