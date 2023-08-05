package ssj.model.osoba;

import java.time.LocalDate;

import ssj.managers.ZahtevManager;
import ssj.utils.KorisneFunkcije;

public class Administrator extends Osoba{

	public Administrator() {
		super();
	}

	public static Administrator parse(String[] podaci) {
		//id; korisnickoIme; lozinka; ime; prezime; uloga; datumRodjenja; telefon; adresa
		Administrator administrator = new Administrator();
		administrator.setId(Integer.parseInt(podaci[0].trim()));
		administrator.setKorisnickoIme(podaci[1].trim());
		administrator.setLozinka(podaci[2].trim());
		administrator.setIme(podaci[3].trim());
		administrator.setPrezime(podaci[4].trim());
		administrator.setUloga(Uloga.ADMINISTRATOR);
		administrator.setDatumRodjenja(LocalDate.parse(podaci[6].trim(), KorisneFunkcije.dtf));
		
		if(podaci[7].trim().equals(Pol.muski.toString()))
			administrator.setPol(Pol.muski);
		else
			administrator.setPol(Pol.zenski);
		
		administrator.setTelefon(podaci[8].trim());
		administrator.setAdresa(podaci[9].trim());
		administrator.setObrisan(Boolean.parseBoolean(podaci[10].trim()));
		return administrator;
	}

	public String toString() {
		return super.toString();
	}

	@Override
	public String toFile() {
		return String.join("; ", Integer.toString(id), korisnickoIme, lozinka, 
				ime, prezime, Integer.toString(Uloga.ADMINISTRATOR.ordinal()), datumRodjenja.format(KorisneFunkcije.dtf), pol.toString(), telefon, adresa, Boolean.toString(obrisan));
	}
}
