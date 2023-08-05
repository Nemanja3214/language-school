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

import ssj.model.Isplata;
import ssj.model.Racun;
import ssj.model.Termin;

public class RacunManager implements Manager{

	private List<Racun> racuni;
	private Map<Integer, Racun> racuniMapa;
	private String nazivFajla;
	
	
	public static RacunManager rm;
	
	public static RacunManager getInstance() {
		if(rm == null) {
			rm = new RacunManager();
		}
		return rm;
	}
	
	public static RacunManager getInstance(String fajl) {
		if(rm == null) {
			rm = new RacunManager();
			rm.ucitajPodatke(fajl);
		}
		return rm;
	}

	private RacunManager() {
		this.racuni = new ArrayList<Racun>();
		this.racuniMapa = new HashMap<Integer, Racun>();
	}
	
	@Override
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
				Racun r = Racun.parse(podaci);
				
				this.racuni.add(r);
				this.racuniMapa.put(r.getId(), r);
				
				r.getUcenik().getRacuni().add(r);
				r.getTermin().getRacuni().add(r);
				
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
	@Override
	public void sacuvajPodatke(String fajl) {
		try {
			PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fajl),"utf-8"), true);
			out.println("# id; vremeNaplate; iznos; terminId; ucenikId");
			for(Racun r: racuni) {
				out.println(r.toFile());
			}
			
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}

	public List<Racun> getRacuni() {
		return racuni;
	}

	public void setRacuni(List<Racun> racuni) {
		this.racuni = racuni;
	}

	public Map<Integer, Racun> getRacuniMapa() {
		return racuniMapa;
	}

	public void setRacuniMapa(Map<Integer, Racun> racuniMapa) {
		this.racuniMapa = racuniMapa;
	}
	public static void reset() {
		rm = null;
	}

	public static RacunManager getRm() {
		return rm;
	}

	
	

}
