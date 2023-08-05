package ssj.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Random;

import javax.swing.JOptionPane;

public class KorisneFunkcije {
	public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d.M.yyyy.");
	public static DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("H:m d.M.yyyy.");
	public static SimpleDateFormat sdf = new SimpleDateFormat("d.M.yyyy.");
	
	public static int generisiId(Map mapa) {
		Random r= new Random();
		int idKandidat;
		while(true) {
			idKandidat = Math.abs(r.nextInt());
			if(mapa.get(idKandidat) == null)
				return idKandidat;
		}
	}
	
	public static boolean isInt(String unos) {
		try {
			Integer.parseInt(unos);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	public static boolean isPositiveInt(String unos) {
		try {
			return Integer.parseInt(unos) >= 0;
		}
		catch (Exception e) {
			return false;
		}
	}

	public static boolean isDouble(String s) {
		try {
			Double.parseDouble(s);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	public static boolean loseLokalnoVreme(String vremeString) {
		try {
		LocalDateTime.parse(vremeString, dtf1);
		return false;
		}
		catch(DateTimeParseException e) {
			return true;
		}
	}
	
	public static int[] getSliceOfArray(int[] arr, int start, int end){
		
//		kreira se nov niz
		int[] slice = new int[end - start];
		
//		kopiraju se elementi u nov niz
		for (int i = 0; i < slice.length; i++) {
		slice[i] = arr[start + i];
		}
		
//		vraca se isecak
		return slice;
	}
	
	public static void main(String[] args) {
		System.out.println(LocalDateTime.parse("11:00 15.4.2021.", KorisneFunkcije.dtf1).format(dtf1));
	}

	
	



}
