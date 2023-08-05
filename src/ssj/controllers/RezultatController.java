package ssj.controllers;

import java.util.List;

import ssj.gui.rezultat.RezultatiShow;
import ssj.model.Rezultat;
import ssj.model.Termin;
import ssj.model.osoba.Osoba;

public class RezultatController {

	public static void prikazRezultataTermina(Termin t, Osoba o) {
		List<Rezultat> rezultati = t.getRezultati();
		new RezultatiShow(rezultati, o);
	}

}
