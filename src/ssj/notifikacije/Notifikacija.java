package ssj.notifikacije;

import ssj.managers.KursManager;
import ssj.model.Kurs;

public class Notifikacija {
	private int id;
	private String naslov;
	private String tekst;
	
	private Kurs kurs;
	
	public Notifikacija() {
		
	}

	public String getNaslov() {
		return naslov;
	}

	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}

	public String getTekst() {
		return tekst;
	}

	public void setTekst(String tekst) {
		this.tekst = tekst;
	}

	public Kurs getKurs() {
		return kurs;
	}

	public void setKurs(Kurs kurs) {
		this.kurs = kurs;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static Notifikacija parse(String[] podaci) {
		Notifikacija n = new Notifikacija();
		
//		# id;  ucenikId; naslov; tekst
		n.setId(Integer.parseInt(podaci[0].trim()));
		n.setKurs(KursManager.getInstance().getKurseviMapa().get(Integer.parseInt(podaci[1].trim())));
		n.setNaslov(podaci[2].trim());
		n.setTekst(podaci[3].trim());
		
		return  n;
	}

	public String toFile() {
		return String.join(";", Integer.toString(id), Integer.toString(kurs.getId()), naslov, tekst);
	}

	public Notifikacija(int id, String naslov, String tekst, Kurs kurs) {
		super();
		this.id = id;
		this.naslov = naslov;
		this.tekst = tekst;
		this.kurs = kurs;
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
		Notifikacija other = (Notifikacija) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	 
}
