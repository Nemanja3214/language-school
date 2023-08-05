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

import ssj.model.Kurs;
import ssj.model.Pohadjanje;
import ssj.model.zahtev.Zahtev;

public class PohadjanjeManager implements Manager{
	private static PohadjanjeManager pm;
	private List<Pohadjanje> pohadjanja;
	private Map<Integer, Pohadjanje> pohadjanjaMapa;
	private String nazivFajla;
	
	public static PohadjanjeManager getInstance() {
		if(pm == null) {
			pm = new PohadjanjeManager();
		}
		return pm;
	}
	
	public static PohadjanjeManager getInstance(String fajl) {
		if(pm == null) {
			pm = new PohadjanjeManager();
			pm.ucitajPodatke(fajl);
		}
		return pm;
	}

	private PohadjanjeManager() {
		this.pohadjanja = new ArrayList<Pohadjanje>();
		this.pohadjanjaMapa = new HashMap<Integer, Pohadjanje>();
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
				Pohadjanje p = Pohadjanje.parse(podaci);
				
				this.pohadjanja.add(p);
				this.pohadjanjaMapa.put(p.getId(), p);
				
				p.getKurs().getPohadjanja().add(p);
				p.getUcenik().getPohadjanja().add(p);
				
				System.out.println(p);
				
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
			out.println("# id; polozio; vremeUpisa; kursId; ucenikId");
			for(Pohadjanje p: pohadjanja) {
				out.println(p.toFile());
			}
			
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}

	public List<Pohadjanje> getPohadjanja() {
		return pohadjanja;
	}

	public void setPohadjanja(List<Pohadjanje> pohadjanja) {
		this.pohadjanja = pohadjanja;
	}

	public Map<Integer, Pohadjanje> getPohadjanjaMapa() {
		return pohadjanjaMapa;
	}

	public void setPohadjanjaMapa(Map<Integer, Pohadjanje> pohadjanjaMapa) {
		this.pohadjanjaMapa = pohadjanjaMapa;
	}
	public static void reset() {
		pm = null;
	}

	public static PohadjanjeManager getPm() {
		return pm;
	}
	
}
