package ssj.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import ssj.gui.predavac.PredavaciShow;
import ssj.gui.sekretar.SekretariShow;
import ssj.gui.ucenik.UceniciShow;
import ssj.managers.OsobaManager;
import ssj.managers.ZahtevManager;
import ssj.model.Kurs;
import ssj.model.Racun;
import ssj.model.Termin;
import ssj.model.Test;
import ssj.model.osoba.Osoba;
import ssj.model.osoba.Predavac;
import ssj.model.osoba.Sekretar;
import ssj.model.osoba.Ucenik;
import ssj.model.zahtev.Kreiran;
import ssj.model.zahtev.Zahtev;

public class OsobaController {

	public static Osoba login(String username, String password) {
		try {
			for(Osoba o:OsobaManager.getInstance().getOsobe()) {
				if(o.getKorisnickoIme().equals(username) && o.getLozinka().equals(password))
					return o;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void prikazUcenika(JFrame roditelj) {
		new UceniciShow();
		
	}

	public static boolean vecPostojiKorisnicko(String korisnickoIme) {
		for(Osoba o:OsobaManager.getInstance().getOsobe()) {
			if(o.getKorisnickoIme().equals(korisnickoIme))
				return true;
		}
		return false;
	}

	public static void prikazSekretara() {
		new SekretariShow();
	}

	public static void prikazPredavaca() {
		new PredavaciShow();
	}
	
	public static int prebrojRacuneUcenikaZaTest(Ucenik u, Test t) {
		int i = 0;
		for(Racun r: u.getRacuni()) {
			if(r.getTermin().getTest().equals(t)) {
				i++;
			}
		}
		return i;
	}

	public static int prebrojRacuneTerminaPredavaca(Predavac p) {
		List<Kurs> kursevi = p.getKursevi();
		
		List<Test> testovi = new ArrayList<Test>();
		for(Kurs k: kursevi){
			testovi.addAll(k.getTestovi());
		}
		
		List<Termin> termini = new ArrayList<Termin>();
		for(Test t: testovi) {
			termini.addAll(t.getTermini());
		}
		
		List<Racun> racuni = new ArrayList<Racun>();
		for(Termin t: termini) {
			racuni.addAll(t.getRacuni());
		}
		return racuni.size();
	}

	public static int prebrojZahteveSekretara(Sekretar sekretar) {
		int i = 0;
		for(Zahtev z: ZahtevManager.getInstance().getZahtevi()) {
			if(!(z.getStanje() instanceof Kreiran) && z.getSekretar().equals(sekretar))
				i++;
		}
		return i;
	}


}
