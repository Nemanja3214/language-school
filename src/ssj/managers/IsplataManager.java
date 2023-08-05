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

public class IsplataManager implements Manager{

	private static IsplataManager im;
	private List<Isplata> isplate;
	private Map<Integer, Isplata> isplateMapa;
	private String nazivFajla;
	
	public static IsplataManager getInstance() {
		if(im == null) {
			im = new IsplataManager();
		}
		return im;
	}
	
	public static IsplataManager getInstance(String fajl) {
		if(im == null) {
			im = new IsplataManager();
			im.ucitajPodatke(fajl);
		}
		return im;
	}
	

	private IsplataManager() {
		this.isplate= new ArrayList<Isplata>();
		this.isplateMapa = new HashMap<Integer, Isplata>();
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
				Isplata i = Isplata.parse(podaci);
				
				this.isplate.add(i);
				this.isplateMapa.put(i.getId(), i);
				
				i.getZaposlen().getIsplate().add(i);
				
				System.out.println(i);
				
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
			out.println("#id; vremeIsplate; iznos; zaposlenId");
			for(Isplata i: this.isplate) {
				out.println(i.toFile());
			}
			
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}

	public List<Isplata> putanjaIsplate() {
		return isplate;
	}

	public void setIsplate(List<Isplata> isplate) {
		this.isplate = isplate;
	}

	public Map<Integer, Isplata> getIsplateMapa() {
		return isplateMapa;
	}

	public void setIsplateMapa(Map<Integer, Isplata> isplateMapa) {
		this.isplateMapa = isplateMapa;
	}

	public String getNazivFajla() {
		return nazivFajla;
	}

	public void setNazivFajla(String nazivFajla) {
		this.nazivFajla = nazivFajla;
	}
	
	public static void reset() {
		im = null;
	}

	public List<Isplata> getIsplate() {
		return isplate;
	}

	public static IsplataManager getIm() {
		return im;
	}


	

	
}
