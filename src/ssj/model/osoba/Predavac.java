package ssj.model.osoba;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ssj.controllers.OsobaController;
import ssj.model.Isplata;
import ssj.model.Jezik;
import ssj.model.Kurs;
import ssj.utils.KorisneFunkcije;

public class Predavac extends Zaposlen{
	
	private List<Jezik> jezici;
	
	private List<Kurs> kursevi;
	private List<Isplata> isplate; 

	public Predavac() {
		super();
		this.jezici = new ArrayList<Jezik>();
		
		this.kursevi = new ArrayList<Kurs>();
		this.isplate = new ArrayList<Isplata>();
	}
	public static Predavac parse(String[] podaci) {
		//id; korisnickoIme; lozinka; ime; prezime; uloga; datumRodjenja; telefon; adresa; nivo spreme; godineStaza; koeficijent
		Predavac p= new Predavac();
		p.setId(Integer.parseInt(podaci[0].trim()));
		p.setKorisnickoIme(podaci[1].trim());
		p.setLozinka(podaci[2].trim());
		p.setIme(podaci[3].trim());
		p.setPrezime(podaci[4].trim());
		p.setUloga(Uloga.PREDAVAC);
		p.setDatumRodjenja(LocalDate.parse(podaci[6].trim(), KorisneFunkcije.dtf));
		
		if(podaci[7].trim().equals(Pol.muski.toString()))
			p.setPol(Pol.muski);
		else
			p.setPol(Pol.zenski);
		
		p.setTelefon(podaci[8].trim());
		p.setAdresa(podaci[9].trim());
		p.setNivoSpreme(podaci[10].trim());
		p.setGodineStaza(Integer.parseInt(podaci[11].trim()));
		p.setKoeficijent(Double.parseDouble(podaci[12].trim()));
		p.setObrisan(Boolean.parseBoolean(podaci[13].trim()));
		return p;
	}
	
	@Override
	public String toFile() {
		return String.join("; ", Integer.toString(id), korisnickoIme, lozinka, ime, prezime, Integer.toString(Uloga.PREDAVAC.ordinal()), 
				datumRodjenja.format(KorisneFunkcije.dtf), pol.toString(), telefon, adresa, nivoSpreme, Integer.toString(godineStaza), Double.toString(koeficijent), Boolean.toString(obrisan));
	}
	
	public List<Jezik> getJezici() {
		return jezici;
	}

	public void setJezici(List<Jezik> jezici) {
		this.jezici = jezici;
	}

	public List<Kurs> getKursevi() {
		return kursevi;
	}

	public void setKursevi(List<Kurs> kursevi) {
		this.kursevi = kursevi;
	}

	public List<Isplata> getIsplate() {
		return isplate;
	}

	public void setIsplate(List<Isplata> isplate) {
		this.isplate = isplate;
	}
	public Object toCell(int columnIndex) {
		switch(columnIndex) {
		case 0: return this.id;
		case 1: return this.ime;
		case 2: return this.prezime;
		case 3: return this.korisnickoIme;
		case 4: return this.pol;
		case 5: return this.datumRodjenja.format(KorisneFunkcije.dtf);
		case 6: return this.adresa;
		case 7: return this.telefon;
		case 8: return this.nivoSpreme;
		case 9: return this.godineStaza;
		case 10: return this.koeficijent;
		default: return "";
		}
	}
	@Override
	protected double izracunajBonus() {
		return OsobaController.prebrojRacuneTerminaPredavaca(this) * 100;
	}
	
	
}
