package ssj.model.osoba;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ssj.managers.RacunManager;
import ssj.model.Kurs;
import ssj.model.Pohadjanje;
import ssj.model.Racun;
import ssj.model.Rezultat;
import ssj.model.zahtev.Zahtev;
import ssj.notifikacije.Notifikacija;
import ssj.notifikacije.Observer;
import ssj.utils.KorisneFunkcije;

public class Ucenik extends Osoba implements Observer{
	private double stanjeRacuna;
	private List<Zahtev> zahtevi;
	private List<Rezultat> rezultati;
	private List<Racun> racuni;
	private List<Pohadjanje> pohadjanja;
	
	private List<Notifikacija> notifikacije;
	
	public Ucenik() {
		super();
		this.zahtevi = new ArrayList<Zahtev>();
		this.rezultati = new ArrayList<Rezultat>();
		this.racuni = new ArrayList<Racun>();
		this.pohadjanja = new ArrayList<Pohadjanje>();
		this.notifikacije = new ArrayList<Notifikacija>();
	}
	
	public static Ucenik parse(String[] podaci) {
		//id; korisnickoIme; lozinka; ime; prezime; uloga; datumRodjenja; telefon; adresa
		Ucenik u = new Ucenik();
		u.setId(Integer.parseInt(podaci[0].trim()));
		u.setKorisnickoIme(podaci[1].trim());
		u.setLozinka(podaci[2].trim());
		u.setIme(podaci[3].trim());
		u.setPrezime(podaci[4].trim());
		u.setUloga(Uloga.UCENIK);
		u.setDatumRodjenja(LocalDate.parse(podaci[6].trim(), KorisneFunkcije.dtf));

		if(podaci[7].trim().equals(Pol.muski.toString()))
			u.setPol(Pol.muski);
		else
			u.setPol(Pol.zenski);
		
		u.setTelefon(podaci[8].trim());
		u.setAdresa(podaci[9].trim());
		u.setStanjeRacuna(Double.parseDouble(podaci[10].trim()));
		u.setObrisan(Boolean.parseBoolean(podaci[11].trim()));
		return u;
	}
	
	@Override
	public String toFile() {
		return String.join("; ", Integer.toString(id), korisnickoIme, lozinka, ime, prezime, Integer.toString(Uloga.UCENIK.ordinal()), 
				datumRodjenja.format(KorisneFunkcije.dtf), pol.toString(), telefon, adresa, Double.toString(stanjeRacuna), Boolean.toString(obrisan));
	}

	public double getStanjeRacuna() {
		return stanjeRacuna;
	}

	public void setStanjeRacuna(double stanjeRacuna) {
		this.stanjeRacuna = stanjeRacuna;
	}

	public List<Zahtev> getZahtevi() {
		return zahtevi;
	}

	public void setZahtevi(List<Zahtev> zahtevi) {
		this.zahtevi = zahtevi;
	}

	public List<Rezultat> getRezultati() {
		return rezultati;
	}

	public void setRezultati(List<Rezultat> rezultati) {
		this.rezultati = rezultati;
	}

	public List<Racun> getRacuni() {
		return racuni;
	}

	public void setRacuni(List<Racun> racuni) {
		this.racuni = racuni;
	}

	public List<Pohadjanje> getPohadjanja() {
		return pohadjanja;
	}

	public void setPohadjanja(List<Pohadjanje> pohadjanja) {
		this.pohadjanja = pohadjanja;
	}

	public Object toCell(int columnIndex) {
		switch(columnIndex) {
		case 0: return id;
		case 1: return ime;
		case 2: return prezime;
		case 3: return korisnickoIme;
		case 4: return pol;
		case 5: return datumRodjenja.format(KorisneFunkcije.dtf);
		case 6: return adresa;
		case 7: return telefon;
		case 8: return stanjeRacuna;
		default: return "";
//				private String[] kolone = {"ID", "Ime", "Prezime", "Korisnicko ime", "Pol", "Datum rodjenja", "Adresa", "Telefon", "Stanje racuna"};
		}
	}
	public List<Kurs> getKursevi(){
		List<Kurs> kursevi = new ArrayList<Kurs>();
		for(Pohadjanje p: this.getPohadjanja()) {
			kursevi.add(p.getKurs());
		}
		return kursevi;
	}

	public List<Kurs> getNepolozeniKursevi() {
		List<Kurs> kursevi = new ArrayList<Kurs>();
		for(Pohadjanje p: this.getPohadjanja()) {
			if(!p.isPolozio())
				kursevi.add(p.getKurs());
		}
		return kursevi;
	}

	public double getUkupanIznos() {
		double ukupanIznos = 0;
		for(Racun r: RacunManager.getInstance().getRacuni()) {
			if(r.getUcenik().equals(this))
				ukupanIznos += r.getIznos();
		}
		return ukupanIznos;
	}

	public List<Notifikacija> getNotifikacije() {
		return notifikacije;
	}

	public void setNotifikacije(List<Notifikacija> notifikacije) {
		this.notifikacije = notifikacije;
	}

	@Override
	public void update(Notifikacija n) {
		this.notifikacije.add(n);
		
	}

	public Ucenik(int id, String ime, String prezime, String korisnickoIme, String lozinka, Uloga uloga, Pol pol,
			LocalDate datumRodjenja, String adresa, String telefon, double stanjeRacuna) {
		super(id, ime, prezime, korisnickoIme, lozinka, uloga, pol, datumRodjenja, adresa, telefon);
		this.stanjeRacuna = stanjeRacuna;
		
	}

	
	
}
