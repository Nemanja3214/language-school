package ssj.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import ssj.managers.OsobaManager;
import ssj.managers.TerminManager;
import ssj.model.osoba.Ucenik;
import ssj.utils.KorisneFunkcije;

public class Racun extends Transakcija implements Zapisiv{
	private Termin termin;
	private Ucenik ucenik;
	
	public Racun() {

	}

	public Object toCell(int columnIndex) {
		switch(columnIndex) {
		case 0: return id;
		case 1: return vremeNaplate.format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy."));
		case 2: return iznos;
		case 3: return "Škola";
		case 4:return this.getUcenik();
		default: return "";
		}
	}


	public Termin getTermin() {
		return termin;
	}

	public void setTermin(Termin termin) {
		this.termin = termin;
	}

	public Ucenik getUcenik() {
		return ucenik;
	}

	public void setUcenik(Ucenik ucenik) {
		this.ucenik = ucenik;
	}

//	# id; vremeNaplate; iznos; terminId; ucenikId
	public static Racun parse(String[] podaci) {
		Racun r = new Racun();
		r.setId(Integer.parseInt(podaci[0].trim()));
		r.setVremeNaplate(LocalDateTime.parse(podaci[1].trim(), KorisneFunkcije.dtf1));
		r.setIznos(Double.parseDouble(podaci[2].trim()));
		r.setTermin(TerminManager.getInstance().getTerminiMapa().get(Integer.parseInt(podaci[3].trim())));
		r.setUcenik(OsobaManager.getInstance().getUceniciMapa().get(Integer.parseInt(podaci[4].trim())));
		r.setObrisan(Boolean.parseBoolean(podaci[5].trim()));
		return r;
	}

	public String toFile() {
//		# id; vremeNaplate; iznos; terminId; ucenikId
		return String.join(";", Integer.toString(id), vremeNaplate.format(KorisneFunkcije.dtf1),
				Double.toString(iznos), Integer.toString(termin.getId()), Integer.toString(ucenik.getId()), Boolean.toString(obrisan));
	}



	public Racun(int id, LocalDateTime vremeNaplate, double iznos, Termin termin, Ucenik ucenik) {
		super(id, vremeNaplate, iznos);
		this.termin = termin;
		this.ucenik = ucenik;
	}
	
	
	
}
