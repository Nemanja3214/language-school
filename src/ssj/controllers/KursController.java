package ssj.controllers;

import java.util.List;

import ssj.gui.kurs.KurseviShow;
import ssj.model.Kurs;
import ssj.model.osoba.Predavac;
import ssj.model.osoba.Ucenik;

public class KursController {

	public static void prikazPredavacKursevi(Predavac p) {
		List<Kurs> kursevi = p.getKursevi();
		new KurseviShow(kursevi, p);
		
	}

	public static void prikazUcenikKursevi(Ucenik u) {
		List<Kurs> kursevi = u.getKursevi();
		new KurseviShow(kursevi, u);
		
	}

}
