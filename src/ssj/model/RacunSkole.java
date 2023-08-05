package ssj.model;

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

import ssj.model.zahtev.Zahtev;

public class RacunSkole{

//	singleton
	private double stanje;
	public static RacunSkole racunSkole;
	public static String nazivFajla = "."+File.separator+"data"+File.separator+"racun_skole.txt";
	
	public static RacunSkole getInstance() {
		if(racunSkole == null) {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(nazivFajla), "utf-8"));
				String red;
				String[] podaci;
				racunSkole = new RacunSkole();
				while((red = in.readLine()) != null) {
					red = red.trim();
					if(red.equals("") || red.startsWith("#"))
						continue;
					podaci = red.split(";");
					
					racunSkole.setStanje(Double.parseDouble(podaci[0].trim()));
					
					System.out.println(racunSkole);
					
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
		return racunSkole;
	}

	private RacunSkole() {
		
	}
	
	public double getStanje() {
		return stanje;
	}

	public void setStanje(double stanje) {
		this.stanje = stanje;
	}
	@Override
	public String toString() {
		return "RacunSkole [stanje=" + stanje + "]";
	}

	public void sacuvajPodatke() {
		try {
			PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(nazivFajla),"utf-8"), true);
			out.println(Double.toString(stanje));
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}

}
