package ssj.model;

import java.time.LocalDateTime;

import ssj.managers.KursManager;
import ssj.managers.OsobaManager;
import ssj.model.osoba.Ucenik;
import ssj.utils.Izbrisiv;
import ssj.utils.KorisneFunkcije;

public class Pohadjanje extends Izbrisiv implements Zapisiv{
	
	private int id;
	private LocalDateTime vremeUpisa;
	private boolean polozio;
	
	private Kurs kurs;
	private Ucenik ucenik;
	
	public Pohadjanje() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getVremeUpisa() {
		return vremeUpisa;
	}

	public void setVremeUpisa(LocalDateTime vremeUpisa) {
		this.vremeUpisa = vremeUpisa;
	}

	public boolean isPolozio() {
		return polozio;
	}

	public void setPolozio(boolean polozio) {
		this.polozio = polozio;
	}

	public Kurs getKurs() {
		return kurs;
	}

	public void setKurs(Kurs kurs) {
		this.kurs = kurs;
	}

	public Ucenik getUcenik() {
		return ucenik;
	}

	public void setUcenik(Ucenik ucenik) {
		this.ucenik = ucenik;
	}

	public static Pohadjanje parse(String[] podaci) {
		Pohadjanje p = new Pohadjanje();
		p.setId(Integer.parseInt(podaci[0].trim()));
		p.setPolozio(Boolean.parseBoolean(podaci[1]));
		p.setVremeUpisa(LocalDateTime.parse(podaci[2].trim(), KorisneFunkcije.dtf1));
		p.setKurs(KursManager.getInstance().getKurseviMapa().get(Integer.parseInt(podaci[3].trim())));
		p.setUcenik(OsobaManager.getInstance().getUceniciMapa().get(Integer.parseInt(podaci[4].trim())));
		p.setObrisan(Boolean.parseBoolean(podaci[5].trim()));
		
		return p;
	}

//	# id; polozio; vremeUpisa; kursId; ucenikId
	@Override
	public String toFile() {
		return String.join(";", Integer.toString(id), Boolean.toString(polozio),
				vremeUpisa.format(KorisneFunkcije.dtf1), Integer.toString(kurs.getId()), Integer.toString(ucenik.getId()), Boolean.toString(obrisan));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pohadjanje other = (Pohadjanje) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public Pohadjanje(int id, LocalDateTime vremeUpisa, boolean polozio, Kurs kurs, Ucenik ucenik) {
		super();
		this.id = id;
		this.vremeUpisa = vremeUpisa;
		this.polozio = polozio;
		this.kurs = kurs;
		this.ucenik = ucenik;
	}
	

}
