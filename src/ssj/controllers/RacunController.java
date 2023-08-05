package ssj.controllers;

import java.util.ArrayList;
import java.util.List;

import ssj.gui.racun.RacuniShow;
import ssj.managers.RacunManager;
import ssj.model.Cenovnik;
import ssj.model.Kurs;
import ssj.model.Racun;
import ssj.model.Rezultat;
import ssj.model.Termin;
import ssj.model.Test;
import ssj.model.osoba.Administrator;
import ssj.model.osoba.Ucenik;

public class RacunController {

	public static List<Ucenik> dobaviUcenike(Termin t) {
		List<Ucenik> ucenici = new ArrayList<Ucenik>();
		for(Racun r: t.getRacuni()) {
			ucenici.add(r.getUcenik());
		}
		return ucenici;
	}

	public static double izracunajIznos(Ucenik u, Termin t) {
		Kurs k = t.getTest().getKurs();
		
		List<Termin> termini = new ArrayList<Termin>();
		for(Test test: k.getTestovi()) {
			termini.addAll(test.getTermini());
		}
		
		List<Rezultat> rezultati = new ArrayList<Rezultat>();
		for(Termin termin: termini) {
			rezultati.addAll(termin.getRezultati());
		}
		
		int i = 0;
		for(Rezultat r: rezultati) {
			if(u.equals(r.getUcenik()))
				i++;
		}
//		ukoliko je uradio više od 10 testova dobija popust
		if(i > Cenovnik.norma)
			return k.getCena(true) + k.getUvecanjeCene() * i;
		else
			return k.getCena(false) + k.getUvecanjeCene() * i;
	}

	public static void prikaziSveRacune(Administrator a) {
		new RacuniShow(RacunManager.getInstance().getRacuni(), a);
	}

	public static void prikaziFinansijskuKarticuUcenika(Ucenik u) {
		List<Racun> racuni = new ArrayList<Racun>();
		for(Racun r:  RacunManager.getInstance().getRacuni()) {
			if(r.getUcenik().equals(u))
				racuni.add(r);
		}
		new RacuniShow(racuni, u);
		
	}

}
