package ssj.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ssj.gui.termin.TerminiShow;
import ssj.managers.RacunManager;
import ssj.model.Kurs;
import ssj.model.Racun;
import ssj.model.Termin;
import ssj.model.Test;
import ssj.model.osoba.Osoba;
import ssj.model.osoba.Ucenik;

public class TerminController {

	public static void prikazTerminaTesta(Test t, Osoba o) {
		List<Termin> termini = t.getTermini();
		new TerminiShow(termini, o, false);
		
	}
	
	public static void prikazDostupnihTermina(Ucenik u) {
		List<Termin> termini = dostupniTermini(u);
		
		new TerminiShow(termini, u, true);
	}

	public static List<Termin> dostupniTermini(Ucenik u) {
		List<Kurs> kursevi = u.getNepolozeniKursevi();
		
		List<Test> testovi = new ArrayList<Test>();
		for(Kurs k: kursevi) {
			testovi.addAll(k.getTestovi());
		}
		
		List<Termin> termini = new ArrayList<Termin>();
		for(Test t: testovi) {
			termini.addAll(t.getTermini());
		}

		List<Termin> terminiZaBrisanje = new ArrayList<Termin>();
		for(Termin t: termini) {
			if(t.getVremeOdrzavanja().isBefore(LocalDateTime.now()) || vecPrijavljen(t, u))
				terminiZaBrisanje.add(t);
		}
		
		for(Termin t: terminiZaBrisanje) {
			termini.remove(t);
		}
		return termini;
	}

	private static boolean vecPrijavljen(Termin t, Ucenik u) {
		for(Racun r: RacunManager.getInstance().getRacuni()) {
			if(r.getTermin().equals(t) && r.getUcenik().equals(u)) {
				return true;
			}
		}
		return false;
	}
	


}
