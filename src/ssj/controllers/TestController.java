package ssj.controllers;

import java.util.List;

import ssj.gui.test.TestoviShow;
import ssj.model.Cenovnik;
import ssj.model.Kurs;
import ssj.model.Termin;
import ssj.model.Test;
import ssj.model.osoba.Osoba;
import ssj.model.osoba.Ucenik;

public class TestController {

	public static void prikazTestovaKursa(Kurs k, Osoba o) {
		List<Test> testovi = k.getTestovi();
		new TestoviShow(testovi, o);
		
	}

	public static double izracunajIznos(Termin t, Ucenik u) {
		int brojPolaganja = OsobaController.prebrojRacuneUcenikaZaTest(u, t.getTest());
		Kurs k = t.getTest().getKurs();
//		ukoliko je polagao više od 10 puta ima popust
		if(brojPolaganja > Cenovnik.norma)
			return k.getCena(true) + brojPolaganja * k.getUvecanjeCene();
		else
			return k.getCena(false) + brojPolaganja * k.getUvecanjeCene();
	}

}
