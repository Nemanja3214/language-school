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
import ssj.model.Test;

public class TestManager implements Manager{


	private List<Test> testovi;
	private Map<Integer, Test> testoviMapa;
	private String nazivFajla;
	
	
	public static TestManager tm;
	
	public static TestManager getInstance() {
		if(tm == null) {
			tm = new TestManager();
		}
		return tm;
	}
	
	public static TestManager getInstance(String fajl) {
		if(tm == null) {
			tm = new TestManager();
			tm.ucitajPodatke(fajl);
		}
		return tm;
	}

	private TestManager() {
		this.testovi = new ArrayList<Test>();
		this.testoviMapa = new HashMap<Integer, Test>();
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
				Test t = Test.parse(podaci);
				
				this.testovi.add(t);
				this.testoviMapa.put(t.getId(), t);
				
				t.getKurs().getTestovi().add(t);
				
				System.out.println(t);
//				# id; naziv; opis; kursId; 
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

	public List<Test> getTestovi() {
		return testovi;
	}

	public void setTestovi(List<Test> testovi) {
		this.testovi = testovi;
	}

	public Map<Integer, Test> getTestoviMapa() {
		return testoviMapa;
	}

	public void setTestoviMapa(Map<Integer, Test> testoviMapa) {
		this.testoviMapa = testoviMapa;
	}

	@Override
	public void sacuvajPodatke(String fajl) {
		try {
			PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fajl),"utf-8"), true);
			out.println("# id; naziv; opis; kursId; ");
			for(Test t: testovi) {
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

	public static TestManager getTm() {
		return tm;
	}
	
}
