package ssj.managers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ssj.model.Cenovnik;
import ssj.model.Kurs;

public class CenovnikManager{
	private static CenovnikManager cm;
	private List<Cenovnik> cenovnici;
	private Map<Integer, Cenovnik> cenovniciMapa;
	private String nazivFajla;
	
	public static CenovnikManager getInstance() {
		if(cm == null) {
			cm = new CenovnikManager();
		}
		return cm;
	}
	
	public static CenovnikManager getInstance(String fajl, String fajl2) {
		if(cm == null) {
			cm = new CenovnikManager();
			cm.ucitajPodatke(fajl, fajl2);
		}
		return cm;
	}
	

	private CenovnikManager() {
		this.cenovnici= new ArrayList<Cenovnik>();
		this.cenovniciMapa = new HashMap<Integer, Cenovnik>();
	}
	
	public static void reset() {
		cm = null;
	}

	public void ucitajPodatke(String fajl, String fajl2) {
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
				Cenovnik c = Cenovnik.parse(podaci);
				if(c.getVaziOd().isBefore(LocalDate.now()) && c.getVaziDo().isAfter(LocalDate.now()))
					Cenovnik.trenutni = c;
				
				this.cenovnici.add(c);
				this.cenovniciMapa.put(c.getId(), c);
				
				
				System.out.println(c);
				
			}
			
			in.close();
			
			in = new BufferedReader(new InputStreamReader(new FileInputStream(fajl2), "utf-8"));

			while((red = in.readLine()) != null) {
				red = red.trim();
				if(red.equals("") || red.startsWith("#"))
					continue;
				podaci = red.split(";");

//				# cena; uvecanjeCene; popust; cenovnikId; kursId
				
				double cena = Double.parseDouble(podaci[0].trim());
				double uvecanjeCene = Double.parseDouble(podaci[1].trim());
				double popust = Double.parseDouble(podaci[2].trim());
				int cenovnikId = Integer.parseInt(podaci[3].trim());
				int kursId = Integer.parseInt(podaci[4].trim());
				Cenovnik c = cenovniciMapa.get(cenovnikId);
				Kurs k = KursManager.getInstance().getKurseviMapa().get(kursId);
				c.getCene().put(k, cena);
				c.getUvecanjaCena().put(k, uvecanjeCene);
				c.getPopusti().put(k, popust);
				
				
			}
			
			in.close();
			
			if(Cenovnik.trenutni == null) {
				Cenovnik.trenutni = new Cenovnik();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void sacuvajPodatke(String fajl, String fajl2) {
		try {
			PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fajl),"utf-8"), true);
			out.println("# id; vaziOd; vaziDo");
			for(Cenovnik c: this.cenovnici) {
				out.println(c.toFile());
			}
			
			out.close();
			
			out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fajl2),"utf-8"), true);
			out.println("# cena; uvecanjeCene; popust; cenovnikId; kursId");
			for(Cenovnik c: this.cenovnici) {
				for(Kurs k: c.getCene().keySet())
					out.println(String.join("; ", Double.toString(c.getCene().get(k)), Double.toString(c.getUvecanjaCena().get(k)),
							Double.toString(c.getPopusti().get(k)), Integer.toString(c.getId()), Integer.toString(k.getId())));
			}
			
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}

	public List<Cenovnik> getCenovnici() {
		return cenovnici;
	}

	public void setCenovnici(List<Cenovnik> cenovnici) {
		this.cenovnici = cenovnici;
	}

	public Map<Integer, Cenovnik> getCenovniciMapa() {
		return cenovniciMapa;
	}

	public void setCenovniciMapa(Map<Integer, Cenovnik> cenovniciMapa) {
		this.cenovniciMapa = cenovniciMapa;
	}

	public void azurirajTrenutni() {
		for(Cenovnik c: cenovnici) {
			if(c.getVaziOd().isBefore(LocalDate.now()) && c.getVaziDo().isAfter(LocalDate.now())) {
				Cenovnik.trenutni = c;
			}
		}
		
	}

	public static CenovnikManager getCm() {
		return cm;
	}
	
	

}
