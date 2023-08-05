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

import ssj.model.Termin;
import ssj.model.Test;

public class TerminManager implements Manager{
	private List<Termin> termini;
	private Map<Integer, Termin> terminiMapa;
	private String nazivFajla;
	
	
	private static TerminManager tm;
	
	public static TerminManager getInstance() {
		if(tm == null) {
			tm = new TerminManager();
		}
		return tm;
	}
	
	public static TerminManager getInstance(String fajl) {
		if(tm == null) {
			tm = new TerminManager();
			tm.ucitajPodatke(fajl);
		}
		return tm;
	}

	private TerminManager() {
		this.termini = new ArrayList<Termin>();
		this.terminiMapa = new HashMap<Integer, Termin>();
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
				Termin t = Termin.parse(podaci);
				
				this.termini.add(t);
				this.terminiMapa.put(t.getId(), t);
				
				t.getTest().getTermini().add(t);
				
				System.out.println(t);
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

	public List<Termin> getTermini() {
		return termini;
	}

	public void setTermini(List<Termin> termini) {
		this.termini = termini;
	}

	public Map<Integer, Termin> getTerminiMapa() {
		return terminiMapa;
	}

	public void setTerminiMapa(Map<Integer, Termin> terminiMapa) {
		this.terminiMapa = terminiMapa;
	}

	public static TerminManager getTm() {
		return tm;
	}

	public static void setTm(TerminManager tm) {
		TerminManager.tm = tm;
	}

	@Override
	public void sacuvajPodatke(String fajl) {
		try {
			PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fajl),"utf-8"), true);
			out.println("# id; naziv; opis; kursId; ");
			for(Termin t : termini) {
				out.println(t.toFile());
			}
			
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
	public static void reset() {
		tm = null;
	}
	
}
