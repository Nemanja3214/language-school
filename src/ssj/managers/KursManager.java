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
import java.util.List;
import java.util.Map;

import ssj.model.Jezik;
import ssj.model.Kurs;

public class KursManager implements Manager{
	private static KursManager km;
	private List<Kurs> kursevi;
	private Map<Integer, Kurs> kurseviMapa;
	private String nazivFajla;
	
	public static KursManager getInstance() {
		if(km == null) {
			km = new KursManager();
		}
		return km;
	}
	
	public static KursManager getInstance(String fajl) {
		if(km == null) {
			km = new KursManager();
			km.ucitajPodatke(fajl);
		}
		return km;
	}

	private KursManager() {
		this.kursevi = new ArrayList<Kurs>();
		this.kurseviMapa = new HashMap<Integer, Kurs>();
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
				Kurs k = Kurs.parse(podaci);
				
				this.kursevi.add(k);
				this.kurseviMapa.put(k.getId(), k);
				
				k.getPredavac().getKursevi().add(k);
				k.getJezik().getKursevi().add(k);
				
				System.out.println(k);
				
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

	public List<Kurs> getKursevi() {
		return kursevi;
	}

	public void setKursevi(List<Kurs> kursevi) {
		this.kursevi = kursevi;
	}

	public Map<Integer, Kurs> getKurseviMapa() {
		return kurseviMapa;
	}

	public void setKurseviMapa(Map<Integer, Kurs> kurseviMapa) {
		this.kurseviMapa = kurseviMapa;
	}

	@Override
	public void sacuvajPodatke(String fajl) {
		try {
			PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fajl),"utf-8"), true);
			out.println("#id; naziv; cena; uvecanje cene; jezikId; predavacId");
			for(Kurs k: kursevi) {
				out.println(k.toFile());
			}
			
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
	public static void reset() {
		km = null;
	}

	public static KursManager getKm() {
		return km;
	}
	
}
