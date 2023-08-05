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
import ssj.notifikacije.Notifikacija;

public class NotifikacijeManager implements Manager{
	private static NotifikacijeManager nm;
	private List<Notifikacija> notifikacije;
	private Map<Integer, Notifikacija> notifikacijeMapa;
	private String nazivFajla;
	
	public static NotifikacijeManager getInstance() {
		if(nm == null) {
			nm= new NotifikacijeManager();
		}
		return nm;
	}
	
	public static NotifikacijeManager getInstance(String fajl) {
		if(nm == null) {
			nm = new NotifikacijeManager();
			nm.ucitajPodatke(fajl);
		}
		return nm;
	}

	private NotifikacijeManager() {
		this.notifikacije= new ArrayList<Notifikacija>();
		this.notifikacijeMapa = new HashMap<Integer, Notifikacija>();
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
				Notifikacija n = Notifikacija.parse(podaci);
				
				this.notifikacije.add(n);
				this.notifikacijeMapa.put(n.getId(), n);
				
				n.getKurs().obavesti(n);
				
				System.out.println(n);
				
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
			out.println("# id;  kursID; naslov; tekst");
			for(Notifikacija n: this.notifikacije) {
				out.println(n.toFile());
			}
			
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}

	public List<Notifikacija> getNotifikacije() {
		return notifikacije;
	}

	public void setNotifikacije(List<Notifikacija> notifikacije) {
		this.notifikacije = notifikacije;
	}

	public Map<Integer, Notifikacija> getNotifikacijeMapa() {
		return notifikacijeMapa;
	}

	public void setNotifikacijeMapa(Map<Integer, Notifikacija> notifikacijeMapa) {
		this.notifikacijeMapa = notifikacijeMapa;
	}

	public String getNazivFajla() {
		return nazivFajla;
	}

	public void setNazivFajla(String nazivFajla) {
		this.nazivFajla = nazivFajla;
	}
	public static void reset() {
		nm = null;
	}

	public static NotifikacijeManager getNm() {
		return nm;
	}
	
}
