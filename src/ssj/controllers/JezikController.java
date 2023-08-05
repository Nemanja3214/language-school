package ssj.controllers;

import java.util.ArrayList;
import java.util.List;

import ssj.managers.JezikManager;
import ssj.model.Jezik;
import ssj.model.Kurs;

public class JezikController {

	public static List<Jezik> najpopularnijiJezici(int velicina) {
		List<Jezik> najpopularniji = new ArrayList<Jezik>();
		List<Integer> najpopularnijiVrednosti = new ArrayList<Integer>();
		for(Jezik j: JezikManager.getInstance().getJezici()) {
			int brojUcenika = prebrojUcenike(j);
			for(int i = 0; i < velicina ; i++) {
				if(i >= najpopularniji.size()) {
					najpopularniji.add(j);
					najpopularnijiVrednosti.add(brojUcenika);
					break;
				}
				else if(brojUcenika > najpopularnijiVrednosti.get(i)){
					najpopularniji.add(i, j);
					najpopularnijiVrednosti.add(i, brojUcenika);
					break;
				}
			}
		}
		
		return najpopularniji.subList(0, velicina);
	}

	private static int prebrojUcenike(Jezik j) {
		int brojUcenika = 0;
		for(Kurs k: j.getKursevi()) {
			brojUcenika += k.getPohadjanja().size();
		}
		return brojUcenika;
	}

}
