package ssj.model.osoba;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ssj.controllers.OsobaController;
import ssj.model.Isplata;
import ssj.model.zahtev.MozeDaObradiZahtev;
import ssj.model.zahtev.Odbijen;
import ssj.model.zahtev.Prihvacen;
import ssj.model.zahtev.UObradi;
import ssj.model.zahtev.Zahtev;
import ssj.utils.KorisneFunkcije;

public class Sekretar extends Zaposlen implements MozeDaObradiZahtev{ 
	private List<Isplata> isplate;
	private List<Zahtev> zahtevi;

	public Sekretar() {
		super();
		this.isplate = new ArrayList<Isplata>();
		this.zahtevi = new ArrayList<Zahtev>();
	}
	
	public static Sekretar parse(String[] podaci) {
		//id; korisnickoIme; lozinka; ime; prezime; uloga; datumRodjenja; telefon; adresa
		Sekretar s = new Sekretar();
		s.setId(Integer.parseInt(podaci[0].trim()));
		s.setKorisnickoIme(podaci[1].trim());
		s.setLozinka(podaci[2].trim());
		s.setIme(podaci[3].trim());
		s.setPrezime(podaci[4].trim());
		s.setUloga(Uloga.SEKRETAR);
		s.setDatumRodjenja(LocalDate.parse(podaci[6].trim(), KorisneFunkcije.dtf));

		if(podaci[7].trim().equals(Pol.muski.toString()))
			s.setPol(Pol.muski);
		else
			s.setPol(Pol.zenski);
		
		s.setTelefon(podaci[8].trim());
		s.setAdresa(podaci[9].trim());
		s.setNivoSpreme(podaci[10].trim());
		s.setGodineStaza(Integer.parseInt(podaci[11].trim()));
		s.setKoeficijent(Double.parseDouble(podaci[12].trim()));
		s.setObrisan(Boolean.parseBoolean(podaci[13].trim()));
		return s;
	}
	
	@Override
	public String toFile() {
		return String.join("; ", Integer.toString(id), korisnickoIme, lozinka, ime, prezime, Integer.toString(Uloga.SEKRETAR.ordinal()), 
				datumRodjenja.format(KorisneFunkcije.dtf), pol.toString(), telefon, adresa, nivoSpreme, Integer.toString(godineStaza),
				Double.toString(koeficijent), Boolean.toString(obrisan));
	}

	public List<Isplata> getIsplate() {
		return isplate;
	}

	public void setIsplate(List<Isplata> isplate) {
		this.isplate = isplate;
	}

	public List<Zahtev> getZahtevi() {
		return zahtevi;
	}

	public void setZahtevi(List<Zahtev> zahtevi) {
		this.zahtevi = zahtevi;
	}
//	private String[] kolone = {"ID", "Ime", "Prezime", "Korisnicko ime", "Pol", "Datum rodjenja", "Adresa", "Telefon", "Nivo spreme", "Staz", "Koeficijent"};

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
	public void pregledaj(Zahtev z) {
		z.setStanje(UObradi.getInstance());
		
	}

	@Override
	public void obradi(boolean odobren, Zahtev z) {

		if(odobren) {
			z.setStanje(Prihvacen.getInstance());
		}
		else {
			z.setStanje(Odbijen.getInstance());
		}
	}

	@Override
	protected double izracunajBonus() {
		return OsobaController.prebrojZahteveSekretara(this) * 100;
	}
	
	
}
