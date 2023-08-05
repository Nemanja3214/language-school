package ssj.managers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ssj.factory.OsobaFactory;
import ssj.model.osoba.Administrator;
import ssj.model.osoba.Osoba;
import ssj.model.osoba.Predavac;
import ssj.model.osoba.Sekretar;
import ssj.model.osoba.Ucenik;
import ssj.model.osoba.Zaposlen;


public class OsobaManager implements Manager{
	Map<Integer, Osoba> osobeMapa;
	List<Osoba> osobe;
	
	Map<Integer, Administrator> administratoriMapa;
	List<Administrator> administratori;
	
	Map<Integer, Sekretar> sekretariMapa;
	List<Sekretar> sekretari;
	
	Map<Integer, Predavac> predavaciMapa;
	List<Predavac> predavaci;
	
	Map<Integer, Ucenik> uceniciMapa;
	List<Ucenik> ucenici;
	
	Map<Integer, Zaposlen> zaposleniMapa;
	List<Zaposlen> zaposleni;
	private String nazivFajla;
	private Set<String> korisnicka;
	
	
	public static OsobaManager om;
	
	public static OsobaManager getInstance() {
		if(om == null) {
			om = new OsobaManager();
		}
		return om;
	}
	
	public static OsobaManager getInstance(String fajl) {
		if(om == null) {
			om = new OsobaManager();
			om.ucitajPodatke(fajl);
		}
		return om;
	}
	public static void reset() {
		om = null;
	}
	
	private OsobaManager() {
		this.osobe = new ArrayList<Osoba>();
		this.osobeMapa = new HashMap<Integer, Osoba>();
		
		this.administratori = new ArrayList<Administrator>();
		this.administratoriMapa = new HashMap<Integer, Administrator>();
		
		this.sekretari = new ArrayList<Sekretar>();
		this.sekretariMapa = new HashMap<Integer, Sekretar>();
		
		this.predavaci = new ArrayList<Predavac>();
		this.predavaciMapa = new HashMap<Integer, Predavac>();
		
		this.ucenici = new ArrayList<Ucenik>();
		this.uceniciMapa = new HashMap<Integer, Ucenik>();
		
		this.zaposleni = new ArrayList<Zaposlen>();
		this.zaposleniMapa = new HashMap<Integer, Zaposlen>();
		
		this.korisnicka = new HashSet<String>();
		
	}
	
	public void ucitajPodatke(String fajl) {
		try {
			this.nazivFajla = fajl;
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fajl), "utf-8"));
			String red;
			String[] podaci;
			while((red = in.readLine()) != null) {
				red = red.trim();
				if(red.equals("") || red.startsWith("#"))
					continue;
				podaci = red.split(";");
				Osoba o = OsobaFactory.kreirajOsobu(podaci);
				
				this.osobe.add(o);
				this.osobeMapa.put(o.getId(), o);
				
				if(!o.getKorisnickoIme().trim().equals(""))
					this.korisnicka.add(o.getKorisnickoIme());
				
				System.out.println(o);
				
			}
			
			in.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Map<Integer, Osoba> getOsobeMapa() {
		return osobeMapa;
	}

	public void setOsobeMapa(Map<Integer, Osoba> osobeMapa) {
		this.osobeMapa = osobeMapa;
	}

	public List<Osoba> getOsobe() {
		return osobe;
	}

	public void setOsobe(List<Osoba> osobe) {
		this.osobe = osobe;
	}

	public Map<Integer, Administrator> getAdministratoriMapa() {
		return administratoriMapa;
	}

	public void setAdministratoriMapa(Map<Integer, Administrator> administratoriMapa) {
		this.administratoriMapa = administratoriMapa;
	}

	public List<Administrator> getAdministratori() {
		return administratori;
	}

	public void setAdministratori(List<Administrator> administratori) {
		this.administratori = administratori;
	}

	public Map<Integer, Sekretar> getSekretariMapa() {
		return sekretariMapa;
	}

	public Set<String> getKorisnicka() {
		return korisnicka;
	}

	public void setKorisnicka(Set<String> korisnicka) {
		this.korisnicka = korisnicka;
	}

	public void setSekretariMapa(Map<Integer, Sekretar> sekretariMapa) {
		this.sekretariMapa = sekretariMapa;
	}

	public List<Sekretar> getSekretari() {
		return sekretari;
	}

	public void setSekretari(List<Sekretar> sekretari) {
		this.sekretari = sekretari;
	}

	public Map<Integer, Predavac> getPredavaciMapa() {
		return predavaciMapa;
	}

	public void setPredavaciMapa(Map<Integer, Predavac> predavaciMapa) {
		this.predavaciMapa = predavaciMapa;
	}

	public List<Predavac> getPredavaci() {
		return predavaci;
	}

	public void setPredavaci(List<Predavac> predavaci) {
		this.predavaci = predavaci;
	}

	public Map<Integer, Ucenik> getUceniciMapa() {
		return uceniciMapa;
	}

	public void setUceniciMapa(Map<Integer, Ucenik> uceniciMapa) {
		this.uceniciMapa = uceniciMapa;
	}

	public List<Ucenik> getUcenici() {
		return ucenici;
	}

	public void setUcenici(List<Ucenik> ucenici) {
		this.ucenici = ucenici;
	}

	public Map<Integer, Zaposlen> getZaposleniMapa() {
		return zaposleniMapa;
	}

	public void setZaposleniMapa(Map<Integer, Zaposlen> zaposleniMapa) {
		this.zaposleniMapa = zaposleniMapa;
	}

	public List<Zaposlen> getZaposleni() {
		return zaposleni;
	}

	public void setZaposleni(List<Zaposlen> zaposleni) {
		this.zaposleni = zaposleni;
	}

//	#OSOBE: id; korisnickoIme; lozinka; ime; prezime; uloga; datumRodjenja; pol; telefon; adresa
//	#UCENICI: id; korisnickoIme; lozinka; ime; prezime; uloga; datumRodjenja; pol; telefon; adresa; stanjeRacuna
//	#SEKRETAR: korisnickoIme; lozinka; ime; prezime; uloga; datumRodjenja; pol; telefon; adresa; strucna sprema; godine staza; koeficijent spreme
	@Override
	public void sacuvajPodatke(String fajl) {
		try {
			PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fajl),"utf-8"), true);
			out.println("#OSOBE: id; korisnickoIme; lozinka; ime; prezime; uloga; datumRodjenja; pol; telefon; adresa");
			out.println("#UCENICI: id; korisnickoIme; lozinka; ime; prezime; uloga; datumRodjenja; pol; telefon; adresa; stanjeRacuna");
			out.println("#SEKRETAR: korisnickoIme; lozinka; ime; prezime; uloga; datumRodjenja; pol; telefon; adresa; strucna sprema; godine staza; koeficijent spreme");
			out.println("#PREDAVAC: korisnickoIme; lozinka; ime; prezime; uloga; datumRodjenja; pol; telefon; adresa; strucna sprema; godine staza; koeficijent spreme");
			for(Osoba o: osobe) {
				out.println(o.toFile());
			}
			
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}

	public static OsobaManager getOm() {
		return om;
	}

	

}
