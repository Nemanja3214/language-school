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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ssj.model.osoba.Sekretar;
import ssj.model.zahtev.Kreiran;
import ssj.model.zahtev.Odbijen;
import ssj.model.zahtev.Prihvacen;
import ssj.model.zahtev.UObradi;
import ssj.model.zahtev.Zahtev;
import ssj.model.zahtev.ZahtevStanje;
import ssj.utils.KorisneFunkcije;

public class ZahtevManager implements Manager{
	private List<Zahtev> zahtevi;
	private Map<Integer, Zahtev> zahteviMapa;
	private String nazivFajla;
	public static final int KREIRAN = 1;
	public static final int U_OBRADI = 2;
	public static final int ODBIJEN = 3;
	public static final int PRIHVACEN = 4;
	

	public static ZahtevManager zm;
	
	public static ZahtevManager getInstance() {
		if(zm == null) {
			zm  = new ZahtevManager();
		}
		return zm;
	}
	
	public static ZahtevManager getInstance(String nazivFajla) {
		
		if(zm == null) {
			zm = new ZahtevManager();
			zm.ucitajPodatke(nazivFajla);
			
		}
		return zm;
	}
	private ZahtevManager() {
		this.zahtevi = new ArrayList<Zahtev>();
		this.zahteviMapa = new HashMap<Integer, Zahtev>();
	}
	public void ucitajPodatke(String nazivFajla) {
		try {
			this.nazivFajla = nazivFajla;
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(nazivFajla), "utf-8"));
			String line;
			while((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.startsWith("#"))
					continue;
				System.out.println(line);
				String[] podaci = line.split(";");
				
				Zahtev z = kreirajZahtev(podaci);
				
				zahtevi.add(z);
				zahteviMapa.put(z.getId(), z);
				
				z.getKurs().getZahtevi().add(z);
				if(z.getSekretar() != null)
					z.getSekretar().getZahtevi().add(z);
				z.getUcenik().getZahtevi().add(z);
				
				
				System.out.println();
				
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

	private Zahtev kreirajZahtev(String[] podaci) {
		Zahtev z = new Zahtev();
		z.setId(Integer.parseInt(podaci[0].trim()));
		z.setStanje(uzmiStanje(Integer.parseInt(podaci[1].trim())));
		Sekretar s = podaci[2].trim().equals("") ? null : OsobaManager.getInstance().getSekretariMapa().get(Integer.parseInt(podaci[2].trim()));
		z.setSekretar(s);
		z.setUcenik(OsobaManager.getInstance().getUceniciMapa().get(Integer.parseInt(podaci[3].trim())));
		z.setKurs(KursManager.getInstance().getKurseviMapa().get(Integer.parseInt(podaci[4].trim())));
		z.setVremeKreiranja(LocalDateTime.parse(podaci[5].trim(), KorisneFunkcije.dtf1));
		z.setObrisan(Boolean.parseBoolean(podaci[6].trim()));
		return z;
		
		
	}
	public static ZahtevStanje uzmiStanje(int id) {
		switch(id) {
		case KREIRAN: return Kreiran.getInstance(); //1
		case U_OBRADI: return UObradi.getInstance(); //2
		case ODBIJEN: return Odbijen.getInstance(); //3
		case PRIHVACEN: return Prihvacen.getInstance(); //4
		default: return null;
		}
	}
	
	public static int idStanja(ZahtevStanje zs) {
		if(zs instanceof Kreiran)
			return KREIRAN;
		else if(zs instanceof UObradi)
			return U_OBRADI;
		else if(zs instanceof Odbijen)
			return ODBIJEN;
		else if(zs instanceof Prihvacen)
			return PRIHVACEN;
		else
			return KREIRAN;
	}
	

	public List<Zahtev> getZahtevi() {
		return zahtevi;
	}

	public void setZahtevi(List<Zahtev> zahtevi) {
		this.zahtevi = zahtevi;
	}

	public Map<Integer, Zahtev> getZahteviMapa() {
		return zahteviMapa;
	}

	public void setZahteviMapa(Map<Integer, Zahtev> zahteviMapa) {
		this.zahteviMapa = zahteviMapa;
	}

	public static int getKreiran() {
		return KREIRAN;
	}

	public static int getuObradi() {
		return U_OBRADI;
	}

	public static int getOdbijen() {
		return ODBIJEN;
	}

	public static int getPrihvacen() {
		return PRIHVACEN;
	}

	@Override
	public void sacuvajPodatke(String fajl) {
		try {
			PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fajl),"utf-8"), true);
			out.println("# id; stanje; sekretar; ucenik; kurs; vremeKreiranja");
			for(Zahtev z: zahtevi) {
				out.println(z.toFile());
			}
			
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
	public static void reset() {
		zm = null;
	}

	public static ZahtevManager getZm() {
		return zm;
	}
	
	
}
