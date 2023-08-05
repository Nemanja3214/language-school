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

import ssj.model.Isplata;
import ssj.model.Jezik;
import ssj.model.osoba.Predavac;

public class JezikPredavacManager implements Manager{
	private static JezikPredavacManager jpm;

	public static JezikPredavacManager getInstance() {
		if(jpm == null) {
			jpm= new JezikPredavacManager();
		}
		return jpm;
	}
	
	public static JezikPredavacManager getInstance(String fajl) {
		if(jpm == null) {
			jpm = new JezikPredavacManager();
			jpm.ucitajPodatke(fajl);
		}
		return jpm;
	}

	private String nazivFajla;

	private JezikPredavacManager() {
		
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
				
				Jezik j = JezikManager.getInstance().getJeziciMapa().get(Integer.parseInt(podaci[0].trim()));
				Predavac p = OsobaManager.getInstance().getPredavaciMapa().get(Integer.parseInt(podaci[1].trim()));
				
				j.getPredavaci().add(p);
				p.getJezici().add(j);
				
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
			out.println("# jezikId; predavacId");
			for(Jezik j: JezikManager.getInstance().getJezici()) {
				for(Predavac p: j.getPredavaci()) {
					out.println(String.join(";", Integer.toString(j.getId()), Integer.toString(p.getId())));
				}
			}
			
			
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
	public static void reset() {
		jpm = null;
	}

	public static JezikPredavacManager getJpm() {
		return jpm;
	}
	
	
}
