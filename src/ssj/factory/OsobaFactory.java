package ssj.factory;

import ssj.managers.OsobaManager;
import ssj.model.osoba.Administrator;
import ssj.model.osoba.Osoba;
import ssj.model.osoba.Predavac;
import ssj.model.osoba.Sekretar;
import ssj.model.osoba.Ucenik;
import ssj.model.osoba.Uloga;

public class OsobaFactory {
	private String[] uloge = {"Administrator", "Sekretar", "Predavac", "Ucenik"};

	public static Osoba kreirajOsobu(String[] podaci) {
		int ulogaId = Integer.parseInt(podaci[5].trim());
		switch (ulogaId) {
		case 0: {
			Administrator a = Administrator.parse(podaci);
			OsobaManager.getInstance().getAdministratori().add(a);
			OsobaManager.getInstance().getAdministratoriMapa().put(a.getId(), a);
			return a;
		}
		case 1: {
			Sekretar s = Sekretar.parse(podaci);
			if(s.isObrisan()) {
				OsobaManager.getInstance().getSekretari().add(s);
				OsobaManager.getInstance().getZaposleni().add(s);
			}
			else {
				OsobaManager.getInstance().getSekretari().add(0, s);
				OsobaManager.getInstance().getZaposleni().add(0, s);
			}
			
			OsobaManager.getInstance().getSekretariMapa().put(s.getId(), s);
			OsobaManager.getInstance().getZaposleniMapa().put(s.getId(), s);
			return s;
		}
		case 2: {
			Predavac p = Predavac.parse(podaci);
			if(p.isObrisan()) {
				OsobaManager.getInstance().getPredavaci().add(p);
				OsobaManager.getInstance().getZaposleni().add(p);
			}
			else {
				OsobaManager.getInstance().getPredavaci().add(0, p);
				OsobaManager.getInstance().getZaposleni().add(0, p);
			}
			OsobaManager.getInstance().getPredavaciMapa().put(p.getId(), p);
			OsobaManager.getInstance().getZaposleniMapa().put(p.getId(), p);
			return p;
		}
		case 3: {
			Ucenik u = Ucenik.parse(podaci);
//			if(u.isObrisan())
//				OsobaManager.getInstance().getUcenici().add(u);
//			else
//				OsobaManager.getInstance().getUcenici().add(0, u);
			OsobaManager.getInstance().getUcenici().add(u);
			OsobaManager.getInstance().getUceniciMapa().put(u.getId(), u);
			return u;
		}
		default:
			throw new IllegalArgumentException("Došlo je do greške");
		}
	}
}
