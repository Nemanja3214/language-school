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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ssj.model.Jezik;
import ssj.model.Racun;
import ssj.model.Zapisiv;

public class JezikManager implements Manager{
	private List<Jezik> jezici;
	private Map<Integer, Jezik> jeziciMapa;
	private String nazivFajla;
	
	
	public static JezikManager jm;
	
	public static JezikManager getInstance() {
		if(jm == null) {
			jm = new JezikManager();
		}
		return jm;
	}
	
	public static JezikManager getInstance(String fajl) {
		if(jm == null) {
			jm = new JezikManager();
			jm.ucitajPodatke(fajl);
		}
		return jm;
	}
	public static void reset() {
		jm = null;
	}

	private JezikManager() {
		this.jezici = new ArrayList<Jezik>();
		this.jeziciMapa = new HashMap<Integer, Jezik>();
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
				Jezik j = Jezik.parse(podaci);
				
				this.jezici.add(j);
				this.jeziciMapa.put(j.getId(), j);
				
				System.out.println(j);
				
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
			out.println("# id; naziv");
			for(Jezik j: jezici) {
				out.println(j.toFile());
			}
			
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}

	public List<Jezik> getJezici() {
		return jezici;
	}

	public void setJezici(List<Jezik> jezici) {
		this.jezici = jezici;
	}

	public Map<Integer, Jezik> getJeziciMapa() {
		return jeziciMapa;
	}

	public void setJeziciMapa(Map<Integer, Jezik> jeziciMapa) {
		this.jeziciMapa = jeziciMapa;
	}

	public static JezikManager getJm() {
		return jm;
	}


	
}
