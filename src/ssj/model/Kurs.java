package ssj.model;

import java.util.ArrayList;
import java.util.List;

import ssj.managers.JezikManager;
import ssj.managers.OsobaManager;
import ssj.model.osoba.Predavac;
import ssj.model.osoba.Ucenik;
import ssj.model.zahtev.Zahtev;
import ssj.notifikacije.Notifikacija;
import ssj.notifikacije.Observable;
import ssj.utils.Izbrisiv;

public class Kurs extends Izbrisiv implements Zapisiv, Observable{

	private int id;
	private String naziv;
	
	private Jezik jezik;
	private Predavac predavac;
	private List<Notifikacija> notifikacije;
	
	private List<Test> testovi;
	private List<Zahtev> zahtevi;
	private List<Pohadjanje> pohadjanja;
	
	
	
	
	public Kurs() {
		this.pohadjanja = new ArrayList<Pohadjanje>();
		this.testovi = new ArrayList<Test>();
		this.zahtevi = new ArrayList<Zahtev>();
		this.notifikacije = new ArrayList<Notifikacija>();
	}


//	#id; naziv; cena; uvecanje cene; jezikId; predavacId
	public static Kurs parse(String[] podaci) {
		Kurs k = new Kurs();
		k.setId(Integer.parseInt(podaci[0].trim()));
		k.setNaziv(podaci[1].trim());
		k.setJezik(JezikManager.getInstance().getJeziciMapa().get(Integer.parseInt(podaci[2].trim())));
		k.setPredavac(OsobaManager.getInstance().getPredavaciMapa().get(Integer.parseInt(podaci[3].trim())));
		k.setObrisan(Boolean.parseBoolean(podaci[4].trim()));
		return k;
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


	public double getCena(boolean isPopust) {
		double cena;
		if(Cenovnik.trenutni.getCene().containsKey(this)) {
			cena = Cenovnik.trenutni.getCene().get(this);
		}
		else {
			cena = Cenovnik.podrazumevanaCena;
		}
		
		if(isPopust) {
			double popust;
			if(Cenovnik.trenutni.getPopusti().containsKey(this)) {
				popust = Cenovnik.trenutni.getPopusti().get(this);
			}
			else {
				popust = Cenovnik.podrazumevanPopust;
			}
			return cena * (100 - popust);
		}
		else
			return cena;
	}

	public double getUvecanjeCene() {
		if(Cenovnik.trenutni.getUvecanjaCena().get(this) == null) {
			return Cenovnik.podrazumevanoUvecanje;
		}
		else
			return Cenovnik.trenutni.getUvecanjaCena().get(this);
	}



	public Jezik getJezik() {
		return jezik;
	}


	public void setJezik(Jezik jezik) {
		this.jezik = jezik;
	}


	public Predavac getPredavac() {
		return predavac;
	}


	public void setPredavac(Predavac predavac) {
		this.predavac = predavac;
	}


	public List<Test> getTestovi() {
		return testovi;
	}


	public void setTestovi(List<Test> testovi) {
		this.testovi = testovi;
	}


	public List<Zahtev> getZahtevi() {
		return zahtevi;
	}


	public void setZahtevi(List<Zahtev> zahtevi) {
		this.zahtevi = zahtevi;
	}


	public List<Pohadjanje> getPohadjanja() {
		return pohadjanja;
	}


	public void setPohadjanja(List<Pohadjanje> pohadjanja) {
		this.pohadjanja = pohadjanja;
	}
	


	//	#id; naziv; cena; uvecanje cene; jezikId; predavacId
	public Object toCell(int columnIndex) {
		switch(columnIndex) {
		case 0: return this.id;
		case 1: return naziv;
		case 2: return this.getCena(false);
		case 3: return this.getUvecanjeCene();
		case 4: return this.getPopust();
		case 5: return jezik;
		case 6: return predavac;
		case 7: return this.getUcenici().size();
		default: return "";
		}
	}


	private List<Ucenik> getUcenici() {
		List<Ucenik> ucenici = new ArrayList<Ucenik>();
		for(Pohadjanje p: this.getPohadjanja()) {
			ucenici.add(p.getUcenik());
		}
		return ucenici;
	}


	@Override
	public String toString() {
		return naziv;
	}

//"#id; naziv; cena; uvecanje cene; jezikId; predavacId");
	@Override
	public String toFile() {
		return String.join(";", Integer.toString(id), naziv, Integer.toString(jezik.getId()), Integer.toString(predavac.getId()), Boolean.toString(obrisan));
	}

	@Override
	public void obavesti(Notifikacija n) {
		this.notifikacije.add(n);
		for(Ucenik u: this.getUcenici()) {
			u.update(n);
		}
		
	}


	public Kurs(int id, String naziv, Jezik jezik, Predavac predavac) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.jezik = jezik;
		this.predavac = predavac;
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
		Kurs other = (Kurs) obj;
		if (id != other.id)
			return false;
		return true;
	}


	public double getPopust() {
		if(Cenovnik.trenutni.getPopusti().get(this) == null) {
			return Cenovnik.podrazumevanPopust;
		}
		else
			return Cenovnik.trenutni.getPopusti().get(this);
	}

	 
}
