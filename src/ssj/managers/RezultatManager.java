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

import ssj.model.Rezultat;
import ssj.model.Termin;
import ssj.model.Test;

public class RezultatManager implements Manager{

	private List<Rezultat> rezultati;
	private Map<Integer, Rezultat> rezultatiMapa;
	private String nazivFajla;
	
	
	public static RezultatManager rm;
	
	public static RezultatManager getInstance() {
		if(rm == null) {
			rm = new RezultatManager();
		}
		return rm;
	}
	
	public static RezultatManager getInstance(String fajl) {
		if(rm == null) {
			rm = new RezultatManager();
			rm.ucitajPodatke(fajl);
		}
		return rm;
	}

	private RezultatManager() {
		this.rezultati = new ArrayList<Rezultat>();
		this.rezultatiMapa = new HashMap<Integer, Rezultat>();
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
				Rezultat r = Rezultat.parse(podaci);
				
				this.rezultati.add(r);
				this.rezultatiMapa.put(r.getId(), r);
				
				r.getTermin().getRezultati().add(r);
				r.getUcenik().getRezultati().add(r);
				
				System.out.println(r);
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

	public List<Rezultat> getRezultati() {
		return rezultati;
	}

	public void setRezultati(List<Rezultat> rezultati) {
		this.rezultati = rezultati;
	}

	public Map<Integer, Rezultat> getRezultatiMapa() {
		return rezultatiMapa;
	}

	public void setRezultatiMapa(Map<Integer, Rezultat> rezultatiMapa) {
		this.rezultatiMapa = rezultatiMapa;
	}

	@Override
	public void sacuvajPodatke(String fajl) {
		try {
			PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fajl),"utf-8"), true);
			out.println("# id; brojBodova; ocena; polozio; terminId; ucenikId");
			for(Rezultat r: rezultati) {
				out.println(r.toFile());
			}
			
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
	public static void reset() {
		rm = null;
	}

	public static RezultatManager getRm() {
		return rm;
	}
	
}
