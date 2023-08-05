package ssj.managers;

import java.io.File;

import ssj.model.RacunSkole;

public class Cuvanje {
	private static String dataPath = "data"+File.separator;
	private static String vezePath = dataPath + File.separator + "veze" + File.separator;
	
	public static void sacuvaj() {
		JezikManager.getInstance().sacuvajPodatke(dataPath + "jezici.txt");
		OsobaManager.getInstance().sacuvajPodatke(dataPath + "osobe.txt");
		JezikPredavacManager.getInstance().sacuvajPodatke(vezePath + "jezik_predavac.txt");
		CenovnikManager.getInstance().sacuvajPodatke(dataPath + "cenovnici.txt", dataPath+"cene_i_popusti.txt");
		KursManager.getInstance().sacuvajPodatke(dataPath + "kursevi.txt");
		TestManager.getInstance().sacuvajPodatke(dataPath + "testovi.txt");
		TerminManager.getInstance().sacuvajPodatke(dataPath + "termini.txt");
		RacunManager.getInstance().sacuvajPodatke(dataPath + "racuni.txt");
		RezultatManager.getInstance().sacuvajPodatke(dataPath + "rezultati.txt");
		ZahtevManager.getInstance().sacuvajPodatke(dataPath + "zahtevi.txt");
		PohadjanjeManager.getInstance().sacuvajPodatke(dataPath + "pohadjanja.txt");
		RacunSkole.getInstance().sacuvajPodatke();
		IsplataManager.getInstance().sacuvajPodatke(dataPath + "isplate.txt");
		NotifikacijeManager.getInstance().sacuvajPodatke(dataPath + "notifikacije.txt");
		
	}

	public static void ucitajSve() {
		JezikManager.getInstance(dataPath+"jezici.txt");
		OsobaManager.getInstance(dataPath+"osobe.txt");
		JezikPredavacManager.getInstance(vezePath+"jezik_predavac.txt");
		KursManager.getInstance(dataPath+"kursevi.txt");
		CenovnikManager.getInstance(dataPath+"cenovnici.txt", dataPath + "cene_i_popusti.txt");
		TestManager.getInstance(dataPath+"testovi.txt");
		TerminManager.getInstance(dataPath+"termini.txt");
		RacunManager.getInstance(dataPath+"racuni.txt");
		RezultatManager.getInstance(dataPath+"rezultati.txt");
		ZahtevManager.getInstance(dataPath+"zahtevi.txt");
		PohadjanjeManager.getInstance(dataPath+"pohadjanja.txt");
		RacunSkole.getInstance();
		IsplataManager.getInstance(dataPath+"isplate.txt");
		NotifikacijeManager.getInstance(dataPath+"notifikacije.txt");
		
	}
}
